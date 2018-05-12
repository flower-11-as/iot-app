package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.entity.NoticeRecord;
import com.scrawl.iot.web.dao.mapper.NoticeRecordMapper;
import com.scrawl.iot.web.service.ManagerService;
import com.scrawl.iot.web.service.NoticeRecordService;
import com.scrawl.iot.web.vo.sys.notice.NoticeRecordListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/5/7.
 */
@Service
@Slf4j
public class NoticeRecordServiceImpl implements NoticeRecordService {

    @Autowired
    private NoticeRecordMapper noticeRecordMapper;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private SimpMessagingTemplate template;

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

    @Override
    public void sendNotice(NoticeRecord noticeRecord) {
        Map<Integer, Manager> managerMap = managerService.listOnlineManager().stream().collect(
                Collectors.toMap(Manager::getId, Function.identity(), (v1, v2) -> v2));

        if (!managerMap.containsKey(noticeRecord.getManagerId())) {
            return;
        }

        template.convertAndSendToUser(managerMap.get(noticeRecord.getManagerId()).toString(),
                "/queue/notifications", noticeRecord);
    }
}
