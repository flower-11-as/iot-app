package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Param;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.ParamService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.sys.param.ParamListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Description:
 * Created by as on 2018/5/1.
 */
@RequestMapping("/sys/param")
@Controller
@Slf4j
public class ParamController extends BaseController {

    private static final String prefix = "/system/param";

    @Autowired
    private ParamService paramService;

    @GetMapping
    public String param() {
        return prefix + "/param";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<Param> list(@RequestBody ParamListReqVO reqVO) {
        PageRespVO<Param> pageRespVO = new PageRespVO<>();
        pageRespVO.setRows(paramService.list(reqVO));
        pageRespVO.setTotal(paramService.count(reqVO));
        return pageRespVO;
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Param param = paramService.get(id);
        model.addAttribute("sysParam", param);
        return prefix + "/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public R update(Param param) {
        try {
            param.setUpdateManager(getManagerId());
            param.setUpdateTime(new Date());

            if (!paramService.update(param)) {
                throw new BizException("SYS12002");
            }
        } catch (Exception e) {
            log.error("修改参数异常：", e);
            throw new BizException("SYS12002");
        }
        return R.ok();
    }

    @GetMapping("/add")
    public String add(Model model) {
        return prefix + "/add";
    }

    @PostMapping("/save")
    @ResponseBody
    public R save(Param param) {
        try {
            param.setCreateManager(getManagerId());
            Date now = new Date();
            param.setCreateTime(now);
            param.setUpdateTime(now);
            if (!paramService.save(param)) {
                throw new BizException("SYS12001");
            }
        } catch (Exception e) {
            log.error("添加param异常：", e);
            throw new BizException("SYS12001");
        }
        return R.ok();
    }

    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        try {
            if (!paramService.remove(id)) {
                throw new BizException("SYS12003");
            }
        } catch (Exception e) {
            log.error("删除param异常：", e);
            throw new BizException("SYS12003");
        }
        return R.ok();
    }
}
