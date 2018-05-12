package com.scrawl.iot.web.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageReqVO {
    protected int limit = 10;
    protected int offset = 0;
}
