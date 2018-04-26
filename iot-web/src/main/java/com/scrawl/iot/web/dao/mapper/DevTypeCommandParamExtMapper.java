package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevTypeCommandParamExt;

public interface DevTypeCommandParamExtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevTypeCommandParamExt record);

    int insertSelective(DevTypeCommandParamExt record);

    DevTypeCommandParamExt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevTypeCommandParamExt record);

    int updateByPrimaryKey(DevTypeCommandParamExt record);
}