package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.ManagerAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManagerAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerAccount record);

    int insertSelective(ManagerAccount record);

    ManagerAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerAccount record);

    int updateByPrimaryKey(ManagerAccount record);

    List<ManagerAccount> selectBySelective(ManagerAccount record);

    int deleteByManagerIdAndAccountId(@Param("managerId") Integer managerId, @Param("accountId") Integer accountId);

    int deleteByManagerId(@Param("managerId") Integer managerId);
}