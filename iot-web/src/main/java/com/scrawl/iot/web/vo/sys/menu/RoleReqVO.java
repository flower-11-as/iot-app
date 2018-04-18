package com.scrawl.iot.web.vo.sys.menu;

import com.scrawl.iot.web.dao.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
@Setter
@Getter
public class RoleReqVO extends Role {
    private List<Integer> menuIds;
}
