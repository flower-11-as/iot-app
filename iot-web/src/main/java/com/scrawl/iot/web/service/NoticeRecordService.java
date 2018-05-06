package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.NoticeRecord;
import com.scrawl.iot.web.vo.sys.notice.NoticeRecordListReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/6.
 */
public interface NoticeRecordService {

    List<NoticeRecord> list(NoticeRecordListReqVO reqVO);

    int count(NoticeRecordListReqVO reqVO);

    NoticeRecord get(Integer id);

    boolean save(NoticeRecord noticeRecord);

    boolean update(NoticeRecord noticeRecord);
}
