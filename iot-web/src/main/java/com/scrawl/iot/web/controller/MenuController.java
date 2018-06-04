package com.scrawl.iot.web.controller;

/**
 * Description:
 * Created by as on 2018/4/9.
 */

import com.scrawl.iot.web.dao.entity.Menu;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.MenuService;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.paper.domain.Tree;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author bootdo 1992lcg@163.com
 */
@RequestMapping("/sys/menu")
@Controller
@Slf4j
public class MenuController extends BaseController {

    private String prefix = "/system/menu";

    @Autowired
    MenuService menuService;

    @GetMapping()
    @RequiresPermissions("sys:menu:menu")
    public String menu(ModelAndView modelAndView) {
        return prefix + "/menu";
    }

    @PostMapping("/list")
    @ResponseBody
    public List<Menu> list() {
        return menuService.list();
    }

    @RequiresPermissions("sys:menu:add")
    @GetMapping("/add/{pId}")
    String add(@PathVariable("pId") Integer pId,Model model) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.get(pId).getName());
        }
        return prefix + "/add";
    }

    @RequiresPermissions("sys:menu:edit")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Menu menu = menuService.get(id);
        Integer pId = menu.getParentId();
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.get(pId).getName());
        }
        model.addAttribute("menu", menu);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:menu:add")
    @PostMapping("/save")
    @ResponseBody
    public R save(Menu menu) {
        try {
            Date now = new Date();
            menu.setCreateTime(now);
            menu.setCreateManager(getManagerId());
            menu.setUpdateTime(now);
            if (menuService.save(menu) > 0) {
                return R.ok();
            } else {
                throw new BizException("SYS20001");
            }
        } catch (Exception e) {
            log.error("添加菜单异常：", e);
            throw new BizException("SYS20001");
        }
    }

    @RequiresPermissions("sys:menu:edit")
    @PostMapping("/update")
    @ResponseBody
    public R update(Menu menu) {
        try {
            Date now = new Date();
            menu.setUpdateManager(getManagerId());
            menu.setUpdateTime(now);
            if (menuService.update(menu) > 0) {
                return R.ok();
            } else {
                throw new BizException("SYS20003");
            }
        } catch (Exception e) {
            log.error("编辑菜单异常：", e);
            throw new BizException("SYS20003");
        }
    }

    @RequiresPermissions("sys:menu:remove")
    @PostMapping("/remove")
    @ResponseBody
    R remove(Integer id) {
        try {
            if (menuService.remove(id) > 0) {
                return R.ok();
            } else {
                throw new BizException("SYS20002");
            }
        } catch (Exception e) {
            log.error("删除菜单异常：", e);
            throw new BizException("SYS20002");
        }
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree tree() {
        return menuService.getTree();
    }

    @GetMapping("/tree/{roleId}")
    @ResponseBody
    public Tree tree(@PathVariable("roleId") Integer roleId) {
        return menuService.getTree(roleId);
    }
}
