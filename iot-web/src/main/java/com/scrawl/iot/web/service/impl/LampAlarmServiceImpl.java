package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.*;
import com.scrawl.iot.web.dao.mapper.DeviceAlarmRecordMapper;
import com.scrawl.iot.web.dao.mapper.DeviceExtMapper;
import com.scrawl.iot.web.enums.DeviceAlarmStatusEnum;
import com.scrawl.iot.web.enums.NoticeRecordStatusEnum;
import com.scrawl.iot.web.enums.NoticeTypeEnum;
import com.scrawl.iot.web.service.*;
import com.scrawl.iot.web.vo.iot.device.DeviceAlarmConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Service("lampAlarmService")
@Slf4j
public class LampAlarmServiceImpl extends AbstractAlarmService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceExtMapper deviceExtMapper;

    @Autowired
    private DeviceAlarmRecordMapper deviceAlarmRecordMapper;

    @Autowired
    private NoticeRecordService noticeRecordService;

    @Autowired
    private DeviceMessageService deviceMessageService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ManagerAccountService managerAccountService;

    private final String LAMP_ALARM_LOG_PREFIX = "[产品型号-Lamp]";

    @Override
    @Transactional
    public void alarm(Integer deviceId) {
        Assert.notNull(deviceId, "设备id不能为空");
        log.info(LAMP_ALARM_LOG_PREFIX + "设备[{}]预警", deviceId);

        Device device = deviceService.get(deviceId);
        Assert.notNull(device, "设备[" + deviceId + "]不能为空");

        // 1、获取预警配置
        BigDecimal minPower, maxPower;
        int maxNoReportTime;// 最大未上报时间（h）
        try {
            List<DeviceAlarmConfigVO> alarmConfigs = deviceService.getAlarmConfig(deviceId);
            Map<String, DeviceAlarmConfigVO> alarmConfigMap = alarmConfigs.stream().collect(
                    Collectors.toMap(DeviceAlarmConfigVO::getParamKey, Function.identity()));

            DeviceAlarmConfigVO minPowerConfig = alarmConfigMap.get("minPower");
            DeviceAlarmConfigVO maxPowerConfig = alarmConfigMap.get("maxPower");
            DeviceAlarmConfigVO maxNoReportTimeConfig = alarmConfigMap.get("maxNoReportTime");

            minPower = StringUtils.isEmpty(minPowerConfig.getParamValue()) ?
                    new BigDecimal(minPowerConfig.getSysParamValue()) : new BigDecimal(minPowerConfig.getParamValue());

            maxPower = StringUtils.isEmpty(maxPowerConfig.getParamValue()) ?
                    new BigDecimal(maxPowerConfig.getSysParamValue()) : new BigDecimal(maxPowerConfig.getParamValue());

            maxNoReportTime = StringUtils.isEmpty(maxNoReportTimeConfig.getParamValue()) ?
                    Integer.parseInt(maxNoReportTimeConfig.getSysParamValue()) : Integer.parseInt(maxNoReportTimeConfig.getParamValue());
        } catch (Exception e) {
            log.error(LAMP_ALARM_LOG_PREFIX + "获取设备[{}]配置异常", e);
            return;
        }

        // 2、获取设备最近消息
        DeviceSync deviceSync = deviceMessageService.getLastDeviceSync(deviceId);

        // 2.1、若设备无上报消息，则判断“当前时间”是否超过“设备创建时间”|"最近上报消息时间"加上“最大未上报时间（h）”
        // 当前时间
        DateTime now = DateTime.now();
        // 最近上报时间（若无则取设备创建时间）
        DateTime lastReportTime = new DateTime(Optional.ofNullable(deviceSync).map(DeviceSync::getCreateTime).
                orElse(device.getCreateTime()).getTime());

        if (null == deviceSync && !now.isAfter(lastReportTime.withFieldAdded(DurationFieldType.hours(), maxNoReportTime))) {
            return;
        }

        // 判断“当前时间”是否超过“设备创建时间”|"最近上报消息时间"加上“最大未上报时间（h）”
        if (now.isAfter(lastReportTime.withFieldAdded(DurationFieldType.hours(), maxNoReportTime))) {
            alarmHandler(deviceId, DeviceAlarmStatusEnum.ALARM, String.format("设备[%s]超过[%d]小时未上报消息",
                    device.getDevSerial(), maxNoReportTime));
            return;
        }

        // TODO[as]：暂时仅考虑一套消息模板
        List<DeviceMessage> deviceMessages = deviceMessageService.getDeviceMessageBySync(deviceSync.getId());
        List<DeviceMessageDetail> messageDetails = deviceMessageService.getDeviceMessageDetailsByMsg(deviceMessages.get(0).getId());
        Map<String, DeviceMessageDetail> messageDetailMap = messageDetails.stream().collect(Collectors.toMap(
                DeviceMessageDetail::getParamName, Function.identity()));

        // 开关未全部关闭
        DeviceMessageDetail switch1Message = messageDetailMap.get("Switch1");
        DeviceMessageDetail switch2Message = messageDetailMap.get("Switch2");
        if (switch1Message.getParamValue().equals("off") &&
                switch2Message.getParamValue().equals("off")) {
            log.info(LAMP_ALARM_LOG_PREFIX + "设备[{}]所有开关未开启无需预警处理", deviceId);
            alarmHandler(deviceId, DeviceAlarmStatusEnum.NOT_ALARM, "");
            return;
        }

        // 功率值
        BigDecimal power;
        try {
            power = new BigDecimal(messageDetailMap.get("Power").getParamValue());
        } catch (NumberFormatException e) {
            log.error(LAMP_ALARM_LOG_PREFIX + "设备[{}]获取功率信息异常", e);
            alarmHandler(deviceId, DeviceAlarmStatusEnum.NOT_ALARM, "设备消息获取功率信息异常");
            return;
        }


        // 3、判断是否需要预警
        if (power.compareTo(minPower) >= 0 && power.compareTo(maxPower) <= 0) {
            log.error(LAMP_ALARM_LOG_PREFIX + "设备[{}]功率信息[{}]正常，无需预警", device.getDevSerial(), power);
            alarmHandler(deviceId, DeviceAlarmStatusEnum.NOT_ALARM, "");
            return;
        } else {
            alarmHandler(deviceId, DeviceAlarmStatusEnum.NOT_ALARM, String.format("设备[%s]功率信息[%smA]在预警范围之外(min[%smA], max[%smA])",
                    device.getDevSerial(), power.toPlainString(), minPower.toPlainString(), maxPower.toPlainString()));
        }
    }

    private void alarmHandler(Integer deviceId, DeviceAlarmStatusEnum alarmStatus, String desc) {
        DeviceExt deviceExt = deviceExtMapper.selectByDeviceId(deviceId);
        Byte oldAlarmStatus = deviceExt.getAlarmStatus();
        if (alarmStatus.equals(DeviceAlarmStatusEnum.NOT_ALARM) && deviceExt.getAlarmStatus() == alarmStatus.getStatus()) {
            return;
        }

        Date now = new Date();

        deviceExt.setAlarmStatus(alarmStatus.getStatus());
        deviceExt.setAlarmDesc(desc);
        deviceExt.setAlarmTime(alarmStatus.equals(DeviceAlarmStatusEnum.ALARM) ? now : null);
        deviceExt.setUpdateTime(now);
        deviceExtMapper.updateByPrimaryKey(deviceExt);

        // 插入预警记录
        if (alarmStatus.equals(DeviceAlarmStatusEnum.ALARM)) {
            DeviceAlarmRecord alarmRecord = new DeviceAlarmRecord();
            alarmRecord.setDeviceId(deviceId);
            alarmRecord.setAlarmDesc(desc);
            alarmRecord.setCreateTime(now);

            deviceAlarmRecordMapper.insertSelective(alarmRecord);
        }

        // 发送消息通知，未预警状态置位预警状态才许发送消息通知
        if (oldAlarmStatus.equals(DeviceAlarmStatusEnum.NOT_ALARM.getStatus()) &&
                alarmStatus.equals(DeviceAlarmStatusEnum.ALARM)) {
            CompletableFuture.runAsync(() -> {
                try {
                    // 获取待发送管理员
                    Device device = deviceService.get(deviceId);
                    Account account = accountService.get(device.getServerId());
                    List<ManagerAccount> managerAccounts = managerAccountService.listByAccountId(account.getId());
                    if (null == managerAccounts || managerAccounts.size() == 0) {
                        return;
                    }

                    managerAccounts.forEach(managerAccount -> {
                        // 判断endUserName
                        String endUserNameStr = managerAccount.getEndUserName();
                        if (StringUtils.isNotBlank(endUserNameStr)) {
                            List<String> endUserNames = Arrays.asList(endUserNameStr.split(","));
                            if (StringUtils.isNotBlank(device.getEndUserName()) && !endUserNames.contains(device.getEndUserName())) {
                                return;
                            }
                        }

                        // 添加通知
                        NoticeRecord noticeRecord = new NoticeRecord();
                        noticeRecord.setManagerId(managerAccount.getManagerId());
                        noticeRecord.setType(NoticeTypeEnum.ALARM.getType());
                        noticeRecord.setTitle(String.format(LAMP_ALARM_LOG_PREFIX + "设备[%s]预警", device.getDevSerial()));
                        noticeRecord.setContent(desc);
                        noticeRecord.setStatus(NoticeRecordStatusEnum.NOT_READ.getCode());
                        noticeRecord.setCreateTime(now);
                        noticeRecordService.save(noticeRecord);

                        // 发送通知
                        noticeRecordService.sendNotice(noticeRecord);
                    });
                } catch (Exception e) {
                    log.error("设备[" + deviceId + "]发送预警消息异常", e);
                }
            });
        }
    }
}
