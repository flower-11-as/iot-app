package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Menu;
import com.scrawl.iot.web.dao.mapper.MenuMapper;
import com.scrawl.iot.web.service.MenuService;
import com.scrawl.iot.paper.utils.BuildTree;
import com.scrawl.iot.paper.domain.Tree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/4/9.
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Tree> listMenuTree(Integer id) {
        List<Tree> trees = new ArrayList<>();
        List<Menu> Menus = menuMapper.listMenuByManagerId(id);
        for (Menu sysMenu : Menus) {
            Tree tree = new Tree();
            tree.setId(sysMenu.getId().toString());
            tree.setParentId(sysMenu.getParentId().toString());
            tree.setText(sysMenu.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenu.getUrl());
            attributes.put("icon", sysMenu.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.buildList(trees, "0");
    }

    @Override
    public List list() {
        return menuMapper.list();
    }

    @Override
    public int save(Menu menu) {
        return menuMapper.insertSelective(menu);
    }

    @Override
    public int update(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int remove(Integer id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Menu get(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public Tree getTree() {
        List<Tree> trees = new ArrayList<>();
        List<Menu> menus = menuMapper.list();
        for (Menu menu : menus) {
            Tree tree = new Tree();
            tree.setId(menu.getId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree getTree(Integer roleId) {
        // 根据roleId查询权限
        List<Menu> roleMenus = menuMapper.listMenuByRoleId(roleId);
        List<Integer> roleMenuIds = roleMenus.stream().map(Menu::getId).collect(Collectors.toList());
        List<Tree> trees = new ArrayList<>();
        List<Menu> menus = menuMapper.list();
        for (Menu sysMenu : menus) {
            Tree tree = new Tree();
            tree.setId(sysMenu.getId().toString());
            tree.setParentId(sysMenu.getParentId().toString());
            tree.setText(sysMenu.getName());
            Map<String, Object> state = new HashMap<>(16);
            Integer menuId = sysMenu.getId();
            if (roleMenuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.build(trees);
    }
}
