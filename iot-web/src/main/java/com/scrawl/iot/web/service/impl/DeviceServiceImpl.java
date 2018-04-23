package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.request.IotRegDeviceRequest;
import com.scrawl.iot.paper.http.response.IotDeviceAllResponse;
import com.scrawl.iot.paper.http.response.IotDeviceResponse;
import com.scrawl.iot.paper.http.response.IotResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.*;
import com.scrawl.iot.web.dao.mapper.*;
import com.scrawl.iot.web.enums.DeviceSyncTypeEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.DevTypeService;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private IotHttpService iotHttpService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DeviceMessageMapper deviceMessageMapper;

    @Autowired
    private DeviceMessageDetailMapper deviceMessageDetailMapper;

    @Autowired
    private DeviceSyncMapper deviceSyncMapper;

    @Autowired
    private DeviceBasicDetailMapper deviceBasicDetailMapper;

    @Autowired
    private DevTypeMapper devTypeMapper;

    private final String SERVICE_ID_BATTERY = "Battery";
    private final String SERVICE_ID_METER = "Meter";

    @Override
    public List<Device> list(DeviceListReqVO reqVO) {
        return deviceMapper.selectPageList(reqVO);
    }

    @Override
    public int count(DeviceListReqVO reqVO) {
        return deviceMapper.selectPageCount(reqVO);
    }

    @Override
    public void save(Device device) {
        // TODO:调用iot接口
        DevType devType = devTypeMapper.selectByDevType(device.getDevType());

        Account account = accountService.getAndAuthAccount(devType.getServerId());
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        IotRegDeviceRequest request = new IotRegDeviceRequest();
        request.setDevSerial(device.getDevType());
        request.setName(device.getName());
        request.setDeviceType(device.getDevType());
        request.setConnectPointId(device.getConnectPointId());
        request.setServiceMode(device.getServiceMode());
        request.setEndUserName(device.getEndUserName());
        request.setEndUserInfo(device.getEndUserInfo());
        request.setLocation(device.getLocation());
        request.setLongitude(device.getLongitude());
        request.setLatitude(device.getLatitude());

        device.setServerId(account.getServerId());
        iotHttpService.regDevice(header, request);

        deviceMapper.insertSelective(device);
    }

    @Override
    public boolean update(Device device) {
        // TODO:调用iot接口

        return deviceMapper.updateByPrimaryKeySelective(device) > 0;
    }

    @Override
    @Transactional
    public void syncDevices(List<String> serverIds, Integer managerId) {
        if (null == serverIds || serverIds.size() == 0) {
            return;
        }

        serverIds.forEach(serverId -> syncDevicesByServerId(serverId, managerId));
    }

    // 根据账户同步产品型号
    private void syncDevicesByServerId(String serverId, Integer managerId) {
        Account account = accountService.getAndAuthAccount(serverId);
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        Map<String, Object> params = new HashMap<>();
        params.put("serverID", account.getServerId());

        Integer pageNum = 1;
        List<IotDeviceAllResponse.IotDevice> devicesAll = new ArrayList<>();
        Integer size = 0;
        while(pageNum == 1 || size == 100) {
            params.put("pageNum", pageNum);
            IotDeviceAllResponse response = iotHttpService.getDevices(params, header);
            size = response.getDevices().size();
            devicesAll.addAll(response.getDevices());
            pageNum++;
        }

        devicesAll.forEach(device -> syncDevice(device, managerId));
    }

    private void syncDevice(IotDeviceAllResponse.IotDevice iotDevice, Integer managerId) {
        Device device = deviceMapper.selectByDevSerial(iotDevice.getDevSerial());
        boolean add = false;
        if (null == device) {
            add = true;
            device = new Device();
        }

        device.setServerId(iotDevice.getCreateBy());
        device.setDevType(iotDevice.getDevType());
        device.setDevSerial(iotDevice.getDevSerial());
        device.setName(iotDevice.getName());
        device.setConnectPointId(iotDevice.getConnectPointId());
        device.setIsPublished(iotDevice.getIsPublished());
        device.setLocation(iotDevice.getLocation());
        device.setLongitude(iotDevice.getLongitude());
        device.setLatitude(iotDevice.getLatitude());
        device.setEndUserInfo(iotDevice.getEndUserInfo());
        device.setEndUserName(iotDevice.getEndUserName());
        device.setIndustryName(iotDevice.getIndustryName());
        device.setCategoryName(iotDevice.getCategoryName());
        device.setDisplayIconId(iotDevice.getDisplayIconId());
        device.setClientId(iotDevice.getClientID());
        device.setHasSimCard(iotDevice.getHasSimCard());
        device.setSimNum(iotDevice.getSimNum());
        device.setCreateTime(iotDevice.getCreateTime());

        // 添加设备
        if (add) {
            device.setCreateManager(managerId);
            device.setUpdateTime(new Date());

            deviceMapper.insertSelective(device);
        } else {
            device.setUpdateManager(managerId);
            device.setUpdateTime(new Date());
            deviceMapper.updateByPrimaryKeySelective(device);
        }

        // device sync落地
        DeviceSync deviceSync = new DeviceSync();
        deviceSync.setDeviceId(device.getId());
        deviceSync.setType(DeviceSyncTypeEnum.MANAGER.getType());
        deviceSync.setCreateTime(new Date());
        deviceSyncMapper.insertSelective(deviceSync);

        // 落地basic
        addDeviceBasicDetail(device.getId(), deviceSync.getId(), "batteryLevel", iotDevice.getBatteryLevel());
        addDeviceBasicDetail(device.getId(), deviceSync.getId(), "signalStrength", iotDevice.getSignalStrength());

        if (null == iotDevice.getDevMessage()) {
            return;
        }

        Device finalDevice = device;
        iotDevice.getDevMessage().forEach(deviceData -> {
            // 消息落地
            DeviceMessage deviceMessage = new DeviceMessage();
            deviceMessage.setDeviceId(finalDevice.getId());
            deviceMessage.setSyncId(deviceSync.getId());
            deviceMessage.setMessageId(deviceData.getServiceId());
            deviceMessage.setCreateTime(new Date());
            deviceMessageMapper.insertSelective(deviceMessage);

            // 消息详情落地
            deviceData.getServiceData().forEach((k, v) -> addDeviceMessageDetail(deviceMessage.getId(), k, v));
        });
    }

    private void addDeviceBasicDetail(Integer deviceId, Integer syncId, String paramName, Object paramValue) {
        DeviceBasicDetail deviceBasicDetail = new DeviceBasicDetail();
        deviceBasicDetail.setDeviceId(deviceId);
        deviceBasicDetail.setSyncId(syncId);
        deviceBasicDetail.setParamName(paramName);
        deviceBasicDetail.setParamValue(null == paramValue ? "" : paramValue.toString());
        deviceBasicDetail.setCreateTime(new Date());

        deviceBasicDetailMapper.insertSelective(deviceBasicDetail);
    }

    private void addDeviceMessageDetail(Integer messageId, String paramName, Object paramValue) {
        DeviceMessageDetail deviceMessageDetail = new DeviceMessageDetail();
        deviceMessageDetail.setMessageId(messageId);
        deviceMessageDetail.setParamName(paramName);
        deviceMessageDetail.setParamValue(paramValue.toString());
        deviceMessageDetail.setCreateTime(new Date());

        deviceMessageDetailMapper.insertSelective(deviceMessageDetail);
    }

    @Override
    @Transactional
    public void syncDevice(Integer id, Integer ManagerId) {
        Device device = deviceMapper.selectByPrimaryKey(id);
        Account account = accountService.getAndAuthAccount(device.getServerId());
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        Map<String, Object> params = new HashMap<>();
        params.put("devSerial", device.getDevSerial());
        IotDeviceResponse response = iotHttpService.getDevice(params, header);

        device.setDevType(response.getDevType());
        device.setDevSerial(response.getDevSerial());
        device.setServiceMode(response.getServiceMode());
        device.setName(response.getName());
        device.setConnectPointId(response.getConnectPointId());
        device.setIsPublished(response.getIsPublished());
        device.setLocation(response.getLocation());
        device.setLongitude(response.getLongitude());
        device.setLatitude(response.getLatitude());
        device.setEndUserInfo(response.getEndUserInfo());
        device.setEndUserName(response.getEndUserName());
        device.setHasSimCard(response.getHasSimCard());
        device.setProtocolType(response.getProtocolType());
        device.setSimNum(response.getSimNum());
        device.setUpdateTime(new Date());
        device.setUpdateManager(ManagerId);
        deviceMapper.updateByPrimaryKeySelective(device);

        // device sync落地
        DeviceSync deviceSync = new DeviceSync();
        deviceSync.setDeviceId(device.getId());
        deviceSync.setType(DeviceSyncTypeEnum.MANAGER.getType());
        deviceSync.setCreateTime(Optional.ofNullable(response.getLastMessageTime()).orElse(new Date()));
        deviceSyncMapper.insertSelective(deviceSync);

        response.getDeviceData().forEach(deviceData -> {
            Map<String, Object> serviceData = deviceData.getServiceData();
            if (null == serviceData) {
                return;
            }

            if (deviceData.getServiceId().equals(SERVICE_ID_BATTERY)) {
                addDeviceBasicDetail(device.getId(), deviceSync.getId(), "batteryLevel", serviceData.get("batteryLevel"));
            } else if (deviceData.getServiceId().equals(SERVICE_ID_METER)) {
                addDeviceBasicDetail(device.getId(), deviceSync.getId(), "signalStrength", serviceData.get("signalStrength"));
            } else {
                // 消息落地
                DeviceMessage deviceMessage = new DeviceMessage();
                deviceMessage.setDeviceId(device.getId());
                deviceMessage.setSyncId(deviceSync.getId());
                deviceMessage.setMessageId(deviceData.getServiceId());
                deviceMessage.setCreateTime(new Date());
                deviceMessageMapper.insertSelective(deviceMessage);

                // 消息详情落地
                deviceData.getServiceData().forEach((k, v) -> addDeviceMessageDetail(deviceMessage.getId(), k, v));
            }
        });

    }

    @Override
    public boolean remove(Device device) {
        // TODO:删除设备

        return false;
    }

    @Override
    public boolean sendCommand() {
        // TODO:发送指令

        return false;
    }
}
