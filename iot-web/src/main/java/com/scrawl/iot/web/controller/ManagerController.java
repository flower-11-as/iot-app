package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Account;
import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.AccountService;
import com.scrawl.iot.web.service.ManagerAccountService;
import com.scrawl.iot.web.service.ManagerService;
import com.scrawl.iot.web.service.RoleService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.sys.manager.ManagerAccountRespVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Description:
 * Created by as on 2018/3/11.
 */
@RequestMapping("/sys/manager")
@Slf4j
@Controller
public class ManagerController extends BaseController {

    private static final String prefix = "/system/manager";

    @Autowired
    private ManagerService managerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String manager() {
        return prefix + "/manager";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<Manager> list(@RequestBody ManagerListReqVO reqVO) {
        PageRespVO<Manager> pageRespVO = new PageRespVO<>();
        pageRespVO.setRows(managerService.list(reqVO));
        pageRespVO.setTotal(managerService.count(reqVO));
        return pageRespVO;
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Manager manager = managerService.get(id);
        model.addAttribute("manager", manager);
        List<ManagerRoleRespVO> roles = roleService.getManagerRoleList(id);
        model.addAttribute("roles", roles);
        List<ManagerAccountRespVO> accounts = accountService.getManagerAccountList(id);
        model.addAttribute("accounts", accounts);
        return prefix + "/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public R update(ManagerReqVO reqVO) {
        try {
            reqVO.setUpdateManager(getManagerId());
            reqVO.setUpdateTime(new Date());
            managerService.update(reqVO);
        } catch (Exception e) {
            log.error("修改管理员异常：", e);
            throw new BizException("SYS30001");
        }
        return R.ok();
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Role> roles = roleService.getRoleList(new Role());
        model.addAttribute("roles", roles);
        List<Account> accounts = accountService.list(new Account());
        model.addAttribute("accounts", accounts);
        return prefix + "/add";
    }

    @PostMapping("/validUsername")
    @ResponseBody
    public boolean validUsername(String username) {
        return !Optional.ofNullable(managerService.get(username)).isPresent();
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(ManagerReqVO reqVO) {
        try {
            reqVO.setPassword(new SimpleHash("md5", reqVO.getPassword(),
                    ByteSource.Util.bytes(""), 2).toHex());
            reqVO.setCreateManager(getManagerId());
            Date now = new Date();
            reqVO.setCreateTime(now);
            reqVO.setUpdateTime(now);
            managerService.save(reqVO);
        } catch (Exception e) {
            log.error("添加管理员异常：", e);
            throw new BizException("SYS30002");
        }
        return R.ok();
    }

    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        try {
            managerService.remove(id);
        } catch (Exception e) {
            log.error("删除管理员异常：", e);
            throw new BizException("SYS30003");
        }
        return R.ok();
    }

    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") Integer[] ids) {
        try {
            managerService.batchRemove(ids);
        } catch (Exception e) {
            log.error("删除管理员异常：", e);
            throw new BizException("SYS30003");
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
    public R resetPwd(Manager manager) {
        try {
            manager.setPassword(new SimpleHash("md5", manager.getPassword(),
                    ByteSource.Util.bytes(""), 2).toHex());
            manager.setUpdateManager(getManagerId());
            manager.setUpdateTime(new Date());

            if (!managerService.update(manager)) {
                throw new BizException("SYS30004");
            }
        } catch (Exception e) {
            log.error("重置管理员密码异常：", e);
            throw new BizException("SYS30004");
        }
        return R.ok();
    }
}
