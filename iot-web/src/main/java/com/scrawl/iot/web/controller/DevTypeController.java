package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.DevType;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.DevTypeInfoService;
import com.scrawl.iot.web.service.DevTypeService;
import com.scrawl.iot.web.vo.PageRespVO;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.iot.devType.DevTypeListReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 * Created by as on 2018/4/22.
 */
@Controller
@RequestMapping("/iot/devType")
@Slf4j
public class DevTypeController extends BaseController {
    private static final String prefix = "/iot/devType";

    @Autowired
    private DevTypeService devTypeService;

    @Autowired
    private DevTypeInfoService devTypeInfoService;

    @GetMapping
    public String server() {
        return prefix + "/devType";
    }

    @PostMapping("/list")
    @ResponseBody
    public PageRespVO<DevType> list(@RequestBody DevTypeListReqVO reqVO) {
        PageRespVO<DevType> pageRespVO = new PageRespVO<>();

        // 获取当前管理员绑定iot账户，进行筛选
        reqVO.setServerIds(getManagerServerIds());

        pageRespVO.setRows(devTypeService.list(reqVO));
        pageRespVO.setTotal(devTypeService.count(reqVO));

        return pageRespVO;
    }

    @PostMapping("syncDevTypes")
    @ResponseBody
    public R syncDevTypes() {
        try {
            List<String> serverIds = getManagerServerIds();
            if (null == serverIds || serverIds.size() == 0) {
                throw new BizException("SYS10003");
            }
            devTypeService.syncDevTypes(getManagerServerIds(), getManagerId());
        } catch (BizException e) {
            log.error("同步IoT产品型号异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("同步IoT产品型号异常：", e);
            throw new BizException("SYS80001");
        }
        return R.ok();
    }

    @GetMapping("/info")
    public String info(@RequestParam("id") Integer id, Model model) {
        DevType devType = devTypeService.get(id);
        // devType
        model.addAttribute("devType", devType);

        // devType message info
        model.addAttribute("messageInfos", devTypeInfoService.getDevTypeMessages(id));

        // devType commend info
        model.addAttribute("commendInfos", devTypeInfoService.getDevTypeCommends(id));

        return prefix + "/info";
    }
}
