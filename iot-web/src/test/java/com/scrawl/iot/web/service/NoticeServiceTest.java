package com.scrawl.iot.web.service;

import com.alibaba.fastjson.JSON;
import com.scrawl.iot.paper.domain.Tree;
import com.scrawl.iot.paper.utils.BuildTree;
import com.scrawl.iot.web.dao.entity.Manager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Create by scrawl on 2018/5/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NoticeServiceTest {

    @Autowired
    private ManagerService managerService;

    @Test
    public void managerTree() {
        List<Manager> managers = managerService.list(new Manager());
        List<Tree<Manager>> trees = new ArrayList<>();
        managers.forEach(manager -> {
            Tree<Manager> managerTree = new Tree<>();
            managerTree.setId(manager.getId().toString());
            managerTree.setText(manager.getName());

            trees.add(managerTree);
        });
        Tree<Manager> t = BuildTree.build(trees);
        t.setText("管理员");
        System.out.println(JSON.toJSONString(t));
    }
}
