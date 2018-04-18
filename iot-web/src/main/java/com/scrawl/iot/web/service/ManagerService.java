package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/3/11.
 */
public interface ManagerService {
    List<Manager> list(ManagerListReqVO reqVO);

    Integer count(ManagerListReqVO reqVO);

    Manager get(Integer id);

    boolean update(Manager manager);

    void update(ManagerReqVO reqVO);

    Manager get(String username);

    void save(ManagerReqVO reqVO);

    void remove(Integer managerId);

    void batchRemove(Integer[] managerIds);
}
