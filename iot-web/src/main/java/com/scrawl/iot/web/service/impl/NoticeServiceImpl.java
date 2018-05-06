package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Notice;
import com.scrawl.iot.web.dao.mapper.NoticeMapper;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.NoticeService;
import com.scrawl.iot.web.vo.sys.notice.NoticeListReqVO;
import groovy.util.logging.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/6.
 */
@Service
@Log4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

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
    public void sendNotice(Integer id, List<Integer> managerIds) {
        Notice notice = this.get(id);
        if (null == notice) {
            throw new BizException("SYS13001");
        }

        if (null == managerIds || managerIds.size() == 0) {
            throw new BizException("SYS13002");
        }

        managerIds.forEach(managerId -> {
            // TODO:发送通知
        });
    }
}
