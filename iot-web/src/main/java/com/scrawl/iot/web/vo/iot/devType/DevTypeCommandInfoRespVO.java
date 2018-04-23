package com.scrawl.iot.web.vo.iot.devType;

import com.scrawl.iot.web.dao.entity.DevTypeCommand;
import com.scrawl.iot.web.dao.entity.DevTypeCommandParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Setter
@Getter
public class DevTypeCommandInfoRespVO {
    // 设备指令
    private DevTypeCommand command;
    private List<DevTypeCommandParam> requestList;
    private List<DevTypeCommandParam> responseList;
}
