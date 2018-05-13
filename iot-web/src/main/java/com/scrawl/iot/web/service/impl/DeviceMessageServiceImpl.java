package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.DeviceBasicDetail;
import com.scrawl.iot.web.dao.entity.DeviceMessage;
import com.scrawl.iot.web.dao.entity.DeviceMessageDetail;
import com.scrawl.iot.web.dao.entity.DeviceSync;
import com.scrawl.iot.web.dao.mapper.DeviceBasicDetailMapper;
import com.scrawl.iot.web.dao.mapper.DeviceMessageDetailMapper;
import com.scrawl.iot.web.dao.mapper.DeviceMessageMapper;
import com.scrawl.iot.web.dao.mapper.DeviceSyncMapper;
import com.scrawl.iot.web.service.DeviceMessageService;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 设备消息接口
 * Created by as on 2018/5/13.
 */
@Service
@Logger
public class DeviceMessageServiceImpl implements DeviceMessageService {

    @Autowired
    private DeviceSyncMapper deviceSyncMapper;

    @Autowired
    private DeviceMessageMapper deviceMessageMapper;

    @Autowired
    private DeviceMessageDetailMapper deviceMessageDetailMapper;

    @Autowired
    private DeviceBasicDetailMapper deviceBasicDetailMapper;

    @Override
    public DeviceSync getLastDeviceSync(Integer deviceId) {
        return deviceSyncMapper.selectLastDeviceSync(deviceId);
    }

    @Override
    public List<DeviceMessage> getDeviceMessageBySync(Integer deviceSyncId) {
        return deviceMessageMapper.selectBySyncId(deviceSyncId);
    }

    @Override
    public List<DeviceMessageDetail> getDeviceMessageDetailsByMsg(Integer deviceMessageId) {
        return deviceMessageDetailMapper.selectByMessageId(deviceMessageId);
    }

    @Override
    public Map<String, List<DeviceMessageDetail>> getDeviceMessageDetailsBySync(Integer deviceSyncId) {
        List<DeviceMessage> deviceMessages = getDeviceMessageBySync(deviceSyncId);

        Map<String, List<DeviceMessageDetail>> messageDetailMap = new HashMap<>();
        if (null == deviceMessages) {
            return messageDetailMap;
        }

        deviceMessages.forEach(deviceMessage -> messageDetailMap.put(deviceMessage.getDevTypeMessageId(),
                getDeviceMessageDetailsByMsg(deviceMessage.getId())));

        return messageDetailMap;
    }

    @Override
    public List<DeviceBasicDetail> getDeviceBasicDetails(Integer deviceSyncId) {
        return deviceBasicDetailMapper.selectBySyncId(deviceSyncId);
    }
}
