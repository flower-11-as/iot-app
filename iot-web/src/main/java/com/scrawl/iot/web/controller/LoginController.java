package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.dao.entity.Menu;
import com.scrawl.iot.web.vo.Tree;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.service.MenuService;
import com.scrawl.iot.web.vo.R;
import com.scrawl.iot.web.vo.sys.LoginReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Desc:
 * Create by scrawl on 2018/3/26
 */
@Controller
@Slf4j
public class LoginController extends BaseController{

    @Autowired
    private MenuService menuService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/iotLogin")
    @ResponseBody
    public R iotLogin(@Validated LoginReqVO vo) {
        UsernamePasswordToken token = new UsernamePasswordToken(vo.getUsername(), vo.getPassword());
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
            R r = R.ok();
            return r;
        } catch (AuthenticationException e) {
            throw new BizException("SYS10001");
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Tree<Menu>> menus = menuService.listMenuTree(getManagerId());
        model.addAttribute("menus", menus);
        return "index_iot";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
