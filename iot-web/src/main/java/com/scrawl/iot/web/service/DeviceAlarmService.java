package com.scrawl.iot.web.service;

/**
 * Description:
 * Created by as on 2018/4/29.
 */
public interface DeviceAlarmService {

    void alarm(Integer deviceId, Integer devTypeId);
}
