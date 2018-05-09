package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.NoticeRecord;
import com.scrawl.iot.web.enums.NoticeRecordStatusEnum;
import com.scrawl.iot.web.service.NoticeRecordService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.sys.notice.NoticeRecordListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Description:
 * Created by as on 2018/5/7.
 */
@RequestMapping("/sys/noticeRecord")
@Controller
@Slf4j
public class NoticeRecordController extends BaseController {
    private String prefix = "/system/noticeRecord";

    @Autowired
    private NoticeRecordService noticeRecordService;

    @GetMapping()
    public String noticeRecord(ModelAndView modelAndView) {
        return prefix + "/noticeRecord";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<NoticeRecord> list(@RequestBody NoticeRecordListReqVO reqVO) {
        reqVO.setManagerId(getManagerId());
        PageRespVO<NoticeRecord> pageRespVO = new PageRespVO<>();
        pageRespVO.setRows(noticeRecordService.list(reqVO));
        pageRespVO.setTotal(noticeRecordService.count(reqVO));
        return pageRespVO;
    }

    @GetMapping("/check/{id}")
    public String check(Model model, @PathVariable("id") Integer id) {
        NoticeRecord noticeRecord = noticeRecordService.get(id);
        model.addAttribute("noticeRecord", noticeRecord);

        if (noticeRecord.getStatus().equals(NoticeRecordStatusEnum.NOT_READ.getCode())) {
            noticeRecord.setStatus(NoticeRecordStatusEnum.READ.getCode());
            noticeRecord.setReadTime(new Date());
            noticeRecordService.update(noticeRecord);
        }
        return prefix + "/check";
    }
}
