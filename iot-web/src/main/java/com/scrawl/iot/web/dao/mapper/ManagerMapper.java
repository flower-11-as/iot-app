package com.scrawl.iot.web.dao.mapper;


import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;

import java.util.List;

public interface ManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Manager record);

    int insertSelective(Manager record);

    Manager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKey(Manager record);

    Manager selectByUsername(String userName);

    List<Manager> selectPageList(ManagerListReqVO reqVO);

    int selectPageCount(ManagerListReqVO reqVO);

    List<Manager> selectBySelective(Manager record);
}