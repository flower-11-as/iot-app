package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Role;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.RoleService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.sys.menu.RoleReqVO;
import com.scrawl.iot.web.vo.sys.role.RoleListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Description:
 * Created by as on 2018/4/19.
 */
@RequestMapping("/sys/role")
@Controller
@Slf4j
public class RoleController extends BaseController {
    String prefix = "system/role";

    @Autowired
    RoleService roleService;

    @GetMapping()
    public String role() {
        return prefix + "/role";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<Role> list(@RequestBody RoleListReqVO reqVO) {
        PageRespVO<Role> pageRespVO = new PageRespVO<>();
        pageRespVO.setRows(roleService.list(reqVO));
        pageRespVO.setTotal(roleService.count(reqVO));
        return pageRespVO;
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Role role = roleService.get(id);
        model.addAttribute("role", role);
        return prefix + "/edit";
    }

    @PostMapping("/save")
    @ResponseBody()
    public R save(RoleReqVO reqVO) {
        try {
            reqVO.setCreateManager(getManagerId());
            Date now = new Date();
            reqVO.setCreateTime(now);
            reqVO.setUpdateTime(now);
            roleService.save(reqVO);
        } catch (Exception e) {
            log.error("添加角色异常：", e);
            throw new BizException("SYS40002");
        }
        return R.ok();
    }

    @PostMapping("/update")
    @ResponseBody()
    public R update(RoleReqVO reqVO) {
        try {
            reqVO.setUpdateManager(getManagerId());
            reqVO.setUpdateTime(new Date());
            roleService.update(reqVO);
        } catch (Exception e) {
            log.error("修改角色异常：", e);
            throw new BizException("SYS40001");
        }
        return R.ok();
    }

    @PostMapping("/remove")
    @ResponseBody()
    public R remove(Integer id) {
        try {
            roleService.remove(id);
        } catch (Exception e) {
            log.error("删除角色异常：", e);
            throw new BizException("SYS40003");
        }
        return R.ok();
    }

    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") Integer[] ids) {
        try {
            roleService.batchRemove(ids);
        } catch (Exception e) {
            log.error("删除角色异常：", e);
            throw new BizException("SYS40003");
        }
        return R.ok();
    }
}
