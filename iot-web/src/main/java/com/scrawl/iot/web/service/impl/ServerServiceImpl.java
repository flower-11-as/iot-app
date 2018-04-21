package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.paper.exception.IotHttpException;
import com.scrawl.iot.paper.exception.PaperHttpException;
import com.scrawl.iot.paper.http.request.IotHeader;
import com.scrawl.iot.paper.http.response.IotServerResponse;
import com.scrawl.iot.paper.http.service.IotHttpService;
import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.Server;
import com.scrawl.iot.web.dao.mapper.ServerMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Desc:
 * Create by scrawl on 2018/4/21
 */
@Service
@Slf4j
public class ServerServiceImpl implements ServerService{

    @Autowired
    private ServerMapper serverMapper;

    @Autowired
    private IotHttpService iotHttpService;

    @Autowired
    private AccountService accountService;

    @Override
    public List<Server> list() {
        return serverMapper.selectList();
    }

    @Override
    @Transactional
    public void syncServers() {
        // 获取一个有效账户
        Account account = accountService.getAvailableAccount();
        if (null == account) {
            throw new BizException("IOT10001");
        }

        IotHeader header = new IotHeader();
        header.setServerId(account.getServerId());
        header.setAccessToken(account.getToken());
        IotServerResponse response;
        try {
            response = iotHttpService.getServers(header, null);
        } catch (IotHttpException | PaperHttpException e) {
            throw new BizException("SYS60001", e.getMessage());
        }

        if (null != response.getIotserverList()) {
            serverMapper.deleteAll();

            Date now = new Date();
            response.getIotserverList().forEach(iotServer -> {
                Server server = new Server();
                server.setServerId(iotServer.getId());
                server.setDescription(iotServer.getDesc());
                server.setCreateTime(now);
                serverMapper.insertSelective(server);
            });
        }
    }
}
