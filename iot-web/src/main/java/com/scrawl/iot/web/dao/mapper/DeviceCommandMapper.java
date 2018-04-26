package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceCommand;
import org.apache.ibatis.annotations.Param;

public interface DeviceCommandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceCommand record);

    int insertSelective(DeviceCommand record);

    DeviceCommand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceCommand record);

    int updateByPrimaryKey(DeviceCommand record);

    DeviceCommand selectByDevSerialAndReqCommandId(@Param("devSerial") String devSerial,
                                                   @Param("commandId") String commandId);
}