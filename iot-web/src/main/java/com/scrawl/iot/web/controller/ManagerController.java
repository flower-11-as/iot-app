package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.ManagerService;
import com.scrawl.iot.web.service.RoleService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.sys.manager.ManagerListReqVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerRoleRespVO;
import com.scrawl.iot.web.vo.sys.manager.ManagerUpdateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return prefix+"/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public R update(ManagerUpdateReqVO reqVO) {
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
        List<Role> roles = roleService.getRoleList(null);
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @PostMapping("/validUsername")
    @ResponseBody
    public boolean validUsername(String username) {
        return !Optional.ofNullable(managerService.get(username)).isPresent();
    }
}
