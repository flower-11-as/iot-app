package com.scrawl.iot.web.constants;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
public class JobConstant {
    // 停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    // 开启计划任务
    public static String STATUS_RUNNING_START = "start";
    // job启动状态
    public static final String STATUS_RUNNING = "1";
    // job关闭状态
    public static final String STATUS_NOT_RUNNING = "0";
    // job并发执行
    public static final String CONCURRENT_IS = "1";
    // job非并发执行
    public static final String CONCURRENT_NOT = "0";
}
