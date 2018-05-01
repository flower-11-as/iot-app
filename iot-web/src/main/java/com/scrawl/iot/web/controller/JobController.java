package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Task;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.JobService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.sys.job.JobListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@RequestMapping("/sys/job")
@Controller
@Slf4j
public class JobController {

    private static final String prefix = "/system/job";

    @Autowired
    private JobService jobService;

    @GetMapping()
    String taskScheduleJob() {
        return prefix + "/job";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageRespVO<Task> list(JobListReqVO req) {
        PageRespVO<Task> pageRespVO = new PageRespVO<>();
        pageRespVO.setRows(jobService.list(req));
        pageRespVO.setTotal(jobService.count(req));
        return pageRespVO;
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Task job = jobService.get(id);
        model.addAttribute("job", job);
        return prefix + "/edit";
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        return R.ok(jobService.get(id));
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(Task taskScheduleJob) {
        try {
            if (jobService.save(taskScheduleJob) < 1) {
                throw new BizException("SYS11001");
            }
        } catch (Exception e) {
            log.error("添加job异常：", e);
            throw new BizException("SYS11001");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @PostMapping("/update")
    public R update(Task taskScheduleJob) {
        try {
            if (jobService.update(taskScheduleJob) < 1) {
                throw new BizException("SYS11002");
            }
        } catch (Exception e) {
            log.error("添加job异常：", e);
            throw new BizException("SYS11002");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        try {
            if (jobService.remove(id) < 1) {
                throw new BizException("SYS11003");
            }
        } catch (Exception e) {
            log.error("删除job异常：", e);
            throw new BizException("SYS11003");
        }
        return R.ok();
    }

    @PostMapping(value = "/changeJobStatus")
    @ResponseBody
    public R changeJobStatus(Long id, String cmd) {
        try {
            jobService.changeStatus(id, cmd);
        } catch (Exception e) {
            log.error("停止启动job异常：", e);
            throw new BizException("SYS11004");
        }
        return R.ok();
    }
}
