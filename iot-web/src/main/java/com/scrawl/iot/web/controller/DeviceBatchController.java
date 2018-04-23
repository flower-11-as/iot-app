package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@RequestMapping("/iot/deviceBatch")
@Controller
@Slf4j
public class DeviceBatchController extends BaseController {
    private static final String prefix = "/iot/deviceBatch";

    @GetMapping
    public String deviceBatch() {
        return prefix + "/deviceBatch";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO list() {
        PageRespVO respVO = new PageRespVO();
        return respVO;
    }

    @PostMapping("/addBatch")
    public R save() {
        return R.ok();
    }

    @GetMapping("/syncProgress")
    public R syncProgress() {
        return R.ok();
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id) {
        return prefix + "/view";
    }
}
