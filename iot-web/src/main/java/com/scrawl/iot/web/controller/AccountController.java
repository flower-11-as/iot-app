package com.scrawl.iot.web.controller;

import com.scrawl.iot.web.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 * Created by as on 2018/4/20.
 */
@RequestMapping("/iot/account")
@Controller
@Slf4j
public class AccountController extends BaseController {
    private static final String prefix = "/iot/account";

    @Autowired
    private AccountService accountService;


}
