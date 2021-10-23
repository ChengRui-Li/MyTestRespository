package com.bjpowernode.test;

import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestCrm {

    BeanFactory beanFactory = new ClassPathXmlApplicationContext("Spring/applicationContext.xml");
    UserMapper userMapper = (UserMapper) beanFactory.getBean("userMapper");

    @Test
    public void testAdd(){


        User user = new User();
        user.setId("66");
        user.setLoginAct("wangwu");
        user.setName("王五");
//        该方法只插入有值的数据
        userMapper.insertSelective(user);

    }
    @Test
    public void testDelete(){
//       根据id删除数据库中的数据
//        userMapper.deleteByPrimaryKey("66");

////        根据信息删除数据库中的数据（只能删除等值信息）
//        User user = new User();
//        user.setName("李四");
//        userMapper.delete(user);

//        根据条件删除数据库中的信息满足均可不一定是等值
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
//        两个百分号中间拼的就是条件模糊匹配，即是loginAct列中包含o的全部删除
//        criteria.andLike("loginAct","%o%");
//        userMapper.deleteByExample(example);

//       依然使用Example进行批量删除
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("xiaohong");
//        第一个属性是数据库中的列名第二个是放列名的集合
        criteria.andIn("loginAct", list);
        userMapper.deleteByExample(example);

    }

    @Test
    public void testUpdate(){
//      仅仅更新非空字段(指的是实体类中的非空字段该方法必须给ID)【该方法不会更新实体类中的空值到数据库】
        User user = new User();
        user.setLoginAct("zhangsan");
//        user.setId("b7658115358d4f3ea22f31604d721a18");
//        user.setName("zhangsan");
//        userMapper.updateByPrimaryKeySelective(user);

//        根据条件更新（会更新实体类中的空值到数据库）
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "管理员");
        userMapper.updateByExample(user, example);

    }

    @Test
    public void testSelect(){
//        将查询结果封装为User对象并放入list集合中
//        List<User> list = userMapper.select(new User());
//        System.out.println(list);

//      查询所有数据
//        List<User> listAll = userMapper.selectAll();
//        System.out.println(listAll);
//        根据条件查询
//        userMapper.selectByExample();

//        根据ID查询
//        User user = new User();
//        user.setId("b7658115358d4f3ea22f31604d721a18");
//        User userselect = userMapper.selectByPrimaryKey(user);
//        System.out.println(userselect);
//       排序查询(desc降序，asc为升序)
        Example example = new Example(User.class);
        example.setOrderByClause("lockState desc");
        List<User> list = userMapper.selectByExample(example);
        for (User user : list) {
            System.out.println(user.getLockState());
        }
    }

    @Test
    public void getUUID(){

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("uuid = " + uuid);

    }
    @Test
    public void getMD5Util(){
        String admin = MD5Util.getMD5("admin");
        System.out.println("admin = " + admin);


    }
}
