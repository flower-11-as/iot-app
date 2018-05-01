package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceExt;

public interface DeviceExtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceExt record);

    int insertSelective(DeviceExt record);

    DeviceExt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceExt record);

    int updateByPrimaryKey(DeviceExt record);
}