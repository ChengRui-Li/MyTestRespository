package com.bjpowernode.crm.base.util;

import com.bjpowernode.crm.settings.bean.User;

import javax.servlet.http.HttpSession;

public class CommonUtil {
    public static User getUserBySession(HttpSession session){ return (User) session.getAttribute("user"); }
}
