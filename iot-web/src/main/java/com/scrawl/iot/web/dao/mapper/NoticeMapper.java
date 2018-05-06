package com.scrawl.iot.web.dao.mapper;

import com.scrawl.iot.web.dao.entity.Notice;
import com.scrawl.iot.web.vo.sys.notice.NoticeListReqVO;

import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> selectPageList(NoticeListReqVO reqVO);

    int selectPageCount(NoticeListReqVO reqVO);
}