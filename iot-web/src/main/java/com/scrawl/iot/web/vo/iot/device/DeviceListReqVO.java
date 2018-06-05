package com.scrawl.iot.web.vo.iot.device;

import com.scrawl.iot.web.vo.PageReqVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/23.
 */
@Setter
@Getter
public class DeviceListReqVO extends PageReqVO {
    private String serverId;

    private String devType;

    private String devSerial;

    private String name;

    private List<String> serverIds;

    private List<String> endUserNames;

    private Byte alarmStatus;
}
