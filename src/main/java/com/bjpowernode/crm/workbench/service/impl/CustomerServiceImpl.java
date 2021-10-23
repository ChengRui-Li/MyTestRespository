package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.exception.CrmExceptionEnum;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Contacts;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.bean.CustomerRemark;
import com.bjpowernode.crm.workbench.mapper.ContactsMapper;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.CustomerRemarkMapper;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Override

    public ResultVo saveOrUpdate(Customer customer, User user) {
        ResultVo resultVo = new ResultVo();
        if (customer.getId() == null) {
//            执行客户表的添加操作
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(user.getName());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            int count = customerMapper.insertSelective(customer);
            resultVo.setMessage("添加客户成功");
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_CUSTOMER_INSERT);
            }
        } else {
//            执行编辑客户信息的操作;
            customer.setEditBy(user.getName());
            customer.setEditTime(DateTimeUtil.getSysTime());
            resultVo.setMessage("修改客户信息成功");
            int count = customerMapper.updateByPrimaryKey(customer);
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_CUSTOMER_UPDATE);
            }
        }
        resultVo.setOk(true);

        return resultVo;
    }

    @Override
    public List<Customer> list(int page, int pageSize, Customer customer) {
//        根据customer中的条件条件查询并返回符合条件的集合
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
//        判断查询的客户名字搜索条件是否为空
        if (StrUtil.isNotEmpty(customer.getName())) {
            criteria.andLike("name", "%" + customer.getName() + "%");

        }
//        判断所有者是否为空
        if (StrUtil.isNotEmpty(customer.getOwner())) {
//          首先模糊查询所有者表
            Example userExample = new Example(User.class);
            Example.Criteria criteriaUser = userExample.createCriteria();
            criteriaUser.andLike("name", "%" + customer.getOwner() + "%");
//            将所有查询出的用户id放入一个字符串集合中
            List<User> users = userMapper.selectByExample(userExample);
            List<String> ids = new ArrayList<>();
            for (User user : users) {
                ids.add(user.getId());
            }

            criteria.andIn("owner", ids);
        }
//        判断公司座机电话搜索条件是否为空
        if (StrUtil.isNotEmpty(customer.getPhone())) {
            criteria.andLike("phone", "%" + customer.getPhone() + "%");

        }
//        判断公司网站搜索条件是否为空
        if (StrUtil.isNotEmpty(customer.getWebsite())) {
            criteria.andLike("website", "%" + customer.getWebsite() + "%");
        }
//       使用分页插件进行分页
        PageHelper.startPage(page, pageSize);
//        查询所有符合条件的客户
        List<Customer> customers = customerMapper.selectByExample(example);
        for (Customer customer1 : customers) {
            customer1.setOwner(userMapper.selectByPrimaryKey(customer1.getOwner()).getName());
        }


        return customers;
    }

    @Override
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    @Override
    public Customer queryById(String id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteCustomer(String ids) {
//  将字符串转换为字符串集合
        List<String> list = (List<String>) Arrays.asList(ids.split(","));
        Example example = new Example(Customer.class);
        example.createCriteria().andIn("id", list);
        int count = customerMapper.deleteByExample(example);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CUSTOMER_DELETE);
        }
    }

    @Override
    public Customer detail(String id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setCustomerId(id);
        List<CustomerRemark> customerRemarks = customerRemarkMapper.select(customerRemark);
        customer.setCustomerRemarks(customerRemarks);
        for (CustomerRemark remark : customerRemarks) {
//            将图片设置到remark中
            User user = new User();
            user.setName(remark.getCreateBy());
            List<User> users = userMapper.select(user);
            user = users.get(0);
            ArrayList<String> ownerImgs = new ArrayList<>();
            ownerImgs.add(user.getImg());
            remark.setOwnerImgs(ownerImgs);
//            将联系人查询出来设置到remark中
            Example example = new Example(Contacts.class);
            example.createCriteria().andEqualTo("customerId", id).andEqualTo("owner", customer.getOwner());
            List<Contacts> contacts = contactsMapper.selectByExample(example);
            if (contacts.size() != 0) {
                Contacts contact = contacts.get(0);
                ArrayList<String> fullNames = new ArrayList<>();
                fullNames.add(contact.getFullname());
                remark.setFullName(fullNames);
            } else {
                ArrayList<String> fullNames = new ArrayList<>();
                fullNames.add("");
                remark.setFullName(fullNames);
            }
        }
        customer.setOwner(userMapper.selectByPrimaryKey(customer.getOwner()).getName());
        return customer;
    }

    @Override
    public void addCustomerRemark(String id, String noteContent,User user) {
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setId(UUIDUtil.getUUID());
        customerRemark.setNoteContent(noteContent);
        customerRemark.setCreateBy(user.getName());
        customerRemark.setCreateTime(DateTimeUtil.getSysTime());
        customerRemark.setCustomerId(id);
        int count = customerRemarkMapper.insertSelective(customerRemark);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CUSTOMER_ADD_REMARK);
        }

    }

    @Override
    public void updateCustomerRemark(String id, String noteContent,User user) {
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setId(id);
        customerRemark.setNoteContent(noteContent);
        customerRemark.setEditBy(user.getName());
        customerRemark.setEditTime(DateTimeUtil.getSysTime());
        customerRemark.setEditFlag("1");
        int count = customerRemarkMapper.updateByPrimaryKeySelective(customerRemark);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CUSTOMER_UPDATE_REMARK);
        }
    }

    @Override
    public void deleteCustomerRemark(String id) {
        int count = customerRemarkMapper.deleteByPrimaryKey(id);
        if (count == 0) {

            throw new CrmException(CrmExceptionEnum.USER_CUSTOMER_DELETE_REMARK);
        }
    }
}
