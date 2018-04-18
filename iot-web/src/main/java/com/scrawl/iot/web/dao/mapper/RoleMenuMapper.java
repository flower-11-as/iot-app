package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

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

    int deleteByRoleIdAndMenuId(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int deleteByRoleId(@Param("roleId") Integer roleId);
}