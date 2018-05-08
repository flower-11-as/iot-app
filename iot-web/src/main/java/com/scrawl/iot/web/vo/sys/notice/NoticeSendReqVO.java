package com.scrawl.iot.web.vo.sys.notice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/8.
 */
@Setter
@Getter
public class NoticeSendReqVO {
    private Integer id;
    private List<Integer> managerIds;
}
