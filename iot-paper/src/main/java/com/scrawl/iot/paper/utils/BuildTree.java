package com.scrawl.iot.paper.utils;


import com.scrawl.iot.paper.domain.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTree {

    public static  Tree build(List<Tree> nodes) {
        if (nodes == null) {
            return null;
        }
        List<Tree> topNodes = new ArrayList<>();

        for (Tree children : nodes) {

            String pid = children.getParentId();
            if (pid == null || "0".equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setHasChildren(true);
                    if (null != parent.getState()) {
                        parent.getState().remove("selected");
                    }
                }
            }
        }

        Tree root = new Tree();
        if (topNodes.size() == 1) {
            root = topNodes.get(0);
        } else {
            root.setId("-1");
            root.setParentId("");
            root.setHasParent(false);
            root.setHasChildren(true);
            root.setChecked(true);
            root.setChildren(topNodes);
            root.setText("顶级节点");
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            root.setState(state);
        }

        return root;
    }

    public static  List<Tree> buildList(List<Tree> nodes, String idParam) {
        if (nodes == null) {
            return null;
        }
        List<Tree> topNodes = new ArrayList<>();

        for (Tree children : nodes) {

            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setHasChildren(true);
                    if (null != parent.getState()) {
                        parent.getState().remove("selected");
                    }
                }
            }

        }
        return topNodes;
    }

}