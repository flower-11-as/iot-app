package com.scrawl.iot.web.service.impl;

import com.scrawl.iot.web.dao.entity.Param;
import com.scrawl.iot.web.dao.mapper.ParamMapper;
import com.scrawl.iot.web.service.ParamService;
import com.scrawl.iot.web.vo.sys.param.ParamListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@Service
@Slf4j
public class ParamServiceImpl implements ParamService {

    @Autowired
    private ParamMapper paramMapper;

    @Override
    public List<Param> list(ParamListReqVO req) {
        return paramMapper.selectPageList(req);
    }

    @Override
    public int count(ParamListReqVO req) {
        return paramMapper.selectPageCount(req);
    }

    @Override
    public List<Param> list(Param param) {
        return paramMapper.selectBySelective(param);
    }

    @Override
    public boolean save(Param param) {
        return paramMapper.insertSelective(param) > 0;
    }

    @Override
    public boolean remove(Integer id) {
        return paramMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public Param get(Integer id) {
        return paramMapper.selectByPrimaryKey(id);
    }

    @Override
    public Param get(String paramKey) {
        return paramMapper.selectByParamKey(paramKey);
    }

    @Override
    public boolean update(Param param) {
        return paramMapper.updateByPrimaryKeySelective(param) > 0;
    }
}
