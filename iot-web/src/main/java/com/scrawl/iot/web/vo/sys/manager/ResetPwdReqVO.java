package com.scrawl.iot.web.vo.sys.manager;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/5/2.
 */
@Setter
@Getter
public class ResetPwdReqVO {
    private Integer id;
    private String pwdOld;
    private String pwdNew;
}
