package com.scrawl.iot.web.task;

import com.scrawl.iot.web.dao.entity.DevType;
import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.service.DevTypeService;
import com.scrawl.iot.web.service.DeviceAlarmFactory;
import com.scrawl.iot.web.service.DeviceAlarmService;
import com.scrawl.iot.web.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Component
@Slf4j
public class DeviceAlarmJob implements Job {

    @Autowired
    private DevTypeService devTypeService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceAlarmFactory deviceAlarmFactory;

    private final String JOB_LOG_PREFIX = "设备预警job";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(JOB_LOG_PREFIX + "开始");
        // 1、获取所以需要预警的设备型号
        List<DevType> devTypes = devTypeService.getAlarmDevTypes();
        if (devTypes.size() == 0) {
            log.debug(JOB_LOG_PREFIX + "未获取到需要预警的设备型号");
            return;
        }

        // 2、获取设备型号下的所有设备进行预警
        devTypes.forEach(devType -> {
            DeviceAlarmService deviceAlarmService = deviceAlarmFactory.getAlarmServiceByDevType(devType.getId());
            if (null == deviceAlarmService) {
                log.info(JOB_LOG_PREFIX + "产品型号[{}]未设置告警接口", devType.getId());
                return;
            }

            List<Device> devices = deviceService.getByServerAndDevType(devType.getServerId(), devType.getDevType());

            devices.forEach(device -> {
                try {
                    deviceAlarmService.alarm(device.getId(), devType.getId());
                } catch (Exception e) {
                    log.error(JOB_LOG_PREFIX + "产品型号[{}]设备[{}]告警异常", devType.getId(), device.getDevSerial());
                }
            });

        });
    }
}
