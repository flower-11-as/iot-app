package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceMessage;

public interface DeviceMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceMessage record);

    int insertSelective(DeviceMessage record);

    DeviceMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceMessage record);

    int updateByPrimaryKey(DeviceMessage record);
}