package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Notice;
import com.scrawl.iot.web.vo.sys.notice.NoticeListReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/6.
 */
public interface NoticeService {

    List<Notice> list(NoticeListReqVO reqVO);

    int count(NoticeListReqVO reqVO);

    Notice get(Integer id);

    boolean save(Notice notice);

    boolean update(Notice notice);

    boolean remove(Integer id);

    void sendNotice(Integer id, List<Integer> managerIds);
}
