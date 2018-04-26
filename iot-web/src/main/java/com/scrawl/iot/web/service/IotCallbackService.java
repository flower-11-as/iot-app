package com.scrawl.iot.web.service;

import com.scrawl.iot.web.vo.iot.callback.IotCmdCallbackReqVO;
import com.scrawl.iot.web.vo.iot.callback.IotDataReportReqVO;

/**
 * Description:
 * Created by as on 2018/4/26.
 */
public interface IotCallbackService {

    void cmdCallback(IotCmdCallbackReqVO reqVO);

    void dataReport(IotDataReportReqVO reqVO);
}
