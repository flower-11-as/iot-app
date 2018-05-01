package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.DevType;
import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.dao.mapper.DevTypeMapper;
import com.scrawl.iot.web.dao.mapper.DeviceMapper;
import com.scrawl.iot.web.helper.ApplicationContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by as on 2018/4/29.
 */
@Component
@Slf4j
public class DeviceAlarmFactory {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DevTypeMapper devTypeMapper;

    public DeviceAlarmService getAlarmServiceByDevice(Integer deviceId) {
        Assert.notNull(deviceId, "设备id不能为空");

        Device device = deviceMapper.selectByPrimaryKey(deviceId);
        Assert.notNull(device, "设备不能为空");

        DevType devType = devTypeMapper.selectByServerIdAndDevType(device.getServerId(), device.getDevType());
        Assert.notNull(devType, "产品型号不能为空");

        if (StringUtils.isEmpty(devType.getAlarmService())) {
            log.info("设备[{}]对应产品型号未设置告警接口", deviceId);
            return null;
        }

        return ApplicationContextHelper.getBean(devType.getAlarmService());
    }

    public DeviceAlarmService getAlarmServiceByDevType(Integer devTypeId) {
        Assert.notNull(devTypeId, "设备型号不能为空");

        DevType devType = devTypeMapper.selectByPrimaryKey(devTypeId);
        Assert.notNull(devType, "产品型号不能为空");

        if (StringUtils.isEmpty(devType.getAlarmService())) {
            log.info("产品型号[{}]未设置告警接口", devTypeId);
            return null;
        }

        return ApplicationContextHelper.getBean(devType.getAlarmService());
    }
}
