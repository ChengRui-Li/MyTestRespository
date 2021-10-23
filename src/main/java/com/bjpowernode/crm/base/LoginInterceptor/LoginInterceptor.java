package com.bjpowernode.crm.base.LoginInterceptor;

import com.bjpowernode.crm.settings.bean.User;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.remoting.httpinvoker.HttpInvokerClientInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
//            重定向到登录页面
            response.sendRedirect("/crm/login.jsp");
            return false;
        }

        return true;
    }

}
