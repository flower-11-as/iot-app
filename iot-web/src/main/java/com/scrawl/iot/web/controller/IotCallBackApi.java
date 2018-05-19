package com.scrawl.iot.web.controller;

import com.alibaba.fastjson.JSON;
import com.scrawl.iot.web.service.IotCallbackService;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.iot.callback.IotCmdCallbackReqVO;
import com.scrawl.iot.web.vo.iot.callback.IotDataReportReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@RequestMapping("/iot/callback")
@Controller
@Slf4j
public class IotCallBackApi {
//    批量注册进度变动    http://mydomain.com/cbapi/dev-batch-reg-result
//
//    批量注册完成      http://mydomain.com/cbapi/dev-batch-reg-result
//
//    设备数据上报      http://mydomain.com/cbapi/report-dev-callback
//
//    收到指令响应      http://mydomain.com/cbapi/cmd-response-callback

    @Autowired
    private IotCallbackService iotCallbackService;

    @PostMapping("/{accountSign}/dev-batch-reg-result")
    @ResponseBody
    public R batchRegResult(@PathVariable("accountSign") String accountSign) {
        return R.ok();
    }

    @PostMapping("/{accountSign}/report-dev-callback")
    @ResponseBody
    public R reportDevCallback(@PathVariable("accountSign") String accountSign, @RequestBody IotDataReportReqVO reqVO) {
        log.info("收到Iot数据上报[{}][{}]", accountSign, JSON.toJSONString(reqVO));
        iotCallbackService.dataReport(reqVO);
        return R.ok();
    }

    @PostMapping("/{accountSign}/cmd-response-callback")
    @ResponseBody
    public R cmdResponseCallback(@PathVariable("accountSign") String accountSign, @RequestBody IotCmdCallbackReqVO reqVO) {
        log.info("收到Iot指令响应回调[{}][{}]", accountSign, JSON.toJSONString(reqVO));
        iotCallbackService.cmdCallback(reqVO);

        return R.ok();
    }
}
