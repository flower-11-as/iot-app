package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.iot.account.AccountListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Description:
 * Created by as on 2018/4/20.
 */
@RequestMapping("/iot/account")
@Controller
@Slf4j
public class AccountController extends BaseController {
    private static final String prefix = "/iot/account";

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String account() {
        return prefix + "/account";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<Account> list(@RequestBody AccountListReqVO reqVO) {
        PageRespVO<Account> pageRespVO = new PageRespVO<>();
        pageRespVO.setRows(accountService.list(reqVO));
        pageRespVO.setTotal(accountService.count(reqVO));
        return pageRespVO;
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(Account account) {
        try {
            Date now = new Date();
            account.setCreateManager(getManagerId());
            account.setCreateTime(now);
            account.setUpdateTime(now);
            accountService.save(account);
        } catch (BizException e) {
            log.error("添加IoT账户异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("添加IoT账户异常：", e);
            throw new BizException("SYS50001");
        }
        return R.ok();
    }

    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        try {
            accountService.remove(id);
        } catch (Exception e) {
            log.error("删除IoT账户异常：", e);
            throw new BizException("SYS50002");
        }
        return R.ok();
    }

    @GetMapping("/resetPwd/{id}")
    public String resetPwd(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return prefix + "/reset_pwd";
    }

    @PostMapping("/resetPwd")
    @ResponseBody
    public R resetPwd(Account account) {
        try {
            account.setUpdateManager(getManagerId());
            account.setUpdateTime(new Date());

            if (!accountService.resetPwd(account)) {
                throw new BizException("SYS50003");
            }
        } catch (BizException e) {
            log.error("重置IoT账户密码异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("重置IoT账户密码异常：", e);
            throw new BizException("SYS50003");
        }
        return R.ok();
    }

    @PostMapping("/resetAuth")
    @ResponseBody
    public R resetAuth(Integer id) {
        try {
            if (!accountService.resetAuth(id, getManagerId())) {
                throw new BizException("SYS50002");
            }
        } catch (BizException e) {
            log.error("重置IoT账户授权异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("重置IoT账户授权异常：", e);
            throw new BizException("SYS50002");
        }
        return R.ok();
    }
}
