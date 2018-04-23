package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@RequestMapping("/iot/device")
@Controller
@Slf4j
public class DeviceController extends BaseController {
    private static final String prefix = "/iot/device";

    @GetMapping
    public String device() {
        return prefix + "/device";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<Device> list() {
        PageRespVO<Device> respVO = new PageRespVO<>();
        return respVO;
    }

    @GetMapping
    public String add() {
        return prefix + "/add";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(Device device) {
        return R.ok();
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer id) {
        return prefix + "/view";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id) {
        return prefix + "/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public R update(Device device) {
        return R.ok();
    }

    @PostMapping("/remove/{id}")
    @ResponseBody
    public R remove(@PathVariable("id") Integer id) {
        return R.ok();
    }

    @PostMapping("/syncDevices")
    @ResponseBody
    public R syncDevices() {
        return R.ok();
    }

    @PostMapping("/syncDevices/{id}")
    @ResponseBody
    public R syncDevice(@PathVariable("id") Integer id) {
        return R.ok();
    }

    @PostMapping("/sendCommand")
    public R sendCommand(@RequestBody Map<String, Object> command) {
        return R.ok();
    }
}
