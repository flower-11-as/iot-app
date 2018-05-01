package com.scrawl.iot.web.vo.sys.job;

import com.scrawl.iot.web.vo.PageReqVO;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Setter
@Getter
public class JobListReqVO extends PageReqVO {
    private String jobName;
}
