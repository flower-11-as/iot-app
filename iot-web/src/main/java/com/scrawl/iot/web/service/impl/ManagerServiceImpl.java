package com.scrawl.iot.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.ManagerAccount;
import com.scrawl.iot.web.dao.entity.ManagerRole;
import com.scrawl.iot.web.dao.mapper.ManagerAccountMapper;
import com.scrawl.iot.web.dao.mapper.ManagerMapper;
import com.scrawl.iot.web.dao.mapper.ManagerRoleMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.ManagerService;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/3/11.
 */
@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private ManagerRoleMapper managerRoleMapper;

    @Autowired
    private ManagerAccountMapper managerAccountMapper;

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public List<Manager> list(ManagerListReqVO reqVO) {
        return managerMapper.selectPageList(reqVO);
    }

    @Override
    public List<Manager> list(Manager manager) {
        return managerMapper.selectBySelective(manager);
    }

    @Override
    public Integer count(ManagerListReqVO reqVO) {
        return managerMapper.selectPageCount(reqVO);
    }

    @Override
    public Manager get(Integer id) {
        return managerMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(Manager manager) {
        return managerMapper.updateByPrimaryKeySelective(manager) > 0;
    }

    @Override
    @Transactional
    public void update(ManagerReqVO reqVO) {
        // 修改manager
        managerMapper.updateByPrimaryKeySelective(reqVO);

        // 修改manager-role
        List<Integer> roles = null == reqVO.getRoles() ? new ArrayList<>() : reqVO.getRoles();
        List<Integer> oldRoles = managerRoleMapper.selectRoleIdsByManagerId(reqVO.getId());

        // 删除rm
        oldRoles.stream().filter(role -> !roles.contains(role)).collect(Collectors.toList()).
                forEach(id -> managerRoleMapper.deleteByManagerIdAndRoleId(reqVO.getId(), id));

        // 添加rm
        roles.stream().filter(role -> !oldRoles.contains(role)).collect(Collectors.toList()).forEach(id -> {
            ManagerRole mr = new ManagerRole();
            mr.setManagerId(reqVO.getId());
            mr.setRoleId(id);
            mr.setCreateManager(reqVO.getUpdateManager());
            mr.setCreateTime(new Date());
            managerRoleMapper.insertSelective(mr);
        });


        // 修改manager-account
        Map<Integer, List<String>> accountEndUserNames = JSON.parseObject(reqVO.getAccountEndUserNames(), new TypeReference<Map<Integer, List<String>>>(){});
        ManagerAccount managerAccount = new ManagerAccount();
        managerAccount.setManagerId(reqVO.getId());
        List<ManagerAccount> oldAccountList = managerAccountMapper.selectBySelective(managerAccount);
        List<Integer> oldAccounts = oldAccountList.stream().map(ManagerAccount::getAccountId).collect(Collectors.toList());
        Map<Integer, ManagerAccount> oldAccountMap = oldAccountList.stream().collect(Collectors.toMap(ManagerAccount::getAccountId,
                Function.identity(), (oldValue, newValue) -> newValue));

        // 删除ma
        oldAccounts.stream().filter(account -> !accountEndUserNames.containsKey(account)).collect(Collectors.toList()).
                forEach(id -> managerAccountMapper.deleteByManagerIdAndAccountId(reqVO.getId(), id));

        // 编辑、添加ma
        accountEndUserNames.forEach((accountId, endUserNames) -> {
            if (oldAccountMap.containsKey(accountId)) {
                ManagerAccount ma = oldAccountMap.get(accountId);
                ma.setEndUserName(String.join(",", endUserNames));
                ma.setCreateManager(reqVO.getCreateManager());
                ma.setCreateTime(new Date());
                managerAccountMapper.insertSelective(ma);
            } else {
                ManagerAccount ma = new ManagerAccount();
                ma.setManagerId(reqVO.getId());
                ma.setAccountId(accountId);
                ma.setCreateManager(reqVO.getUpdateManager());
                ma.setCreateTime(new Date());
                managerAccountMapper.insertSelective(ma);
            }
        });
    }

    @Override
    public Manager get(String username) {
        return managerMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public void save(ManagerReqVO reqVO) {
        // 插入管理员
        if (managerMapper.insertSelective(reqVO) == 0) {
            throw new BizException("SYS30002");
        }

        // 插入角色
        List<Integer> roleIds = reqVO.getRoles();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                ManagerRole mr = new ManagerRole();
                mr.setManagerId(reqVO.getId());
                mr.setRoleId(roleId);
                mr.setCreateManager(reqVO.getCreateManager());
                mr.setCreateTime(reqVO.getCreateTime());
                managerRoleMapper.insertSelective(mr);
            });
        }

        // 插入账户
        Map<Integer, List<String>> accountEndUserNames = JSON.parseObject(reqVO.getAccountEndUserNames(), new TypeReference<Map<Integer, List<String>>>(){});
        accountEndUserNames.forEach((accountId, endUserNames) -> {
            ManagerAccount ma = new ManagerAccount();
            ma.setManagerId(reqVO.getId());
            ma.setAccountId(accountId);
            ma.setEndUserName(String.join(",", endUserNames));
            ma.setCreateManager(reqVO.getCreateManager());
            ma.setCreateTime(new Date());
            managerAccountMapper.insertSelective(ma);
        });
    }

    @Override
    @Transactional
    public void remove(Integer managerId) {
        // 删除管理员
        managerMapper.deleteByPrimaryKey(managerId);

        // 删除管理员角色
        managerRoleMapper.deleteByManagerId(managerId);

        // 删除管理员IoT账户
        managerAccountMapper.deleteByManagerId(managerId);
    }

    @Override
    @Transactional
    public void batchRemove(Integer[] managerIds) {
        if (ArrayUtils.isNotEmpty(managerIds)) {
            Arrays.stream(managerIds).forEach(this::remove);
        }
    }

    @Override
    public List<Manager> listOnlineManager() {
        List<Manager> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        Optional.ofNullable(sessions).orElse(new ArrayList<>()).forEach(session -> {
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                return;
            }

            SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
                    .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            Manager userDO = (Manager) principalCollection.getPrimaryPrincipal();
            list.add(userDO);
        });
        return list;
    }
}
