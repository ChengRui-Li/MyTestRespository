package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/workbench/customer/saveOrUpdate")
    @ResponseBody
    public ResultVo saveOrUpdate(Customer customer, HttpSession session){
        User user = CommonUtil.getUserBySession(session);
        ResultVo resultVo = new ResultVo();
        try {
            resultVo = customerService.saveOrUpdate(customer, user);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }

        return resultVo;
    }

    @RequestMapping("/workbench/customer/list")
    @ResponseBody
    public PageInfo list(int page,int pageSize,Customer customer){
       List<Customer> list =  customerService.list(page,pageSize,customer);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
    @RequestMapping("/workbench/customer/queryUsers")
    @ResponseBody
    public List<User> queryUsers(){
        return customerService.queryUsers();
    }
    @RequestMapping("/workbench/customer/queryById")
    @ResponseBody
    public Customer queryById(String id){
        return customerService.queryById(id);
    }
    @RequestMapping("/workbench/customer/deleteCustomer")
    @ResponseBody
    public ResultVo deleteCustomer(String ids){
        ResultVo resultVo = new ResultVo();
        try {
            customerService.deleteCustomer(ids);
            resultVo.setOk(true);
            resultVo.setMessage("删除客户信息成功");
        } catch (CrmException e) {
           resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/customer/detail")
    @ResponseBody
    public Customer detail(String id){

        return customerService.detail(id);
    }
    @RequestMapping("/workbench/customer/addCustomerRemark")
    @ResponseBody
    public ResultVo addCustomerRemark(String id,String noteContent,HttpSession session){
        ResultVo resultVo = new ResultVo();
        User user = CommonUtil.getUserBySession(session);
        try {
            customerService.addCustomerRemark(id,noteContent,user);
            resultVo.setOk(true);
            resultVo.setMessage("添加客户备注信息成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/customer/updateCustomerRemark")
    @ResponseBody
    public ResultVo updateCustomerRemark(String id,String noteContent,HttpSession session){
        ResultVo resultVo = new ResultVo();
        User user = CommonUtil.getUserBySession(session);
        try {
            customerService.updateCustomerRemark(id,noteContent,user);
            resultVo.setOk(true);
            resultVo.setMessage("更改客户备注信息成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }

        return resultVo;
    }

    @RequestMapping("/workbench/customer/deleteCustomerRemark")
    @ResponseBody
    public ResultVo deleteCustomerRemark(String id) {
        ResultVo resultVo = new ResultVo();
        try {
            customerService.deleteCustomerRemark(id);
            resultVo.setOk(true);
            resultVo.setMessage("删除客户备注信息成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }



}
