package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Param;
import com.scrawl.iot.web.vo.sys.param.ParamListReqVO;

import java.util.List;

public interface ParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Param record);

    int insertSelective(Param record);

    Param selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Param record);

    int updateByPrimaryKey(Param record);

    List<Param> selectPageList(ParamListReqVO req);

    int selectPageCount(ParamListReqVO req);

    List<Param> selectBySelective(Param record);

    Param selectByParamKey(String paramKey);
}