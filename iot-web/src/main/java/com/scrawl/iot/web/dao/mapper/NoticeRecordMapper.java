package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.NoticeRecord;
import com.scrawl.iot.web.vo.sys.notice.NoticeRecordListReqVO;

import java.util.List;

public interface NoticeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeRecord record);

    int insertSelective(NoticeRecord record);

    NoticeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeRecord record);

    int updateByPrimaryKey(NoticeRecord record);

    List<NoticeRecord> selectPageList(NoticeRecordListReqVO reqVO);

    int selectPageCount(NoticeRecordListReqVO reqVO);
}