package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.dao.mapper.RoleMapper;
import com.scrawl.iot.web.service.RoleService;
import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/14.
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<ManagerRoleRespVO> getManagerRoleList(Integer managerId) {
        return roleMapper.selectManagerRoleList(managerId);
    }

    @Override
    public List<Role> getRoleList(Role role) {
        return roleMapper.selectBySelective(role);
    }
}
