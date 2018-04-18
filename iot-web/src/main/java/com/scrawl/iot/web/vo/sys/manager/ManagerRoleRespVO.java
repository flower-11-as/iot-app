package com.scrawl.iot.web.vo.sys.manager;

import com.scrawl.iot.web.dao.entity.Role;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/4/14.
 */
@Setter
@Getter
public class ManagerRoleRespVO extends Role {
    private String roleSign;
}
