package com.scrawl.iot.web.service;


import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.vo.sys.account.AccountListReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/20.
 */
public interface AccountService {
    List<Account> list(AccountListReqVO reqVO);

    boolean save(Account account);

    boolean remove(Integer id);

    Account get(Integer id);
}
