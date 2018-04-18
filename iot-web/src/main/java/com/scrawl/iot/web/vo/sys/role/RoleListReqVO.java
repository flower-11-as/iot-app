package com.scrawl.iot.web.vo.sys.role;

import com.scrawl.iot.web.vo.PageReqVO;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
@Getter
@Setter
public class RoleListReqVO extends PageReqVO {
    private String name;
}
