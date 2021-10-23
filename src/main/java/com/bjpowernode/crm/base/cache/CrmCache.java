package com.bjpowernode.crm.base.cache;

import com.bjpowernode.crm.base.bean.DicType;
import com.bjpowernode.crm.base.bean.DicValue;
import com.bjpowernode.crm.base.mapper.DicTypeMapper;
import com.bjpowernode.crm.base.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.*;

@Component
public class CrmCache {
    @Autowired
    private DicTypeMapper dicTypeMapper;
    @Autowired
    private DicValueMapper dicValueMapper;
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserMapper userMapper;


//   该注解功能是服务器启动时自动执行一次该方法
    @PostConstruct
    public void init(){
        List<User> users = userMapper.selectAll();

        servletContext.setAttribute("users", users);
//        查询数据字典的类型(存储于数据类型表中)
        List<DicType> dicTypes = dicTypeMapper.selectAll();
        HashMap<String, List<DicValue>> map = new HashMap<>();
        for (DicType dicType : dicTypes) {
//            获取数据类型的外键
            String code = dicType.getCode();
//            通过数据类型的外键查询数据值表的全部数据，使用Example
            Example example = new Example(DicValue.class);
            example.setOrderByClause("orderNo");
            example.createCriteria().andEqualTo("typeCode", code);
            List<DicValue> dicValues = dicValueMapper.selectByExample(example);
            map.put(code, dicValues);
        }
        servletContext.setAttribute("map", map);
//        缓冲阶段名称
        Map<String, String> stage2PossibilityMap = new TreeMap<>();
//        读取properties文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Mybatis.Stage2Possibility");
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = resourceBundle.getString(key);
            stage2PossibilityMap.put(key, value);
        }
//        把阶段和可能性进行数据缓冲
        servletContext.setAttribute("stage2PossibilityMap", stage2PossibilityMap);
    }
}
