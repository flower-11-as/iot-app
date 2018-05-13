package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceBasicDetail;

import java.util.List;

public interface DeviceBasicDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceBasicDetail record);

    int insertSelective(DeviceBasicDetail record);

    DeviceBasicDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceBasicDetail record);

    int updateByPrimaryKey(DeviceBasicDetail record);

    @Deprecated
    List<DeviceBasicDetail> selectBaseSensorInfo(Integer deviceId);

    List<DeviceBasicDetail> selectBySyncId(Integer syncId);
}