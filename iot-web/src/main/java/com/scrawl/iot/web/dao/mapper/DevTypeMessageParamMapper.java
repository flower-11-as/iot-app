package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevTypeMessageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevTypeMessageParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevTypeMessageParam record);

    int insertSelective(DevTypeMessageParam record);

    DevTypeMessageParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevTypeMessageParam record);

    int updateByPrimaryKey(DevTypeMessageParam record);

    DevTypeMessageParam selectByMessageIdAndName(@Param("messageId") Integer messageId, @Param("name") String name);

    List<DevTypeMessageParam> selectByMessageId(Integer messageId);
}