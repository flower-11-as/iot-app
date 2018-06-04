package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.domain.Tree;
import com.scrawl.iot.paper.exception.IotHttpException;
import com.scrawl.iot.paper.exception.PaperHttpException;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.request.IotLoginRequest;
import com.scrawl.iot.paper.http.request.IotSubscribeRequest;
import com.scrawl.iot.paper.http.response.IotLoginResponse;
import com.scrawl.iot.paper.http.response.IotResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.DevType;
import com.scrawl.iot.web.dao.entity.ManagerAccount;
import com.scrawl.iot.web.dao.mapper.AccountMapper;
import com.scrawl.iot.web.dao.mapper.DevTypeMapper;
import com.scrawl.iot.web.enums.AccountStatusEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.DeviceService;
import com.scrawl.iot.web.service.ManagerAccountService;
import com.scrawl.iot.web.vo.iot.account.AccountListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerAccountRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private DevTypeMapper devTypeMapper;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ManagerAccountService managerAccountService;

    private final Integer AUTH_CONTINUE_HOUR = 8;

    @Override
    public List<Account> list(AccountListReqVO reqVO) {
        return accountMapper.selectPageList(reqVO);
    }

    @Override
    public List<Account> list(Account account) {
        return accountMapper.selectListBySelective(account);
    }

    @Override
    public int count(AccountListReqVO reqVO) {
        return accountMapper.selectPageCount(reqVO);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean iotLogin(Account account) {
        IotLoginRequest request = new IotLoginRequest();
        request.setServerId(account.getServerId());
        request.setPassword(account.getPassword());
        IotLoginResponse response;
        try {
            response = iotHttpService.loginAuth(request);
        } catch (IotHttpException | PaperHttpException e) {
            // 置位失败
            account.setStatus(AccountStatusEnum.FAIL.getCode());
            accountMapper.updateByPrimaryKeySelective(account);

            throw new BizException("SYS50001", e.getMessage());
        }

        account.setToken(response.getAccessToken());
        account.setLastLoginTime(new Date());
        account.setStatus(AccountStatusEnum.SUCCESS.getCode());
        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    @Override
    public boolean save(Account account) {
        return iotLogin(account);
    }

    @Override
    public boolean remove(Integer id) {
        Account account = get(id);

        DevType devType = new DevType();
        devType.setServerId(account.getServerId());
        List<DevType> devTypes = devTypeMapper.selectBySelective(devType);
        if (null != devTypes && devTypes.size() > 0) {
            throw new BizException("SYS50002", "已绑定业务数据，不允许删除");
        }

        return accountMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Account get(Integer id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public Account get(String serverId) {
        return accountMapper.selectByServerId(serverId);
    }

    @Override
    public boolean resetPwd(Account account) {
        Account oldAccount = get(account.getId());
        account.setServerId(oldAccount.getServerId());

        return iotLogin(account);
    }

    @Override
    public boolean resetAuth(Integer id, Integer managerId) {
        Account account = get(id);
        account.setUpdateManager(managerId);
        account.setUpdateTime(new Date());

        return iotLogin(account);
    }

    @Override
    public Account getAvailableAccount() {
        Account params = new Account();
        params.setStatus(AccountStatusEnum.SUCCESS.getCode());
        List<Account> accountList = accountMapper.selectListBySelective(params);
        Account rs = null;
        for (Account account : accountList) {
            // 尝试登陆一次
            try {
                iotLogin(account);
                rs = account;
                break;
            } catch (Exception e) {
                log.error("获取有效账户异常：", e);
                // ignore
            }
        }

        return rs;
    }

    @Override
    public List<ManagerAccountRespVO> getAccountListByManager(Integer managerId) {
        return accountMapper.selectAccountListByManager(managerId);
    }

    @Override
    public Account getAndAuthAccount(String serverId) {
        Account account = accountMapper.selectByServerId(serverId);

        if (account.getStatus().equals(AccountStatusEnum.SUCCESS.getCode())) {
            Date authEndTime = DateUtils.addHours(account.getLastLoginTime(), AUTH_CONTINUE_HOUR);
            Date now = new Date();
            if (!now.after(authEndTime)) {
                return account;
            }
        }

        return iotLogin(account) ? account : null;
    }

    @Override
    public List<Account> getManagerAccountList(Integer managerId) {
        return accountMapper.selectManagerAccountList(managerId);
    }

    @Override
    public boolean subscribe(Account account) {
        Account authAccount = get(account.getId());
        authAccount = getAndAuthAccount(authAccount.getServerId());

        IotHeader header = new IotHeader();
        header.setServerId(authAccount.getServerId());
        header.setAccessToken(authAccount.getToken());

        IotSubscribeRequest request = new IotSubscribeRequest();
        String callbackUrl = account.getSubscribeUrl() + "/account" + account.getId();
        request.setCallbackUrl(callbackUrl);
        IotResponse response = iotHttpService.subscribe(header, request);

        account.setSubscribeUrl(callbackUrl);
        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    @Override
    public List<Tree> getEndUserNameTree() {
        // 获取所有账户
        List<Account> accounts = list(new Account());
        List<Tree> trees = new ArrayList<>();
        accounts.forEach(account -> {
            Tree tree = new Tree();
            tree.setId(account.getId() + "");
            tree.setText(account.getServerId());
            tree.setParentId("-1");
            // 获取账户下所有终端账户名
            List<String> endUserNames = deviceService.getEndUserNameByServerId(account.getServerId());
            if (endUserNames.size() > 0) {
                tree.setHasChildren(true);

                List<Tree> childTrees = new ArrayList<>();
                endUserNames.forEach(endUserName -> {
                    Tree childTree = new Tree();
                    childTree.setId(endUserName);
                    childTree.setText(endUserName);
                    childTrees.add(childTree);
                });
                tree.setChildren(childTrees);
            }

            trees.add(tree);
        });

        return trees;
    }

    @Override
    public List<Tree> getEndUserNameTree(Integer accountId) {
        List<ManagerAccount> managerAccounts = managerAccountService.listByAccountId(accountId);
        Map<Integer, String> managerAccountMap = managerAccounts.stream().collect(Collectors.toMap(ManagerAccount::getAccountId, ManagerAccount::getEndUserName));

        // 获取所有账户
        List<Account> accounts = list(new Account());
        List<Tree> trees = new ArrayList<>();
        accounts.forEach(account -> {
            Tree tree = new Tree();
            tree.setId(account.getId() + "");
            tree.setText(account.getServerId());
            tree.setParentId("-1");
            if (managerAccountMap.containsKey(account.getId())) {
                Map<String, Object> state = new HashMap<>();
                state.put("selected", true);
                tree.setState(state);
            }

            List<String> selectEndUserNames = Optional.ofNullable(managerAccountMap.get(account.getId())).
                    map(endUserName -> Arrays.asList(endUserName.split(","))).orElse(new ArrayList<>());

            // 获取账户下所有终端账户名
            List<String> endUserNames = deviceService.getEndUserNameByServerId(account.getServerId());
            if (endUserNames.size() > 0) {
                tree.setHasChildren(true);

                List<Tree> childTrees = new ArrayList<>();
                endUserNames.forEach(endUserName -> {
                    Tree childTree = new Tree();
                    childTree.setId(endUserName);
                    childTree.setText(endUserName);
                    childTree.setHasParent(true);
                    if (selectEndUserNames.contains(endUserName)) {
                        Map<String, Object> state = new HashMap<>();
                        state.put("selected", true);
                        childTree.setState(state);
                        tree.getState().remove("selected");
                    }
                    childTrees.add(childTree);
                });
                tree.setChildren(childTrees);
            }

            trees.add(tree);
        });

        return trees;
    }
}
