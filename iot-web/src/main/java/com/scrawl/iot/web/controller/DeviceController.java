package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.DevType;
import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.dao.mapper.ServiceModeMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.DevTypeService;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.service.ServerService;
import com.scrawl.iot.web.service.ServiceModeService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.iot.device.DeviceListReqVO;
import com.scrawl.iot.web.vo.iot.device.DeviceListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private DevTypeService devTypeService;

    @Autowired
    private ServerService serverService;

    @Autowired
    private ServiceModeService serviceModeService;

    @GetMapping
    public String device() {
        return prefix + "/device";
    }

    @GetMapping("/alarm")
    public String alarmDevice() {
        return prefix + "/alarmDevice";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<DeviceListRespVO> list(@RequestBody DeviceListReqVO reqVO) {
        reqVO.setServerIds(getManagerServerIds());

        PageRespVO<DeviceListRespVO> respVO = new PageRespVO<>();
        respVO.setRows(deviceService.list(reqVO));
        respVO.setTotal(deviceService.count(reqVO));
        return respVO;
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<String> serverIds = getManagerServerIds();
        List<DevType> devTypes = devTypeService.getByServerIds(serverIds);
        model.addAttribute("devTypes", devTypes);
        model.addAttribute("servers", serverService.list());
        return prefix + "/add";
    }

    @PostMapping("/serverChange")
    @ResponseBody
    public R serverChange(String serverId) {
        return R.ok(serviceModeService.listByServerId(serverId));
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(Device device) {
        device.setCreateManager(getManagerId());
        device.setCreateTime(new Date());
        device.setUpdateTime(new Date());
        try {
            deviceService.save(device);
        }catch (BizException e) {
            log.error("添加IoT设备异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("添加IoT设备异常：", e);
            throw new BizException("SYS90002");
        }
        return R.ok();
    }

    @GetMapping("/info")
    public String view(@RequestParam("id") Integer id, Model model) {
        Device device = deviceService.get(id);
        model.addAttribute("device", device);
        model.addAttribute("baseSensorInfo", deviceService.getBaseSensorInfo(device.getId()));
        model.addAttribute("messageInfo", deviceService.getMessageInfo(device.getId()));

        return prefix + "/info";
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

    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        try {
            deviceService.remove(id, getManagerId());
        }catch (BizException e) {
            log.error("删除IoT设备异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("删除IoT设备异常：", e);
            throw new BizException("SYS90003");
        }
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

    @GetMapping("/command")
    public String command(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("deviceId", id);
        return prefix + "/command";
    }

    @PostMapping("/sendCommand")
    @ResponseBody
    public R sendCommand(@RequestParam Map<String, Object> command, @RequestParam Integer deviceId, @RequestParam String commandId) {
        command.remove("deviceId");
        command.remove("commandId");

        deviceService.sendCommand(command, deviceId, commandId);
        return R.ok();
    }

    @GetMapping("/alarmConfig/{id}")
    public String alarmConfig(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("device", deviceService.get(id));
        model.addAttribute("alarmConfigs", deviceService.getAlarmConfig(id));
        return prefix + "/alarmConfig";
    }

    @PostMapping("/saveAlarmConfig")
    @ResponseBody
    public R saveAlarmConfig(@RequestParam Map<String, String> params) {
        try {
            Integer deviceId = Integer.valueOf(params.get("deviceId"));
            params.remove("deviceId");

            deviceService.saveAlarmConfig(deviceId, params, getManagerId());
        } catch (Exception e) {
            log.error("保存设备预警配置异常：", e);
            throw new BizException("SYS90004");
        }
        return R.ok();
    }
}
