package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.TranService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.lang.model.SourceVersion;
import javax.naming.Name;
import javax.swing.filechooser.FileSystemView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Override
    public List<String> queryCustomerName(String customerName) {
        Example example = new Example(Customer.class);
        example.createCriteria().andLike("name", "%" + customerName + "%");
        List<Customer> customers = customerMapper.selectByExample(example);
        List<String> names = new ArrayList<>();
        for (Customer customer : customers) {
            names.add(customer.getName());
        }
        return names;
    }

    @Override
    public Map<String, Object> queryStages(String id, Map<String, String> stage2PossibilityMap, Integer index1, User user) {
//         创建一个Map集合用于存放交易图标和交易历史等
        Map<String, Object> map = new HashMap<>();
        //    将Map集合转换成List集合
        List<Map.Entry<String, String>> stages = new ArrayList<>(stage2PossibilityMap.entrySet());
        Tran tran = tranMapper.selectByPrimaryKey(id);
        String currentStage;
        String currentPossibility;
        if (index1 != null) {
            //    查询当前交易所处的阶段
            currentStage = stages.get(index1).getKey();
//    获取当前交易的成功的可能性
            currentPossibility = stages.get(index1).getValue();
//            更新交易信息
            tran.setStage(currentStage);
            tran.setPossibility(currentPossibility);
            tranMapper.updateByPrimaryKey(tran);
            //            添加一条交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(currentStage);
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setCreateBy(user.getName());
            tranHistory.setTranId(tran.getId());
            tranHistory.setPossibility(currentPossibility);
            tranHistoryMapper.insertSelective(tranHistory);
        } else {
            //    查询当前交易所处的阶段
            currentStage = tran.getStage();
//    获取当前交易的成功的可能性
            currentPossibility = tran.getPossibility();

        }

//     判断并得出锚点在九个阶段中的位置和第一个导致交易失败的在九个阶段的位置
        int index = 0;
        int position = 0;
        int count = 0;
//        循环结束后将确认当前交易所处的阶段即是锚点的位置，以及交易失败的点的精确位置
        for (int i = 0; i < stages.size(); i++) {
            String key = stages.get(i).getKey();
            String value = stages.get(i).getValue();
            if (currentStage.equals(key)) {

//                获取当前交易阶段所处的位置
                index = i;
            }

            if ("0".equals(value)) {
                count++;
                if (count == 1) {
//             获取当交易失败时候的位置(在整个流程中的精确位置包含自身从零计数)
                    position = i;
                }

            }
        }
        List<StageVo> stageVoList = new ArrayList<>();
        if (!"0".equals(currentPossibility)) {
//        再次遍历stages集合以便将每个图标的状态赋值给stageVo以便返回页面，
//        并显示成为响应的图标及鼠标覆盖显示阶段名字的事件。
            for (int i = 0; i < stages.size(); i++) {
                if (i < index) {
                    System.out.println("绿圈");
//                    设置stageVo为绿圈，并设置stageVo为第i个阶段对应的阶段名称
                    StageVo stageVo = new StageVo();
                    stageVo.setType("ok-circle");
                    stageVo.setColor("#90F790");
                    stageVo.setContent(stages.get(i).getKey() + ":" + stages.get(i).getValue());
                    stageVoList.add(stageVo);
                } else if (i == index) {
                    System.out.println("锚点");
                    StageVo stageVo = new StageVo();
                    stageVo.setType("map-marker");
                    stageVo.setColor("#90F790");
                    stageVo.setContent(stages.get(i).getKey() + ":" + stages.get(i).getValue());
                    stageVoList.add(stageVo);
                } else if (i > index && i < position) {
                    System.out.println("黑圈");
                    StageVo stageVo = new StageVo();
                    stageVo.setType("ok-circle");
                    stageVo.setColor("");
                    stageVo.setContent(stages.get(i).getKey() + ":" + stages.get(i).getValue());
                    stageVoList.add(stageVo);
                } else {
                    System.out.println("黑叉");
                    StageVo stageVo = new StageVo();
                    stageVo.setType("remove-circle");
                    stageVo.setColor("");
                    stageVo.setContent(stages.get(i).getKey() + ":" + stages.get(i).getValue());
                    stageVoList.add(stageVo);
                }
            }
        } else {
            //        再次遍历stages集合以便将每个图标的状态赋值给stageVo以便返回页面，
//        并显示成为响应的图标及鼠标覆盖显示阶段名字的事件。
            for (int i = 0; i < stages.size(); i++) {
                if ("0".equals(stages.get(i).getValue())) {
                    if (stages.get(i).getKey().equals(currentStage)) {
                        System.out.println("红叉");
                        StageVo stageVo = new StageVo();
                        stageVo.setType("remove-circle");
                        stageVo.setColor("#ff0009");
                        stageVo.setContent(stages.get(i).getKey() + ":" + stages.get(i).getValue());
                        stageVoList.add(stageVo);
                    } else {
                        System.out.println("黑叉");
                        StageVo stageVo = new StageVo();
                        stageVo.setType("remove-circle");
                        stageVo.setColor("");
                        stageVo.setContent(stages.get(i).getKey() + ":" + stages.get(i).getValue());
                        stageVoList.add(stageVo);
                    }
                } else {
                    System.out.println("黑圈");
                    StageVo stageVo = new StageVo();
                    stageVo.setType("ok-circle");
                    stageVo.setColor("");
                    stageVo.setContent(stages.get(i).getKey() + ":" + stages.get(i).getValue());
                    stageVoList.add(stageVo);

                }
            }
        }
        Example example = new Example(TranHistory.class);
        example.createCriteria().andEqualTo("tranId", tran.getId());

        List<TranHistory> tranHistories = tranHistoryMapper.selectByExample(example);
        User user1 = userMapper.selectByPrimaryKey(tran.getOwner());
        Activity activity = activityMapper.selectByPrimaryKey(tran.getActivityId());
        Contacts contacts = contactsMapper.selectByPrimaryKey(tran.getContactsId());
        Customer customer = customerMapper.selectByPrimaryKey(tran.getCustomerId());
        tran.setOwner(user1.getName());
        tran.setActivityId(activity.getName());
        tran.setContactsId(contacts.getFullname());
        tran.setCustomerId(customer.getName());
        map.put("stageVoList", stageVoList);
        map.put("tranHistories", tranHistories);
        map.put("tran", tran);
        return map;
    }

    @Override
    public List<Tran> list(int page, int pageSize, Tran tran) {
        Example example = new Example(Tran.class);
        Example.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(tran.getOwner())) {
            Example example1 = new Example(User.class);
            example1.createCriteria().andLike("name", "%" + tran.getOwner() + "%");
            List<User> users = userMapper.selectByExample(example1);
            List<String> ids = new ArrayList<>();
            for (User user1 : users) {
                ids.add(user1.getId());
            }
            criteria.andIn("owner", ids);
        }
        if (StrUtil.isNotEmpty(tran.getName())) {
            criteria.andLike("name", tran.getName());
        }
        if (StrUtil.isNotEmpty(tran.getCustomerId())) {
            Example example1 = new Example(Customer.class);
            example1.createCriteria().andLike("name", "%" + tran.getCustomerId() + "%");
            List<Customer> customers = customerMapper.selectByExample(example1);
            List<String> ids = new ArrayList<>();
            for (Customer customer : customers) {
                ids.add(customer.getId());
            }
            criteria.andIn("customerId", ids);
        }
        if (StrUtil.isNotEmpty(tran.getStage())) {

            criteria.andLike("stage", "%" + tran.getStage() + "%");

        }
        if (StrUtil.isNotEmpty(tran.getType())) {
            criteria.andLike("type", "%" + tran.getType() + "%");
        }
        if (StrUtil.isNotEmpty(tran.getSource())) {
            criteria.andLike("source", "%" + tran.getSource() + "%");
        }
        if (StrUtil.isNotEmpty(tran.getContactsId())) {
            Example example1 = new Example(Contacts.class);
            example1.createCriteria().andLike("fullname", "%" + tran.getContactsId() + "%");
            List<Contacts> contacts = contactsMapper.selectByExample(example1);
            List<String> ids = new ArrayList<>();
            for (Contacts contact : contacts) {
                ids.add(contact.getId());
            }
            criteria.andIn("contactsId", ids);
        }
        PageHelper.startPage(page, pageSize);
        List<Tran> tranList = tranMapper.selectByExample(example);
        for (Tran tran1 : tranList) {
            tran1.setOwner(userMapper.selectByPrimaryKey(tran1.getOwner()).getName());
            tran1.setCustomerId(customerMapper.selectByPrimaryKey(tran1.getCustomerId()).getName());
            tran1.setContactsId(contactsMapper.selectByPrimaryKey(tran1.getContactsId()).getFullname());
            tran1.setActivityId(activityMapper.selectByPrimaryKey(tran1.getActivityId()).getName());
        }
        return tranList;
    }

    @Override
    public BarChartVo tranBarChart() {
        BarChartVo barChartVo = new BarChartVo();
        List<Map<String, Long>> maps = tranMapper.queryCountByStages();
        List<String> stages = new ArrayList<>();
        List<Long> values = new ArrayList<>();
        for (Map<String, Long> map : maps) {
            stages.add(map.get("阶段") + "");
            values.add(map.get("数量"));
        }
        barChartVo.setStages(stages);
        barChartVo.setValues(values);
        return barChartVo;
    }

    @Override
    public List<PieChartVo> tranPieChart() {
        return tranMapper.queryPieChartVoList();
    }
}
