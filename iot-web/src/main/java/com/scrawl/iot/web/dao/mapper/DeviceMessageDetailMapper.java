package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceMessageDetail;

import java.util.List;

public interface DeviceMessageDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceMessageDetail record);

    int insertSelective(DeviceMessageDetail record);

    DeviceMessageDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceMessageDetail record);

    int updateByPrimaryKey(DeviceMessageDetail record);

    List<DeviceMessageDetail> selectMessageInfo(Integer deviceId);

    String selectDataType(Integer id);
}