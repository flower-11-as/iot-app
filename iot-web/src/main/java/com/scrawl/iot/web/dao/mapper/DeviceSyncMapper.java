package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceSync;

public interface DeviceSyncMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceSync record);

    int insertSelective(DeviceSync record);

    DeviceSync selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceSync record);

    int updateByPrimaryKey(DeviceSync record);
}