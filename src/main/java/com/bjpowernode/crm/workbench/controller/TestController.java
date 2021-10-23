package com.bjpowernode.crm.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/workbench/main/toIndex")
    public String toIndex(){
//      转发到main/index.jsp
        return "workbench/main/index";
    }
}
