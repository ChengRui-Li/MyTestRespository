package com.bjpowernode.crm.workbench.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Data
@Table(name = "tbl_customer_remark")
@NameStyle(Style.normal)
public class CustomerRemark {
    @Id
    private String id;
    private String noteContent;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String editFlag;
    private String customerId;
    //    客户联系人信息
    private List<String> fullName;
    //    所有者图片信息
    private List<String> ownerImgs;
}
