package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.response.IotDeviceAllResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.dao.entity.DeviceMessage;
import com.scrawl.iot.web.dao.entity.DeviceMessageDetail;
import com.scrawl.iot.web.dao.mapper.DeviceMapper;
import com.scrawl.iot.web.dao.mapper.DeviceMessageDetailMapper;
import com.scrawl.iot.web.dao.mapper.DeviceMessageMapper;
import com.scrawl.iot.web.enums.DeviceMessageTypeEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Device> list(DeviceListReqVO reqVO) {
        return deviceMapper.selectPageList(reqVO);
    }

    @Override
    public int count(DeviceListReqVO reqVO) {
        return deviceMapper.selectPageCount(reqVO);
    }

    @Override
    public boolean save(Device device) {
        // TODO:调用iot接口

        return deviceMapper.insertSelective(device) > 0;
    }

    @Override
    public boolean update(Device device) {
        // TODO:调用iot接口

        return deviceMapper.updateByPrimaryKeySelective(device) > 0;
    }

    @Override
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

        if (null == iotDevice.getDevMessage()) {
            return;
        }

        Device finalDevice = device;
        iotDevice.getDevMessage().forEach(deviceData -> {
            // 消息落地
            DeviceMessage deviceMessage = new DeviceMessage();
            deviceMessage.setDeviceId(finalDevice.getId());
            deviceMessage.setMessageId(deviceData.getServiceId());
            deviceMessage.setCreateTime(new Date());
            deviceMessageMapper.insertSelective(deviceMessage);

            // 消息详情落地
            addDeviceMessageDetail(deviceMessage.getId(), DeviceMessageTypeEnum.BATTERY, "batteryLevel", iotDevice.getBatteryLevel());
            addDeviceMessageDetail(deviceMessage.getId(), DeviceMessageTypeEnum.METER, "signalStrength", iotDevice.getSignalStrength());
            deviceData.getServiceData().forEach((k, v) -> addDeviceMessageDetail(deviceMessage.getId(), DeviceMessageTypeEnum.MESSAGE, k, v));
        });
    }

    private void addDeviceMessageDetail(Integer messageId, DeviceMessageTypeEnum typeEnum, String paramName,
                                        Object paramValue) {
        DeviceMessageDetail deviceMessageDetail = new DeviceMessageDetail();
        deviceMessageDetail.setMessageId(messageId);
        deviceMessageDetail.setType(typeEnum.getType());
        deviceMessageDetail.setParamName(paramName);
        deviceMessageDetail.setParamValue(paramValue.toString());
        deviceMessageDetail.setCreateTime(new Date());

        deviceMessageDetailMapper.insertSelective(deviceMessageDetail);
    }

    @Override
    public boolean syncDevice(Device device) {
        // TODO:同步单个设备

        return false;
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
