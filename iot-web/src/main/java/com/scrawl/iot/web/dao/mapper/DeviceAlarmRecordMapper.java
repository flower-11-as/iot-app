package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceAlarmRecord;

public interface DeviceAlarmRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceAlarmRecord record);

    int insertSelective(DeviceAlarmRecord record);

    DeviceAlarmRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceAlarmRecord record);

    int updateByPrimaryKey(DeviceAlarmRecord record);
}