package com.scrawl.iot.web.service;

import com.scrawl.iot.web.vo.iot.devType.DevTypeCommandInfoRespVO;
import com.scrawl.iot.web.vo.iot.devType.DevTypeMessageInfoRespVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
public interface DevTypeInfoService {
    List<DevTypeMessageInfoRespVO> getDevTypeMessages(Integer devTypeId);

    List<DevTypeCommandInfoRespVO> getDevTypeCommands(Integer devTypeId);
}
