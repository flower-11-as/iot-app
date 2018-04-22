package com.scrawl.iot.web.vo.iot.devType;

import com.scrawl.iot.web.vo.PageReqVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Setter
@Getter
public class DevTypeListReqVO extends PageReqVO {
    private String serverId;

    private String devType;

    private Byte delFlag;

    private List<String> serverIds;
}
