package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.DeviceBatch;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.DeviceBatchService;
import com.scrawl.iot.web.vo.PageReqVO;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@RequestMapping("/iot/deviceBatch")
@Controller
@Slf4j
public class DeviceBatchController extends BaseController {
    private static final String prefix = "/iot/deviceBatch";

    @Autowired
    private DeviceBatchService deviceBatchService;

    @GetMapping
    public String deviceBatch() {
        return prefix + "/deviceBatch";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<DeviceBatch> list(@RequestBody PageReqVO pageReqVO) {
        PageRespVO<DeviceBatch> respVO = new PageRespVO<>();
        respVO.setRows(deviceBatchService.list(pageReqVO));
        respVO.setTotal(deviceBatchService.count(pageReqVO));
        return respVO;
    }

    @PostMapping("/batchAdd")
    @ResponseBody
    public R batchAdd(@RequestParam("file") MultipartFile file) {
        try {
            if (null == file || null == file.getInputStream()) {
                throw new BizException("SYS14002");
            }

            deviceBatchService.batchAdd(file.getInputStream(), getManagerId());
        }catch (BizException e) {
            log.error("批量添加IoT设备异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("批量添加IoT设备异常：", e);
            throw new BizException("SYS14001");
        }
        return R.ok();
    }
}
