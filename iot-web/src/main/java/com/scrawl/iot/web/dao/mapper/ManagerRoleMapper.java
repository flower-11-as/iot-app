package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.ManagerRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManagerRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerRole record);

    int insertSelective(ManagerRole record);

    ManagerRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerRole record);

    int updateByPrimaryKey(ManagerRole record);

    List<ManagerRole> selectByManagerId(Integer managerId);

    List<Integer> selectRoleIdsByManagerId(Integer managerId);

    int deleteByManagerIdAndRoleId(@Param("managerId") Integer managerId, @Param("roleId") Integer roleId);

    int deleteByManagerId(@Param("managerId") Integer managerId);
}