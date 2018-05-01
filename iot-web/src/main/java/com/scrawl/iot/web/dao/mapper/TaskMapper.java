package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Task;
import com.scrawl.iot.web.vo.sys.job.JobListReqVO;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    List<Task> selectPageList(JobListReqVO req);

    int selectCountList(JobListReqVO req);

    List<Task> selectBySelective(Task record);
}