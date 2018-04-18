package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

    List<RoleMenu> selectByRoleId(Integer roleId);

    List<RoleMenu> selectByManagerId(Integer managerId);

    List<String> selectPermissionsByManagerId(Integer managerId);
}