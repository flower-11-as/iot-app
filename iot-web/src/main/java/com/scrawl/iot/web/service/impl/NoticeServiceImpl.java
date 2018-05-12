package com.scrawl.iot.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.Notice;
import com.scrawl.iot.web.dao.entity.NoticeRecord;
import com.scrawl.iot.web.dao.mapper.NoticeMapper;
import com.scrawl.iot.web.dao.mapper.NoticeRecordMapper;
import com.scrawl.iot.web.enums.NoticeRecordStatusEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.ManagerService;
import com.scrawl.iot.web.service.NoticeRecordService;
import com.scrawl.iot.web.service.NoticeService;
import com.scrawl.iot.web.vo.sys.notice.NoticeListReqVO;
import com.scrawl.iot.web.vo.sys.notice.NoticeSendReqVO;
import groovy.util.logging.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/5/6.
 */
@Service
@Log4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeRecordService noticeRecordService;

    @Override
    public List<Notice> list(NoticeListReqVO reqVO) {
        return noticeMapper.selectPageList(reqVO);
    }

    @Override
    public int count(NoticeListReqVO reqVO) {
        return noticeMapper.selectPageCount(reqVO);
    }

    @Override
    public Notice get(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(Notice notice) {
        return noticeMapper.insertSelective(notice) > 0;
    }

    @Override
    public boolean update(Notice notice) {
        return noticeMapper.updateByPrimaryKeySelective(notice) > 0;
    }

    @Override
    public boolean remove(Integer id) {
        return noticeMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public void sendNotice(NoticeSendReqVO reqVO) {
        Notice notice = this.get(reqVO.getId());
        if (null == notice) {
            throw new BizException("SYS13001");
        }

        if (null == reqVO.getManagerIds() || reqVO.getManagerIds().size() == 0) {
            throw new BizException("SYS13002");
        }



        Date now = new Date();
        reqVO.getManagerIds().forEach(managerId -> {
            // 添加发送记录
            NoticeRecord noticeRecord = new NoticeRecord();
            noticeRecord.setManagerId(managerId);
            noticeRecord.setType(notice.getType());
            noticeRecord.setTitle(notice.getTitle());
            noticeRecord.setContent(notice.getContent());
            noticeRecord.setStatus(NoticeRecordStatusEnum.NOT_READ.getCode());
            noticeRecord.setCreateTime(now);
            noticeRecordService.save(noticeRecord);

            CompletableFuture.runAsync(() -> {
                noticeRecordService.sendNotice(noticeRecord);
            });
        });
    }
}
