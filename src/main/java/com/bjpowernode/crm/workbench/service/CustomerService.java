package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Customer;

import java.util.List;

public interface CustomerService {
    ResultVo saveOrUpdate(Customer customer, User user);

    List<Customer> list(int page, int pageSize, Customer customer);

    List<User> queryUsers();

    Customer queryById(String id);

    void deleteCustomer(String ids);

    Customer detail(String id);

    void addCustomerRemark(String id, String noteContent,User user);

    void updateCustomerRemark(String id, String noteContent,User user);

    void deleteCustomerRemark(String id);
}
