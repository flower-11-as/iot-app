package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceAlarmConfig;
import org.apache.ibatis.annotations.Param;

public interface DeviceAlarmConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceAlarmConfig record);

    int insertSelective(DeviceAlarmConfig record);

    DeviceAlarmConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceAlarmConfig record);

    int updateByPrimaryKey(DeviceAlarmConfig record);

    DeviceAlarmConfig selectByDeviceIdAndParamKey(@Param("deviceId") Integer deviceId, @Param("paramKey") String paramKey);
}