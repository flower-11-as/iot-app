package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevTypeCommand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevTypeCommandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevTypeCommand record);

    int insertSelective(DevTypeCommand record);

    DevTypeCommand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevTypeCommand record);

    int updateByPrimaryKey(DevTypeCommand record);

    DevTypeCommand selectByTypeIdAndName(@Param("typeId") Integer typeId, @Param("name") String name);

    List<DevTypeCommand> selectByTypeId(Integer typeId);
}