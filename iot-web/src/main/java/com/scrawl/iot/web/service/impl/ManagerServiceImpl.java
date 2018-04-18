package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.ManagerRole;
import com.scrawl.iot.web.dao.mapper.ManagerMapper;
import com.scrawl.iot.web.dao.mapper.ManagerRoleMapper;
import com.scrawl.iot.web.service.ManagerService;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerUpdateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Override
    public List<Manager> list(ManagerListReqVO reqVO) {
        return managerMapper.selectPageList(reqVO);
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
    @Transactional
    public void update(ManagerUpdateReqVO reqVO) {
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
    }
}
