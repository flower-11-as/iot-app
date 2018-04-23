package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description: TODO
 * Created by as on 2018/4/23.
 */
@RequestMapping("/iot/callback")
@Controller
public class IotCallBackApi {
//    批量注册进度变动    http://mydomain.com/cbapi/dev-batch-reg-result
//
//    批量注册完成      http://mydomain.com/cbapi/dev-batch-reg-result
//
//    设备数据上报      http://mydomain.com/cbapi/report-dev-callback
//
//    收到指令响应      http://mydomain.com/cbapi/cmd-response-callback

    @PostMapping("/dev-batch-reg-result")
    @ResponseBody
    public R batchRegResult(){
        return R.ok();
    }

    @PostMapping("/report-dev-callback")
    @ResponseBody
    public R reportDevCallback() {
        return R.ok();
    }

    @PostMapping("/cmd-response-callback")
    public R cmdResponseCallback() {
        return R.ok();
    }
}
