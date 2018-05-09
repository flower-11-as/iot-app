package com.scrawl.iot.web.controller;

import com.scrawl.iot.paper.domain.Tree;
import com.scrawl.iot.paper.utils.BuildTree;
import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.Notice;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.ManagerService;
import com.scrawl.iot.web.service.NoticeService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.sys.notice.NoticeListReqVO;
import com.scrawl.iot.web.vo.sys.notice.NoticeSendReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/6.
 */
@Controller
@RequestMapping("/sys/notice")
@Slf4j
public class NoticeController extends BaseController {

    private String prefix = "/system/notice";

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ManagerService managerService;

    @GetMapping()
    public String notice(ModelAndView modelAndView) {
        return prefix + "/notice";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<Notice> list(@RequestBody NoticeListReqVO reqVO) {
        PageRespVO<Notice> pageRespVO = new PageRespVO<>();
        pageRespVO.setRows(noticeService.list(reqVO));
        pageRespVO.setTotal(noticeService.count(reqVO));
        return pageRespVO;
    }

    @GetMapping("/add")
    public String add(Model model) {
        return prefix + "/add";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(Notice notice) {
        try {
            notice.setCreateManager(getManagerId());
            Date now = new Date();
            notice.setCreateTime(now);
            notice.setUpdateTime(now);
            if (!noticeService.save(notice)) {
                throw new BizException("SYS13003");
            }
        } catch (Exception e) {
            log.error("添加通知异常：", e);
            throw new BizException("SYS13003");
        }
        return R.ok();
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Notice notice = noticeService.get(id);
        model.addAttribute("notice", notice);
        return prefix + "/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public R update(Notice notice) {
        try {
            notice.setUpdateManager(getManagerId());
            notice.setUpdateTime(new Date());
            if (!noticeService.update(notice)) {
                throw new BizException("SYS13004");
            }
        } catch (Exception e) {
            log.error("修改通知异常：", e);
            throw new BizException("SYS13004");
        }
        return R.ok();
    }

    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        try {
            if (!noticeService.remove(id)) {
                throw new BizException("SYS13005");
            }
        } catch (Exception e) {
            log.error("删除通知异常：", e);
            throw new BizException("SYS13005");
        }
        return R.ok();
    }

    @GetMapping("/toSendNotice/{id}")
    public String toSendNotice(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("notice", noticeService.get(id));
        return prefix + "/sendNotice";
    }

    @GetMapping("/managerTree")
    @ResponseBody
    public Tree<Manager> managerTree() {
        List<Manager> managers = managerService.list(new Manager());
        List<Tree<Manager>> trees = new ArrayList<>();
        managers.forEach(manager -> {
            Tree<Manager> managerTree = new Tree<>();
            managerTree.setId(manager.getId().toString());
            managerTree.setText(manager.getName());

            trees.add(managerTree);
        });

        Tree<Manager> rs = BuildTree.build(trees);
        rs.setText("管理员");
        return rs;
    }

    @PostMapping("/sendNotice")
    @ResponseBody
    public R sendNotice(NoticeSendReqVO reqVO) {
        try {
            reqVO.getManagerIds().remove(new Integer(-1));
            noticeService.sendNotice(reqVO);
        } catch (Exception e) {
            log.error("发送通知异常：", e);
            throw new BizException("SYS13006");
        }
        return R.ok();
    }
}
