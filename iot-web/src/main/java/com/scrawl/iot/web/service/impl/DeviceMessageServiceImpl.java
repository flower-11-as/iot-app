package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.*;
import com.scrawl.iot.web.dao.mapper.DeviceBasicDetailMapper;
import com.scrawl.iot.web.dao.mapper.DeviceMessageDetailMapper;
import com.scrawl.iot.web.dao.mapper.DeviceMessageMapper;
import com.scrawl.iot.web.dao.mapper.DeviceSyncMapper;
import com.scrawl.iot.web.service.DeviceMessageService;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.service.ParamService;
import com.scrawl.iot.web.vo.iot.device.DeviceReportRespVO;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ParamService paramService;

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

    @Override
    public DeviceReportRespVO getReportData(Integer deviceId) {
        // init
        DeviceReportRespVO reportData = new DeviceReportRespVO();
        List<String> legend = Arrays.asList("功率(W)", "电压(V)", "电流(mA)");
        List<String> xAxis = new ArrayList<>();
        Map<String, List<Float>> series = new HashMap<>();
        List<Float> powerList = new ArrayList<>();
        List<Float> voltageList = new ArrayList<>();
        List<Float> currentList = new ArrayList<>();
        series.put("功率(W)", powerList);
        series.put("电压(V)", voltageList);
        series.put("电流(mA)", currentList);

        reportData.setTitle("设备基本信息");
        reportData.setLegend(legend);
        reportData.setXaxis(xAxis);
        reportData.setSeries(series);

        Device device = deviceService.get(deviceId);
        // 获取近24小内，设备基本信息
        // 获取系统设置报表步长，默认步长为2
        int stepSize = Optional.ofNullable(paramService.get("stepSize")).
                map(Param::getParamValue).
                map(Integer::parseInt).
                orElse(2);
        int width = Optional.ofNullable(paramService.get("width")).
                map(Param::getParamValue).
                map(Integer::parseInt).
                orElse(12);

        DateTime endTime = DateTime.now().withMinuteOfHour(59).withSecondOfMinute(59).minusHours(stepSize*width);
        for (int i=1; i <= width; i++) {
            endTime = endTime.plusHours(2);
            // 设置x轴坐标
            xAxis.add(endTime.toString("MM月dd日HH时"));

            // 设置数据
            // 获取截止时间最近一条设备消息
            DeviceSync deviceSync = deviceSyncMapper.selectLastDeviceSyncWithEndTime(deviceId, endTime.toDate());
            if (null == deviceSync) {
                powerList.add(0f);
                voltageList.add(0f);
                currentList.add(0f);
            } else {
                // TODO[as]：暂时仅考虑一套消息模板
                List<DeviceMessage> deviceMessages = this.getDeviceMessageBySync(deviceSync.getId());
                List<DeviceMessageDetail> messageDetails = this.getDeviceMessageDetailsByMsg(deviceMessages.get(0).getId());
                Map<String, DeviceMessageDetail> messageDetailMap = messageDetails.stream().collect(Collectors.toMap(
                        DeviceMessageDetail::getParamName, Function.identity()));
                powerList.add(Optional.ofNullable(messageDetailMap).
                        map((m)-> m.get("Power")).
                        map(DeviceMessageDetail::getParamValue).
                        map(Float::parseFloat).
                        orElse(0f));
                voltageList.add(Optional.ofNullable(messageDetailMap).
                        map((m)-> m.get("Voltage")).
                        map(DeviceMessageDetail::getParamValue).
                        map(Float::parseFloat).
                        orElse(0f));
                currentList.add(Optional.ofNullable(messageDetailMap).
                        map((m)-> m.get("Current")).
                        map(DeviceMessageDetail::getParamValue).
                        map(Float::parseFloat).
                        orElse(0f));
            }
        }

        return reportData;
    }
}
