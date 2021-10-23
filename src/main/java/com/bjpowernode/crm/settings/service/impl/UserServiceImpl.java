package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.exception.CrmExceptionEnum;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user, String code, String correctVerifyCode) {
//          第一步校验验证码equalsIgnoreCase();比较的时候不区分大小写
        if (!correctVerifyCode.equalsIgnoreCase(code)) {
//            验证码不正确手动抛异常
            throw new CrmException(CrmExceptionEnum.USER_LOGIN_VERIFY_CODE);
        }
//      重新加密来自密码输入框的明文密码
        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
//      从数据库中查询是否存在与前台输入user相对应的数据（方法一采用普通的select方式）
//        List<User> list = userMapper.select(user);
//        方法二采用example条件查询
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginAct", user.getLoginAct()).andEqualTo("loginPwd", user.getLoginPwd());
        List<User> list = userMapper.selectByExample(example);

        if (list.size() == 0) {
//      手动抛异常用户不存在或者密码错误
            throw new CrmException(CrmExceptionEnum.USER_LOGIN_USERNAME_PASSWORD);

        }
//        检测账号是否失效
//        首先获取系统当前时间
        String sysTime = DateTimeUtil.getSysTime();
        User user1 = list.get(0);
        String expireTime = user1.getExpireTime();
        if (expireTime.compareTo(sysTime) < 0) {
            throw new CrmException(CrmExceptionEnum.USER_LOGIN_EXPIRE_TIME);

        }
//        检测账号是否被冻结，0为被冻结1为未被冻结
        if ("0".equals(user1.getLockState())) {
            throw new CrmException(CrmExceptionEnum.USER_LOGIN_LOCKSTATE);
        }
//      检测登录目标主机的ip地址是否为允许范围内的ip
        String allowIps1 = user.getAllowIps();
        String allowIps = user1.getAllowIps();
        if (!allowIps.contains(allowIps1)) {
            throw new CrmException(CrmExceptionEnum.USER_LOGIN_ALLOWIP);
        }


        return user1;
    }

    @Override
    public void verifyOldPwd(String oldPwd, String correctPwd) {
//        判断前台输入框中的密码是否与数据库中的一致
            oldPwd = MD5Util.getMD5(oldPwd);
//            该处if条件语句
        if (!oldPwd.equals(correctPwd)) {
//            密码不正确抛出新的异常
            throw new CrmException(CrmExceptionEnum.USER_UPDATE_OLDPWDVERIFY);
        }
    }

    @Override
    public void updateUser(User user) {
//        密码加密
        if (!(user.getLoginPwd() == null)) {
            user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
        }
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i == 0) {
            throw new CrmException(CrmExceptionEnum.USER_UPDATE_INFO);
        }

    }
}
