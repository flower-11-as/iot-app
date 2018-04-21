package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.exception.IotHttpException;
import com.scrawl.iot.paper.exception.PaperHttpException;
import com.scrawl.iot.paper.http.request.IotLoginRequest;
import com.scrawl.iot.paper.http.response.IotLoginResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.mapper.AccountMapper;
import com.scrawl.iot.web.enums.AccountStatusEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.vo.sys.account.AccountListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/20.
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private IotHttpService iotHttpService;

    @Override
    public List<Account> list(AccountListReqVO reqVO) {
        return accountMapper.selectPageList(reqVO);
    }

    @Override
    public int count(AccountListReqVO reqVO) {
        return accountMapper.selectPageCount(reqVO);
    }

    @Override
    public boolean save(Account account) {
        IotLoginResponse response = iotLogin(account.getServerId(), account.getPassword());

        account.setToken(response.getAccessToken());
        account.setLastLoginTime(new Date());
        account.setStatus(AccountStatusEnum.SUCCESS.getCode());
        return accountMapper.insertSelective(account) > 0;
    }

    @Override
    public boolean remove(Integer id) {
        // TODO: 有业务数据的不能删除

        return accountMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Account get(Integer id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean resetPwd(Account account) {
        Account oldAccount = get(account.getId());

        IotLoginResponse response = iotLogin(oldAccount.getServerId(), account.getPassword());
        account.setToken(response.getAccessToken());
        account.setLastLoginTime(new Date());
        account.setStatus(AccountStatusEnum.SUCCESS.getCode());
        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    @Override
    public boolean resetAuth(Integer id, Integer managerId) {
        Account account = get(id);

        IotLoginResponse response = iotLogin(account.getServerId(), account.getPassword());
        account.setToken(response.getAccessToken());
        account.setLastLoginTime(new Date());
        account.setStatus(AccountStatusEnum.SUCCESS.getCode());
        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    private IotLoginResponse iotLogin(String serverId, String password) {
        IotLoginRequest request = new IotLoginRequest();
        request.setServerId(serverId);
        request.setPassword(password);
        IotLoginResponse response;
        try {
            response = iotHttpService.loginAuth(request);
        } catch (IotHttpException | PaperHttpException e) {
            throw new BizException("SYS50001", e.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public Account getAvailableAccount() {
        Account params = new Account();
        params.setStatus(AccountStatusEnum.SUCCESS.getCode());
        List<Account> accountList = accountMapper.selectListBySelective(params);
        Account rs = null;
        for (Account account : accountList) {
            // 尝试登陆一次
            try {
                IotLoginResponse response = iotLogin(account.getServerId(), account.getPassword());

                account.setToken(response.getAccessToken());
                account.setLastLoginTime(new Date());
                account.setStatus(AccountStatusEnum.SUCCESS.getCode());
                accountMapper.updateByPrimaryKeySelective(account);

                rs = account;
                break;
            } catch (IotHttpException | PaperHttpException e) {
                // ignore
            }
        }

        return rs;
    }
}
