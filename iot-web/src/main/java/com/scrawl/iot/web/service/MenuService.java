package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Menu;
import com.scrawl.iot.web.vo.Tree;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/9.
 */
public interface MenuService {
    List<Tree<Menu>> listMenuTree(Integer id);

    List<Menu> list();

    int save(Menu menu);

    int update(Menu menu);

    int remove(Integer id);

    Menu get(Integer id);
}
