package com.bjpowernode.crm.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Enumeration;

@Controller
public class ViewController {
    @RequestMapping(value = {"/toView/{firstView}/{secondView}",
                             "/toView/{firstView}/{secondView}/{thirdView}",
                             "/toView/{firstView}/{secondView}/{thirdView}/{fourthView}"})
    public String toView(@PathVariable(value = "firstView",required = false) String firstView,
                         @PathVariable(value = "secondView",required = false) String secondView,
                         @PathVariable(value = "thirdView",required = false) String thirdView,
                         @PathVariable(value = "fourthView",required = false) String fourthView,
                         HttpServletRequest request){
//        获取地址栏传参的所有参数的名字
        Enumeration<String> parameterNames = request.getParameterNames();
//        遍历参数名字枚举
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
//         地址栏传递的参数的value在当前request域对象中(由于)
            String value = request.getParameter(name);
            request.setAttribute(name, value);
        }
        if (thirdView != null) {
            if (fourthView != null) {
                return firstView + File.separator + secondView + File.separator + thirdView+ File.separator + fourthView;
            } else {
                return firstView + File.separator + secondView + File.separator + thirdView;
            }
        }
        return firstView + File.separator + secondView;
    }
}
