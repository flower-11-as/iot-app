package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.http.request.IotCommandRequest;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.request.IotRegDeviceRequest;
import com.scrawl.iot.paper.http.response.IotCommandResponse;
import com.scrawl.iot.paper.http.response.IotDeviceAllResponse;
import com.scrawl.iot.paper.http.response.IotDeviceResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.paper.utils.IotDataCastUtil;
import com.scrawl.iot.web.constants.ParamConstant;
import com.scrawl.iot.web.dao.entity.*;
import com.scrawl.iot.web.dao.mapper.*;
import com.scrawl.iot.web.enums.DeviceSyncTypeEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.DeviceMessageService;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.service.ParamService;
import com.scrawl.iot.web.vo.iot.callback.IotDataReportReqVO;
import com.scrawl.iot.web.vo.iot.device.DeviceAlarmConfigVO;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;
import com.scrawl.iot.web.vo.iot.device.DeviceListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private DevTypeMessageMapper devTypeMessageMapper;

    @Autowired
    private DevTypeMessageParamMapper devTypeMessageParamMapper;

    @Autowired
    private DeviceCommandMapper deviceCommandMapper;

    @Autowired
    private DeviceCommandDetailMapper deviceCommandDetailMapper;

    @Autowired
    private DevTypeCommandParamMapper devTypeCommandParamMapper;

    @Autowired
    private DevTypeCommandMapper devTypeCommandMapper;

    @Autowired
    private DeviceExtMapper deviceExtMapper;

    @Autowired
    private ParamService paramService;

    @Autowired
    private DeviceAlarmConfigMapper deviceAlarmConfigMapper;

    @Autowired
    private DeviceMessageService deviceMessageService;

    private final String SERVICE_ID_BATTERY = "Battery";
    private final String SERVICE_ID_METER = "Meter";

    @Override
    public List<DeviceListRespVO> list(DeviceListReqVO reqVO) {
        List<DeviceListRespVO> list = deviceMapper.selectPageList(reqVO);
        list.forEach(device -> {
            DeviceSync deviceSync = deviceMessageService.getLastDeviceSync(device.getId());
            device.setLastReportTime(null != deviceSync ? deviceSync.getCreateTime() : null);
        });
        return list;
    }

    @Override
    public int count(DeviceListReqVO reqVO) {
        return deviceMapper.selectPageCount(reqVO);
    }

    @Override
    public void save(Device device) {
        // 调用iot接口
        DevType devType = devTypeMapper.selectByServerIdAndDevType(device.getServerId(), device.getDevType());

        Account account = accountService.getAndAuthAccount(devType.getServerId());
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        IotRegDeviceRequest request = new IotRegDeviceRequest();
        request.setDevSerial(device.getDevSerial());
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

        // 插入设备扩展
        DeviceExt deviceExt = new DeviceExt();
        deviceExt.setDeviceId(device.getId());
        deviceExt.setCreateTime(device.getCreateTime());
        deviceExt.setUpdateTime(device.getCreateTime());

        deviceExtMapper.insertSelective(deviceExt);
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
        while (pageNum == 1 || size == 100) {
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

            // 插入设备扩展
            DeviceExt deviceExt = new DeviceExt();
            deviceExt.setDeviceId(device.getId());
            deviceExt.setCreateTime(device.getCreateTime());
            deviceExt.setUpdateTime(device.getCreateTime());

            deviceExtMapper.insertSelective(deviceExt);
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
            deviceMessage.setDevTypeMessageId(deviceData.getServiceId());
            deviceMessage.setCreateTime(new Date());
            deviceMessageMapper.insertSelective(deviceMessage);

            // 根据message_id, dev_type 获取devTypeMessage
            DevTypeMessage devTypeMessage = devTypeMessageMapper.selectByDevTypeAndMessageId(finalDevice.getDevType(), deviceData.getServiceId());

            // 消息详情落地
            deviceData.getServiceData().forEach((k, v) -> addDeviceMessageDetail(deviceMessage.getId(), k, v, devTypeMessage));
        });
    }

    @Override
    public void deviceDataReport(IotDataReportReqVO reqVO) {
        Device device = deviceMapper.selectByDevSerial(reqVO.getDevSerial());
        if (null == device) {
            return;
        }

        // device sync落地
        DeviceSync deviceSync = new DeviceSync();
        deviceSync.setDeviceId(device.getId());
        deviceSync.setType(DeviceSyncTypeEnum.MANAGER.getType());
        deviceSync.setCreateTime(new Date());
        deviceSyncMapper.insertSelective(deviceSync);

//        {'serviceId': 'Battery', 'serviceData': {'batteryLevel': 10}}, //电量
//        {'serviceId': 'Meter', 'serviceData': {'signalStrength': -11}}, //信号

        List<IotDataReportReqVO.IotDeviceData> serviceData = reqVO.getServiceData();
        if (null == serviceData || serviceData.size() == 0) {
            return;
        }

        serviceData.forEach(deviceData -> {
            // 落地basic
            if (("Meter").equals(deviceData.getServiceId())) {
                addDeviceBasicDetail(device.getId(), deviceSync.getId(), "batteryLevel", deviceData.getServiceData().get("batteryLevel"));
            } else if (("Battery").equals(deviceData.getServiceId())) {
                addDeviceBasicDetail(device.getId(), deviceSync.getId(), "signalStrength", deviceData.getServiceData().get("signalStrength"));
            }
            // 落地message
            else {
                // 消息落地
                DeviceMessage deviceMessage = new DeviceMessage();
                deviceMessage.setDeviceId(device.getId());
                deviceMessage.setSyncId(deviceSync.getId());
                deviceMessage.setDevTypeMessageId(deviceData.getServiceId());
                deviceMessage.setCreateTime(new Date());
                deviceMessageMapper.insertSelective(deviceMessage);

                // 根据message_id, dev_type 获取devTypeMessage
                DevTypeMessage devTypeMessage = devTypeMessageMapper.selectByDevTypeAndMessageId(device.getDevType(), deviceData.getServiceId());

                // 消息详情落地
                deviceData.getServiceData().forEach((k, v) -> addDeviceMessageDetail(deviceMessage.getId(), k, v, devTypeMessage));
            }
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

    private void addDeviceMessageDetail(Integer messageId, String paramName, Object paramValue, DevTypeMessage devTypeMessage) {
        // data type, message_id,dev_type

        DeviceMessageDetail deviceMessageDetail = new DeviceMessageDetail();
        deviceMessageDetail.setMessageId(messageId);
        deviceMessageDetail.setParamName(paramName);
        if (null != devTypeMessage) {
            DevTypeMessageParam devTypeMessageParam = devTypeMessageParamMapper.selectByMessageIdAndName(devTypeMessage.getId(), paramName);
            deviceMessageDetail.setDataType(Optional.ofNullable(devTypeMessageParam.getDataType()).orElse(null));
        }
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
                deviceMessage.setDevTypeMessageId(deviceData.getServiceId());
                deviceMessage.setCreateTime(new Date());
                deviceMessageMapper.insertSelective(deviceMessage);

                // 根据message_id, dev_type 获取devTypeMessage
                DevTypeMessage devTypeMessage = devTypeMessageMapper.selectByDevTypeAndMessageId(device.getDevType(), deviceData.getServiceId());

                // 消息详情落地
                deviceData.getServiceData().forEach((k, v) -> addDeviceMessageDetail(deviceMessage.getId(), k, v, devTypeMessage));
            }
        });

    }

    @Override
    public void remove(Integer id, Integer managerId) {
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
        iotHttpService.delDevice(params, header);

        deviceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void sendCommand(Map<String, Object> command, Integer deviceId, String commandId) {
        Device device = get(deviceId);
        Account account = accountService.getAndAuthAccount(device.getServerId());
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());

        DevTypeCommand devTypeCommand = devTypeCommandMapper.selectByTypeIdAndCommandId(
                devTypeMapper.selectByServerIdAndDevType(account.getServerId(), device.getDevType()).getId(), commandId);
        command.forEach((k, v) -> {
            DevTypeCommandParam commandParam = devTypeCommandParamMapper.selectByCommandIdAndName(devTypeCommand.getId(), k);
            command.put(k, IotDataCastUtil.commonReversion(v.toString(), commandParam.getDataType()));
        });

        IotCommandRequest request = new IotCommandRequest();
        request.setMethod(commandId);
        request.setDevSerial(device.getDevSerial());
        request.setParams(command);
        IotCommandResponse response = iotHttpService.command(header, request);

        // 插入command，及其detail
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.setDeviceId(deviceId);
        deviceCommand.setReqCommandId(response.getCommandId());
        deviceCommand.setStatus((byte) 0);
        deviceCommand.setCreateTime(new Date());
        deviceCommandMapper.insertSelective(deviceCommand);

        // 插入detail
        command.forEach((k, v) -> {
            DeviceCommandDetail detail = new DeviceCommandDetail();
            detail.setCommandId(deviceCommand.getId());
            detail.setParamName(k);
            detail.setParamValue(v.toString());
            detail.setCreateTime(new Date());
            deviceCommandDetailMapper.insertSelective(detail);
        });
    }

    @Override
    public Device get(Integer id) {
        return deviceMapper.selectByPrimaryKey(id);
    }

    public Map<String, DeviceBasicDetail> getBaseSensorInfo(Integer deviceId) {
        List<DeviceBasicDetail> deviceBasicDetails = deviceBasicDetailMapper.selectBaseSensorInfo(deviceId);
        return deviceBasicDetails.stream().collect(Collectors.toMap(DeviceBasicDetail::getParamName, DeviceBasicDetail -> DeviceBasicDetail));
    }

    @Override
    public Map<String, DeviceMessageDetail> getMessageInfo(Integer deviceId) {
        List<DeviceMessageDetail> deviceMessageDetails = deviceMessageDetailMapper.selectMessageInfo(deviceId);
        deviceMessageDetails.forEach(deviceMessageDetail -> {
            deviceMessageDetail.setParamValue(IotDataCastUtil.commonCast(deviceMessageDetail.getParamValue(), deviceMessageDetail.getDataType()));
        });
        return deviceMessageDetails.stream().collect(Collectors.toMap(DeviceMessageDetail::getParamName, Function.identity()));
    }

    @Override
    public List<DeviceAlarmConfigVO> getAlarmConfig(Integer deviceId) {
        Device device = get(deviceId);

        DevType devType = devTypeMapper.selectByServerIdAndDevType(device.getServerId(), device.getDevType());

        Param record = new Param();
        record.setGroup(ParamConstant.PARAM_DEV_TYPE_ALARM_PREFIX + devType.getId());
        List<Param> params = paramService.list(record);

        List<DeviceAlarmConfigVO> alarmConfigs = new ArrayList<>();
        params.forEach(param -> {
            DeviceAlarmConfigVO alarmConfig = new DeviceAlarmConfigVO();
            alarmConfig.setDescription(param.getDescription());
            alarmConfig.setParamKey(param.getParamKey());
            alarmConfig.setSysParamValue(param.getParamValue());

            DeviceAlarmConfig deviceAlarmConfig = deviceAlarmConfigMapper.selectByDeviceIdAndParamKey(deviceId, param.getParamKey());
            alarmConfig.setParamValue(null != deviceAlarmConfig ? deviceAlarmConfig.getParamValue() : "");

            alarmConfigs.add(alarmConfig);
        });

        return alarmConfigs;
    }

    @Override
    @Transactional
    public void saveAlarmConfig(Integer deviceId, Map<String, String> params, Integer managerId) {
        if (null == params) {
            return;
        }

        Date now = new Date();
        params.forEach((k, v) -> {
            Param param = paramService.get(k);
            if (null == param || StringUtils.isEmpty(v)) {
                return;
            }

            DeviceAlarmConfig deviceAlarmConfig = deviceAlarmConfigMapper.selectByDeviceIdAndParamKey(deviceId, k);
            if (null == deviceAlarmConfig) {
                deviceAlarmConfig = new DeviceAlarmConfig();
                deviceAlarmConfig.setDeviceId(deviceId);
                deviceAlarmConfig.setParamKey(k);
                deviceAlarmConfig.setParamValue(v);
                deviceAlarmConfig.setDescription(param.getDescription());
                deviceAlarmConfig.setCreateManager(managerId);
                deviceAlarmConfig.setCreateTime(now);
                deviceAlarmConfig.setUpdateTime(now);

                deviceAlarmConfigMapper.insertSelective(deviceAlarmConfig);
            } else {
                deviceAlarmConfig.setParamValue(v);
                deviceAlarmConfig.setUpdateManager(managerId);
                deviceAlarmConfig.setUpdateTime(now);

                deviceAlarmConfigMapper.updateByPrimaryKeySelective(deviceAlarmConfig);
            }
        });
    }

    @Override
    public List<Device> getByServerAndDevType(String serverId, String devType) {
        return deviceMapper.selectByServerAndDevType(serverId, devType);
    }
}
