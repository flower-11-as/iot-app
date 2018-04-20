package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.vo.sys.account.AccountListReqVO;

import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    List<Account> selectPageList(AccountListReqVO reqVO);

    int selectPageCount(AccountListReqVO reqVO);
}