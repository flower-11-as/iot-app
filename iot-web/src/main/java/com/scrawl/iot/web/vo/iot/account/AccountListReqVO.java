package com.scrawl.iot.web.vo.iot.account;

import com.scrawl.iot.web.vo.PageReqVO;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/4/20.
 */
@Setter
@Getter
public class AccountListReqVO extends PageReqVO {
    private String serverId;
}
