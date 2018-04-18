package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<ManagerRoleRespVO> selectManagerRoleList(Integer managerId);

    List<Role> selectBySelective(Role record);
}