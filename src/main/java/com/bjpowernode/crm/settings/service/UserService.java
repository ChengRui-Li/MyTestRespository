package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.bean.User;

public interface UserService {
    User login(User user, String code, String correctVerifyCode);

    void verifyOldPwd(String oldPwd, String correctPwd);

    void updateUser(User user);
}
