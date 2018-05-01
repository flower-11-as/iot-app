package com.scrawl.iot.web.listener;

import com.scrawl.iot.web.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by as on 2018/4/29.
 */
@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    private JobService jobService;

    @Override
    public void run(String... args) {
        try {
            jobService.initSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
