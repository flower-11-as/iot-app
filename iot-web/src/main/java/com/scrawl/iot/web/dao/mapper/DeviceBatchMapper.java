package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.DeviceBatch;
import com.scrawl.iot.web.vo.PageReqVO;

import java.util.List;

public interface DeviceBatchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceBatch record);

    int insertSelective(DeviceBatch record);

    DeviceBatch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceBatch record);

    int updateByPrimaryKeyWithBLOBs(DeviceBatch record);

    int updateByPrimaryKey(DeviceBatch record);

    List<DeviceBatch> selectPageList(PageReqVO reqVO);

    int selectPageCount(PageReqVO reqVO);

    DeviceBatch selectByTaskId(String taskId);
}