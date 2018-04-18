package com.scrawl.iot.web.service;

import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/14.
 */
public interface RoleService {
    List<ManagerRoleRespVO> managerRoleList(Integer managerId);
}
