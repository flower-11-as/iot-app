package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.NoticeRecord;
import com.scrawl.iot.web.dao.mapper.NoticeRecordMapper;
import com.scrawl.iot.web.service.NoticeRecordService;
import com.scrawl.iot.web.vo.sys.notice.NoticeRecordListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/7.
 */
@Service
@Slf4j
public class NoticeRecordServiceImpl implements NoticeRecordService {

    @Autowired
    private NoticeRecordMapper noticeRecordMapper;

    @Override
    public List<NoticeRecord> list(NoticeRecordListReqVO reqVO) {
        return noticeRecordMapper.selectPageList(reqVO);
    }

    @Override
    public int count(NoticeRecordListReqVO reqVO) {
        return noticeRecordMapper.selectPageCount(reqVO);
    }

    @Override
    public NoticeRecord get(Integer id) {
        return noticeRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(NoticeRecord noticeRecord) {
        return noticeRecordMapper.insertSelective(noticeRecord) > 0;
    }

    @Override
    public boolean update(NoticeRecord noticeRecord) {
        return noticeRecordMapper.updateByPrimaryKeySelective(noticeRecord) > 0;
    }
}
