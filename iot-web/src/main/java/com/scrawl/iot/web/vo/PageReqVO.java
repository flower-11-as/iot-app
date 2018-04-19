package com.scrawl.iot.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PageReqVO implements Serializable {
    private static final long serialVersionUID = -771009076935870066L;

    protected int limit;
    protected int offset;
}
