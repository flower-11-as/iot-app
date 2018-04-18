package com.scrawl.iot.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Desc:
 * Create by scrawl on 2018/4/2
 */
@Getter
@Setter
@Builder
public class R<T> implements Serializable {
    private static final long serialVersionUID = -8245020519850698486L;

    @ApiModelProperty(value = "0000(业务响应码)", required = true, example = "0000")
    @Builder.Default
    private String code = "0000";

    @ApiModelProperty(value = "success(提示信息)", required = true, example = "success")
    @Builder.Default
    private String msg = "success";

    @ApiModelProperty(value = "业务数据")
    private T data;

    private R(String code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public static R ok() {
        return R.builder().build();
    }

    public static <T> R ok(T data) {
        return R.builder().data(data).build();
    }

    public static R error(String code, String msg) {
        return R.builder().code(code).msg(msg).build();
    }

    public static <T> R error(String code, String msg, T data) {
        return R.builder().code(code).msg(msg).data(data).build();
    }
}
