package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Menu;
import com.scrawl.iot.web.dao.mapper.MenuMapper;
import com.scrawl.iot.web.vo.Tree;
import com.scrawl.iot.web.service.MenuService;
import com.scrawl.iot.web.utils.BuildTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Tree<Menu>> listMenuTree(Integer id) {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menuDOs = menuMapper.listMenuByManagerId(id);
        for (Menu sysMenuDO : menuDOs) {
            Tree<Menu> tree = new Tree<>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.buildList(trees, "0");
    }

    @Override
    public List<Menu> list() {
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
}
