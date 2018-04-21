package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Server;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.ServerService;
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
 * Desc:
 * Create by scrawl on 2018/4/21
 */
@RequestMapping("/iot/server")
@Controller
@Slf4j
public class ServerController extends BaseController {
    private static final String prefix = "/iot/server";

    @Autowired
    private ServerService serverService;

    @GetMapping
    public String server() {
        return prefix + "/server";
    }

    @PostMapping("/list")
    @ResponseBody
    public List<Server> list() {
        return serverService.list();
    }

    @PostMapping("syncServers")
    @ResponseBody
    public R syncServers() {
        try {
            serverService.syncServers();
        } catch (BizException e) {
            log.error("同步IoT连接平台异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("同步IoT连接平台异常：", e);
            throw new BizException("SYS60001");
        }
        return R.ok();
    }
}
