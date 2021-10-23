package com.bjpowernode.crm.base.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.db.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class VerifyCodeController {
@RequestMapping("/code")
public void getVerifyCode(HttpServletResponse httpServletResponse, HttpSession httpSession){



    //定义图形验证码的长、宽、验证码字符数、干扰元素个数
    CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 10);
    String code = captcha.getCode();
    httpSession.setAttribute("correctVerifyCode", code);
    /*//定义图形验证码的长、宽、验证码字符数、干扰线宽度
    ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
    String code = captcha.getCode();
    httpSession.setAttribute("correctVerifyCode",code);*/
//    图形验证码写出到流
    ServletOutputStream outputStream = null;
    try {
       outputStream = httpServletResponse.getOutputStream();
        captcha.write(outputStream);

    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            }
        }
    }
}
