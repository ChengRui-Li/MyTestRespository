package com.bjpowernode.crm.base.exception;

import lombok.Data;

public enum CrmExceptionEnum {

    USER_LOGIN_USERNAME_PASSWORD("001-001-001","用户名或者密码输入错误"),
    USER_LOGIN_VERIFY_CODE("001-001-002","验证码输入错误"),
    USER_LOGIN_EXPIRE_TIME("001-001-003","您的帐号已失效"),
    USER_LOGIN_LOCKSTATE("001-001-004","您的账号已经被冻结，请联系管理员"),
    USER_LOGIN_ALLOWIP("001-001-005","您的ip地址被禁止访问"),
    USER_UPDATE_OLDPWDVERIFY("001-002-001","原始密码输入错误"),
    USER_UPDATE_INFO("001-002-002","更新用户失败"),
    USER_UPDATE_SUFFIX("001-002-003","只允许上传后缀名为:jpg,png,webp,gif的图片"),
    USER_UPDATE_SIZE("001-002-004","上传的图片不能超过2Mb"),
    USER_ACTIVITY_ADD("001-003-001","添加市场活动失败"),
    USER_ACTIVITY_UPDATE("001-003-002","更新市场活动失败"),
    USER_ACTIVITY_DELETEBENCH("001-003-003","删除市场活动失败"),
    USER_ACTIVITY_REMARK("001-003-004","添加市场活动备注失败"),
    USER_ACTIVITY_UPDATEREMARK("001-003-005","更新市场活动备注失败"),
    USER_ACTIVITY_DELETEREMARK("001-003-006","删除市场活动失败"),
    USER_CLUE_UPDATE("001-004-001","添加线索失败"),
    USER_CLUE_BIND("001-004-002","绑定市场活动失败"),
    USER_CLUE_CONVERT("001-004-003","线索转换失败"),
    USER_CUSTOMER_INSERT("001-005-001","添加客户失败"),
    USER_CUSTOMER_UPDATE("001-005-002","修改客户信息失败"),
    USER_CUSTOMER_DELETE("001-005-003","删除客户信息失败"),
    USER_CUSTOMER_ADD_REMARK("001-005-004","添加客户备注信息失败"),
    USER_CUSTOMER_UPDATE_REMARK("001-005-004","更新客户备注信息失败"),
    USER_CUSTOMER_DELETE_REMARK("001-005-005","删除客户备注信息失败");

    private String code;
    private String message;

    CrmExceptionEnum(String code, String message) {
//        此处必须赋值否则枚举类型中均无值
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
