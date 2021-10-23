package com.bjpowernode.crm.settings.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
// 实体类一共标记四个注解

@Data
@Table(name = "tbl_user")
@NameStyle(Style.normal)
public class User {
    @Id
    private String id;//用户ID主键
    private String loginAct;//登录账号
    private String name;//昵称
    private String loginPwd;//登录密码
    private String email;//邮箱
    private String expireTime;//失效时间
    private String lockState;//是否被锁定
    private String deptno;
    private String allowIps;//允许登录的IP地址
    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;
    private String img;//用户头像

}
