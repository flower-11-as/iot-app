package com.scrawl.iot.web.vo.sys.notice;

import com.scrawl.iot.web.vo.PageReqVO;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/5/6.
 */
@Setter
@Getter
public class NoticeRecordListReqVO extends PageReqVO{
    private String title;
    private Integer managerId;
    private Byte status;
}
