package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Task;
import com.scrawl.iot.web.vo.sys.job.JobListReqVO;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/29.
 */
public interface JobService {
    Task get(Long id);

    List<Task> list(JobListReqVO req);

    int count(JobListReqVO req);

    int save(Task taskScheduleJob);

    int update(Task taskScheduleJob);

    int remove(Long id);

    void initSchedule() throws SchedulerException;

    void changeStatus(Long jobId, String cmd) throws SchedulerException;

    void updateCron(Long jobId) throws SchedulerException;
}
