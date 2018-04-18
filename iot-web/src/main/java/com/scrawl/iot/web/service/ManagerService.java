package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerUpdateReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/3/11.
 */
public interface ManagerService {
    List<Manager> list(ManagerListReqVO reqVO);

    Integer count(ManagerListReqVO reqVO);

    Manager get(Integer id);

    void update(ManagerUpdateReqVO reqVO);

    Manager get(String username);
}
