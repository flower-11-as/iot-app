package com.scrawl.iot.web.vo.iot.device;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/5/13.
 */
@Setter
@Getter
public class DeviceReportRespVO {

    // 报表标题
    private String title;

    // 分类
    private List<String> legend;

    // x轴坐标
    private List<String> xaxis;

    // 数据
    private Map<String, List<Float>> series;
}
