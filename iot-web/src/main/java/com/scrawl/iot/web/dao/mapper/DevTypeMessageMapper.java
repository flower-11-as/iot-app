package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevTypeMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevTypeMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevTypeMessage record);

    int insertSelective(DevTypeMessage record);

    DevTypeMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevTypeMessage record);

    int updateByPrimaryKey(DevTypeMessage record);

    DevTypeMessage selectByTypeIdAndName(@Param("typeId") Integer typeId, @Param("name") String name);

    List<DevTypeMessage> selectByTypeId(Integer typeId);

    DevTypeMessage selectByDevTypeAndMessageId(@Param("devType") String devType, @Param("messageId") String messageId);
}