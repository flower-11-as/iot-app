package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.dao.entity.RoleMenu;
import com.scrawl.iot.web.dao.mapper.RoleMapper;
import com.scrawl.iot.web.dao.mapper.RoleMenuMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.RoleService;
import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;
import com.scrawl.iot.web.vo.sys.menu.RoleReqVO;
import com.scrawl.iot.web.vo.sys.role.RoleListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/4/14.
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<ManagerRoleRespVO> getManagerRoleList(Integer managerId) {
        return roleMapper.selectManagerRoleList(managerId);
    }

    @Override
    public List<Role> getRoleList(Role role) {
        return roleMapper.selectBySelective(role);
    }

    @Override
    public List<Role> list(RoleListReqVO reqVO) {
        return roleMapper.selectPageList(reqVO);
    }

    @Override
    public Integer count(RoleListReqVO reqVO) {
        return roleMapper.selectPageCount(reqVO);
    }

    @Override
    public Role get(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void update(RoleReqVO reqVO) {
        // 修改manager
        roleMapper.updateByPrimaryKeySelective(reqVO);

        // 修改manager-role
        List<Integer> menuIds = null == reqVO.getMenuIds() ? new ArrayList<>() :
                reqVO.getMenuIds().stream().filter(menuId -> menuId > 0).collect(Collectors.toList());
        List<Integer> oldRoles = roleMenuMapper.selectByRoleId(reqVO.getId()).stream().
                map(RoleMenu::getMenuId).collect(Collectors.toList());

        // 删除rm
        oldRoles.stream().filter(role -> !menuIds.contains(role)).collect(Collectors.toList()).
                forEach(id -> roleMenuMapper.deleteByRoleIdAndMenuId(reqVO.getId(), id));


        // 添加rm
        menuIds.stream().filter(role -> !oldRoles.contains(role)).collect(Collectors.toList()).forEach(id -> {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(reqVO.getId());
            rm.setMenuId(id);
            rm.setCreateManager(reqVO.getUpdateManager());
            rm.setCreateTime(reqVO.getUpdateTime());

            roleMenuMapper.insertSelective(rm);
        });
    }

    @Override
    public void save(RoleReqVO reqVO) {
        // 插入管理员
        if (roleMapper.insertSelective(reqVO) == 0) {
            throw new BizException("SYS40002");
        }

        // 插入角色
        List<Integer> menuIds = reqVO.getMenuIds();
        if (CollectionUtils.isEmpty(menuIds)) {
            return;
        }

        menuIds.forEach(menuId -> {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(reqVO.getId());
            rm.setMenuId(menuId);
            rm.setCreateManager(reqVO.getCreateManager());
            rm.setCreateTime(reqVO.getCreateTime());

            roleMenuMapper.insertSelective(rm);
        });
    }

    @Override
    @Transactional
    public void remove(Integer managerId) {
        // 删除角色
        roleMapper.deleteByPrimaryKey(managerId);

        // 删除角色菜单
        roleMenuMapper.deleteByRoleId(managerId);
    }

    @Override
    @Transactional
    public void batchRemove(Integer[] roleIds) {
        if (ArrayUtils.isNotEmpty(roleIds)) {
            Arrays.stream(roleIds).forEach(this::remove);
        }
    }
}
