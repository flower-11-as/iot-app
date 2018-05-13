package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.ManagerAccount;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
public interface ManagerAccountService {

    List<ManagerAccount> list();

    List<ManagerAccount> listByManagerId(Integer managerId);

    List<ManagerAccount> listByAccountId(Integer accountId);
}
