package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Server;
import com.scrawl.iot.web.dao.entity.ServiceMode;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.ServerService;
import com.scrawl.iot.web.service.ServiceModeService;
import com.scrawl.iot.web.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@RequestMapping("/iot/serviceMode")
@Controller
@Slf4j
public class ServiceModeController {
    private static final String prefix = "/iot/serviceMode";

    @Autowired
    private ServiceModeService serviceModeService;

    @GetMapping
    public String server() {
        return prefix + "/serviceMode";
    }

    @PostMapping("/list")
    @ResponseBody
    public List<ServiceMode> list() {
        return serviceModeService.list();
    }

    @PostMapping("syncServiceModes")
    @ResponseBody
    public R syncServiceModes() {
        try {
            serviceModeService.syncServiceModes();
        } catch (BizException e) {
            log.error("同步IoT业务模式异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("同步IoT业务模式异常：", e);
            throw new BizException("SYS70001");
        }
        return R.ok();
    }
}
