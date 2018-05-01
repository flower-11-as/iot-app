package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.constants.ParamConstant;
import com.scrawl.iot.web.dao.entity.*;
import com.scrawl.iot.web.dao.mapper.DeviceAlarmRecordMapper;
import com.scrawl.iot.web.dao.mapper.DeviceExtMapper;
import com.scrawl.iot.web.service.AbstractAlarmService;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.service.ParamService;
import com.scrawl.iot.web.vo.iot.device.DeviceAlarmConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Service
@Slf4j
public class LampAlarmServiceImpl extends AbstractAlarmService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceExtMapper deviceExtMapper;

    @Autowired
    private DeviceAlarmRecordMapper deviceAlarmRecordMapper;

    private final String LAMP_ALARM_LOG_PREFIX = "Lamp产品型号";

    @Override
    public void alarm(Integer deviceId) {
        Assert.notNull(deviceId, "设备id不能为空");
        log.info(LAMP_ALARM_LOG_PREFIX + "设备[{}]预警", deviceId);

        Device device = deviceService.get(deviceId);
        Assert.notNull(device, "设备不能为空");

        // 1、获取最近消息
        Map<String, DeviceMessageDetail> messageDetailMap = deviceService.getMessageInfo(deviceId);
        if (null == messageDetailMap) {
            log.info(LAMP_ALARM_LOG_PREFIX + "设备[{}]无消息上报", deviceId);
            alarmHandler(deviceId, (byte) 0, "");
            return;
        }

        // 开关未全部关闭
        DeviceMessageDetail switch1Message = messageDetailMap.get("Switch1");
        DeviceMessageDetail switch2Message = messageDetailMap.get("Switch2");
        if (switch1Message.getParamValue().equals("off") &&
                switch2Message.getParamValue().equals("off")) {
            log.info(LAMP_ALARM_LOG_PREFIX + "设备[{}]所有开关未开启无需预警处理", deviceId);
            alarmHandler(deviceId, (byte) 0, "");
            return;
        }

        // 电流值
        BigDecimal current;
        try {
            current = new BigDecimal(messageDetailMap.get("Current").getParamValue());
        } catch (NumberFormatException e) {
            log.error(LAMP_ALARM_LOG_PREFIX + "设备[{}]获取电流消息异常", e);
            alarmHandler(deviceId, (byte) 1, "设备消息获取电流信息异常");
            return;
        }

        // 2、获取预警配置
        List<DeviceAlarmConfigVO> alarmConfigs = deviceService.getAlarmConfig(deviceId);
        if (null == alarmConfigs || alarmConfigs.size() == 0) {
            log.info(LAMP_ALARM_LOG_PREFIX + "设备[{}]未获取到预警配置", deviceId);
            alarmHandler(deviceId, (byte) 0, "");
            return;
        }

        BigDecimal minCurrent, maxCurrent;

        try {
            Map<String, DeviceAlarmConfigVO> alarmConfigMap = alarmConfigs.stream().collect(
                    Collectors.toMap(DeviceAlarmConfigVO::getParamKey, Function.identity()));

            DeviceAlarmConfigVO minCurrentConfig = alarmConfigMap.get("minCurrent");
            DeviceAlarmConfigVO maxCurrentConfig = alarmConfigMap.get("maxCurrent");

            minCurrent = StringUtils.isEmpty(minCurrentConfig.getParamValue()) ?
                    new BigDecimal(minCurrentConfig.getSysParamValue()) : new BigDecimal(minCurrentConfig.getParamKey());

            maxCurrent = StringUtils.isEmpty(maxCurrentConfig.getParamValue()) ?
                    new BigDecimal(maxCurrentConfig.getSysParamValue()) : new BigDecimal(maxCurrentConfig.getParamKey());
        } catch (Exception e) {
            log.error(LAMP_ALARM_LOG_PREFIX + "设备[{}]获取预警电流配置异常", e);
            alarmHandler(deviceId, (byte) 1, "获取预警电流配置异常");
            return;
        }

        // 3、判断是否需要预警
        if (current.compareTo(minCurrent) >= 0 || current.compareTo(maxCurrent) <= 0) {
            alarmHandler(deviceId, (byte) 0, "");
            return;
        }

        // 4、预警处理
        alarmHandler(deviceId, (byte) 1, String.format("设备消息电流信息在预警范围之外(min[%s], max[%s])", minCurrent.toPlainString(),
                maxCurrent.toPlainString()));
    }

    private void alarmHandler(Integer deviceId, Byte alarmStatus, String desc) {
        DeviceExt deviceExt = deviceExtMapper.selectByDeviceId(deviceId);
        if (deviceExt.getAlarmStatus() == alarmStatus.byteValue()) {
            return;
        }

        Date now = new Date();

        deviceExt.setAlarmStatus(alarmStatus);
        deviceExt.setAlarmDesc(desc);
        if (alarmStatus == 1) {
            deviceExt.setAlarmTime(now);
        }
        deviceExt.setUpdateTime(now);
        deviceExtMapper.updateByPrimaryKeySelective(deviceExt);

        // 插入预警记录
        if (alarmStatus == 1) {
            DeviceAlarmRecord alarmRecord = new DeviceAlarmRecord();
            alarmRecord.setDeviceId(deviceId);
            alarmRecord.setAlarmDesc(desc);
            alarmRecord.setCreateTime(now);

            deviceAlarmRecordMapper.insertSelective(alarmRecord);
        }
    }
}
