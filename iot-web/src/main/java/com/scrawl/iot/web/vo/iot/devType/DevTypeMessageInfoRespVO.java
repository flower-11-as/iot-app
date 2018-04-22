package com.scrawl.iot.web.vo.iot.devType;

import com.scrawl.iot.web.dao.entity.DevTypeInfo;
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
    // 消息、指令名称
    private String name;
    private List<DevTypeInfo> messageList;
}
