package com.scrawl.iot.web.service;

import com.scrawl.iot.web.dao.entity.Param;
import com.scrawl.iot.web.vo.sys.param.ParamListReqVO;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
public interface ParamService {
    List<Param> list(ParamListReqVO req);

    int count(ParamListReqVO req);

    List<Param> list(Param param);

    boolean save(Param param);

    boolean remove(Integer id);

    Param get(Integer id);

    Param get(String paramKey);

    boolean update(Param param);
}
