package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public String device() {
        return prefix + "/device";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<Device> list(@RequestBody DeviceListReqVO reqVO) {
        reqVO.setServerIds(getManagerServerIds());

        PageRespVO<Device> respVO = new PageRespVO<>();
        respVO.setRows(deviceService.list(reqVO));
        respVO.setTotal(deviceService.count(reqVO));
        return respVO;
    }

    @GetMapping("/add")
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
        try {
            List<String> serverIds = getManagerServerIds();
            if (null == serverIds || serverIds.size() == 0) {
                throw new BizException("SYS10003");
            }
            deviceService.syncDevices(serverIds, getManagerId());
        } catch (BizException e) {
            log.error("同步IoT设备异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("同步IoT设备异常：", e);
            throw new BizException("SYS90001");
        }
        return R.ok();
    }

    @PostMapping("/syncDevice")
    @ResponseBody
    public R syncDevice(Integer id) {
        try {
            List<String> serverIds = getManagerServerIds();
            if (null == serverIds || serverIds.size() == 0) {
                throw new BizException("SYS10003");
            }
            deviceService.syncDevice(id, getManagerId());
        } catch (BizException e) {
            log.error("同步IoT设备异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("同步IoT设备异常：", e);
            throw new BizException("SYS90001");
        }
        return R.ok();
    }

    @PostMapping("/sendCommand")
    public R sendCommand(@RequestBody Map<String, Object> command) {
        return R.ok();
    }
}
