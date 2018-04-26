package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevTypeMessageParamExt;

public interface DevTypeMessageParamExtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevTypeMessageParamExt record);

    int insertSelective(DevTypeMessageParamExt record);

    DevTypeMessageParamExt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevTypeMessageParamExt record);

    int updateByPrimaryKey(DevTypeMessageParamExt record);
}