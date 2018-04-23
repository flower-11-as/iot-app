package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevTypeCommandParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevTypeCommandParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevTypeCommandParam record);

    int insertSelective(DevTypeCommandParam record);

    DevTypeCommandParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevTypeCommandParam record);

    int updateByPrimaryKey(DevTypeCommandParam record);

    DevTypeCommandParam selectByCommandIdAndName(@Param("commandId") Integer commandId, @Param("name") String name);

    List<DevTypeCommandParam> selectByCommandIdAndType(@Param("commandId")Integer commandId, @Param("type") Byte type);
}