package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/14.
 */
public interface RoleService {
    List<ManagerRoleRespVO> getManagerRoleList(Integer managerId);

    List<Role> getRoleList(Role role);
}
