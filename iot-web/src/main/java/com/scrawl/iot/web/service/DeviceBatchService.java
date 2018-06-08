package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.DeviceBatch;
import com.scrawl.iot.web.vo.PageReqVO;

import java.io.InputStream;
import java.util.List;

/**
 * Desc:
 * Create by scrawl on 2018/6/8
 */
public interface DeviceBatchService {

    List<DeviceBatch> list(PageReqVO reqVO);

    int count(PageReqVO reqVO);

    void batchAdd(InputStream is, Integer managerId);

    DeviceBatch getByTaskId(String taskId);

    void update(DeviceBatch deviceBatch);
}
