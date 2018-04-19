package com.scrawl.iot.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PageRespVO<T> implements Serializable {
    private static final long serialVersionUID = 3668361830279927101L;

    private Integer total = 0;
    private List<T> rows = new ArrayList<>();
}
