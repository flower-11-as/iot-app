package com.scrawl.iot.web.vo.sys.param;

import com.scrawl.iot.web.vo.PageReqVO;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Setter
@Getter
public class ParamListReqVO extends PageReqVO {
    private String paramKey;
    private String group;
}
