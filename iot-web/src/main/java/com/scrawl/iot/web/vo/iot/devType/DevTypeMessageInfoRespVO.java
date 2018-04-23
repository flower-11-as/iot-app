package com.scrawl.iot.web.vo.iot.devType;

import com.scrawl.iot.web.dao.entity.DevTypeMessage;
import com.scrawl.iot.web.dao.entity.DevTypeMessageParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Setter
@Getter
public class DevTypeMessageInfoRespVO {
    // 设备消息
    private DevTypeMessage message;
    private List<DevTypeMessageParam> messageList;
}
