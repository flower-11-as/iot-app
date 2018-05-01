package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.constants.JobConstant;
import com.scrawl.iot.web.dao.entity.Task;
import com.scrawl.iot.web.dao.mapper.TaskMapper;
import com.scrawl.iot.web.quartz.QuartzManager;
import com.scrawl.iot.web.service.JobService;
import com.scrawl.iot.web.vo.sys.job.JobListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/29.
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private QuartzManager quartzManager;

    @Override
    public Task get(Long id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Task> list(JobListReqVO req) {
        return taskMapper.selectPageList(req);
    }

    @Override
    public int count(JobListReqVO req) {
        return taskMapper.selectCountList(req);
    }

    @Override
    public int save(Task taskScheduleJob) {
        return taskMapper.insertSelective(taskScheduleJob);
    }

    @Override
    public int update(Task taskScheduleJob) {
        return taskMapper.updateByPrimaryKeySelective(taskScheduleJob);
    }

    @Override
    @Transactional
    public int remove(Long id) {
        try {
            Task scheduleJob = get(id);
            quartzManager.deleteJob(scheduleJob);
            return taskMapper.deleteByPrimaryKey(id);
        } catch (SchedulerException e) {
            log.error("删除scheduler job异常", e);
            return 0;
        }
    }

    @Override
    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<Task> jobList = taskMapper.selectBySelective(new Task());
        for (Task scheduleJob : jobList) {
            if (JobConstant.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
                quartzManager.addJob(scheduleJob);
            }
        }
    }

    @Override
    public void changeStatus(Long jobId, String cmd) throws SchedulerException {
        Task scheduleJob = get(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (JobConstant.STATUS_RUNNING_STOP.equals(cmd)) {
            quartzManager.deleteJob(scheduleJob);
            scheduleJob.setJobStatus(JobConstant.STATUS_NOT_RUNNING);
        } else {
            if (JobConstant.STATUS_RUNNING_START.equals(cmd)) {
                scheduleJob.setJobStatus(JobConstant.STATUS_RUNNING);
                quartzManager.addJob(scheduleJob);
            }
        }
        update(scheduleJob);
    }

    @Override
    public void updateCron(Long jobId) throws SchedulerException {
        Task scheduleJob = get(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (JobConstant.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            quartzManager.updateJobCron(scheduleJob);
        }
        update(scheduleJob);
    }
}
