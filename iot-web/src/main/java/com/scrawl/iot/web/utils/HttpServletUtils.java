package com.scrawl.iot.web.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Desc:
 * Create by scrawl on 2018/3/27
 */
public class HttpServletUtils {
    public static boolean jsAjax(HttpServletRequest req) {
        //判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        if (!StringUtils.isBlank(req.getHeader("x-requested-with")) && req.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            isAjaxRequest = true;
        }
        return isAjaxRequest;
    }
}
