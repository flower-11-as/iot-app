package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DevType;
import com.scrawl.iot.web.vo.iot.devType.DevTypeListReqVO;

import java.util.List;

public interface DevTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevType record);

    int insertSelective(DevType record);

    DevType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevType record);

    int updateByPrimaryKey(DevType record);

    List<DevType> selectBySelective(DevType record);

    List<DevType> selectPageList(DevTypeListReqVO reqVO);

    int selectPageCount(DevTypeListReqVO reqVO);
}