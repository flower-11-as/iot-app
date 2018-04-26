package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Device;
import com.scrawl.iot.web.dao.entity.DeviceCommand;
import com.scrawl.iot.web.dao.mapper.DeviceCommandMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.service.IotCallbackService;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.iot.callback.IotCmdCallbackReqVO;
import com.scrawl.iot.web.vo.iot.callback.IotDataReportReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
@Service
@Slf4j
public class IotCallbackServiceImpl implements IotCallbackService {

    @Autowired
    private DeviceCommandMapper deviceCommandMapper;

    @Autowired
    private DeviceService deviceService;

    @Override
    @Transactional
    public void cmdCallback(IotCmdCallbackReqVO reqVO) {
        // TODO: 暂时没分析出需要哪些处理
        if (null == reqVO) {
            throw new BizException("IOT10002", "参数不完整");
        }

        // 分析处理结果
        Map<String, Object> resultParams = reqVO.getResultParams();
        if (null == resultParams) {
            throw new BizException("IOT10002", "参数不完整");
        }
//
//        // 1、失败的命令响应
//        if (null != resultParams.get("resultCode")) {
//            throw new BizException("IOT10002", resultParams.get("resultCode").toString());
//        }
//
//        // 2、终端执行失败
//        if (null == resultParams.get("result") || !"0".equals(resultParams.get("result"))) {
//            throw new BizException("IOT10002", resultParams.get("result").toString());
//        }

        // 成功处理
        DeviceCommand deviceCommand = deviceCommandMapper.selectByDevSerialAndReqCommandId(reqVO.getDevSerial(), reqVO.getCommandId());
        if (null == deviceCommand) {
            return;
        }

        // 0、处理中 1、处理成功 2、处理失败
        if (null != resultParams.get("result") && "0".equals(resultParams.get("result"))) {
            deviceCommand.setStatus((byte) 1);
        } else {
            deviceCommand.setStatus((byte) 0);
        }
        deviceCommandMapper.updateByPrimaryKeySelective(deviceCommand);

    }

    @Override
    public void dataReport(IotDataReportReqVO reqVO) {
        if (null == reqVO) {
            return;
        }

        if (StringUtils.isEmpty(reqVO.getDevSerial())) {
            return;
        }

        deviceService.deviceDataReport(reqVO);
    }
}
