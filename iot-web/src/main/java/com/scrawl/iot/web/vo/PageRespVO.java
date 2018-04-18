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
@ApiModel("分页数据")
public class PageRespVO<T> implements Serializable {
    private static final long serialVersionUID = 3668361830279927101L;

    @ApiModelProperty(value = "总记录数", required = true)
    private Integer total = 0;
    @ApiModelProperty(value = "当前页列表数据", required = true)
    private List<T> rows = new ArrayList<>();
}
