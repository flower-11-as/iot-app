package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;
import com.scrawl.iot.web.vo.sys.menu.RoleReqVO;
import com.scrawl.iot.web.vo.sys.role.RoleListReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/14.
 */
public interface RoleService {
    List<ManagerRoleRespVO> getManagerRoleList(Integer managerId);

    List<Role> getRoleList(Role role);

    List<Role> list(RoleListReqVO reqVO);

    Integer count(RoleListReqVO reqVO);

    Role get(Integer id);

    void update(RoleReqVO reqVO);

    void save(RoleReqVO reqVO);

    void remove(Integer id);

    void batchRemove(Integer[] roleIds);
}
