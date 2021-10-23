package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.exception.CrmExceptionEnum;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public List<Clue> list(int page, int pageSize) {
        List<Clue> clues = clueMapper.selectAll();
        for (Clue clue : clues) {
            String owner = clue.getOwner();
            User user = userMapper.selectByPrimaryKey(owner);
            clue.setOwner(user.getName());
        }

        return clues;
    }

    @Override
    public void addClue(Clue clue, HttpSession session) {

        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(CommonUtil.getUserBySession(session).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        int count = clueMapper.insertSelective(clue);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CLUE_UPDATE);
        }

    }

    @Override
    public List<Activity> clueActivityList(String id) {
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
        List<Activity> activityList = new ArrayList<>();
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            Activity activity = activityMapper.selectByPrimaryKey(activityRelation.getActivityId());
            activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());
            activityList.add(activity);
        }
        return activityList;
    }

    @Override
    public List<Activity> queryClueActivityList(String id, String name) {
//        通过线索id查询关联表得到所有的关联表对象
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
//        将该线索id对应的市场活动主键加入到一个集合以便使用
        List<String> ids = new ArrayList<>();
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            ids.add(activityRelation.getActivityId());
        }
        Example example = new Example(Activity.class);
        if (ids.size() != 0) {
            example.createCriteria().andNotIn("id", ids);
        }
        example.createCriteria().andLike("name", "%" + name + "%");
        List<Activity> activityList = activityMapper.selectByExample(example);
        for (Activity activity : activityList) {
            activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());
        }
        return activityList;
    }

    @Override
    public ResultVo bind(String ids, String id) {
        ResultVo resultVo = new ResultVo();
//        分割字符串并放入字符串数组中
        List<Activity> activityList = new ArrayList<>();
        String[] aids = ids.split(",");
        for (String aid : aids) {
            Activity activity = activityMapper.selectByPrimaryKey(aid);
            activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());
            activityList.add(activity);
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(id);
            clueActivityRelation.setActivityId(aid);
            int count = clueActivityRelationMapper.insertSelective(clueActivityRelation);
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_CLUE_BIND);
            }
        }
        resultVo.setOk(true);
        resultVo.setMessage("绑定市场活动成功");
        resultVo.setT(activityList);
        return resultVo;
    }

    @Override
    public void unbind(ClueActivityRelation clueActivityRelation) {
        clueActivityRelationMapper.delete(clueActivityRelation);
    }

    @Override
    public Clue clueList(String id) {
        Clue clue = clueMapper.selectByPrimaryKey(id);
        clue.setOwner(userMapper.selectByPrimaryKey(clue.getOwner()).getName());
        return clue;
    }

    @Override
    public void convert(String id,User user, String isCreateTransaction, Tran tran) {
//        定义一个整数作为默认为0用于异常
        int count = 0;
//        1、根据线索的主键查询线索的信息
        Clue clue = clueMapper.selectByPrimaryKey(id);
//        2、先将客户信息取出来保存到客户表中，当该客户不存在的时候新建客户
//           按公司名称精准查询
        Customer customer = new Customer();
        customer.setName(clue.getCompany());
        List<Customer> customers = customerMapper.select(customer);

        if (customers.size() == 0) {
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(user.getName());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            count = customerMapper.insertSelective(customer);
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
            }
        }
//        3、将联系人信息取出来保存到联系人表中
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(clue.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(user.getName());
        contacts.setCreateTime(DateTimeUtil.getSysTime());
        contacts.setAddress(clue.getAddress());
        count = contactsMapper.insertSelective(contacts);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
        }
//     4、线索中的备注信息取出来保存到客户备注及联系人备注中
//        保存联系人备注信息
        ContactsRemark contactsRemark = new ContactsRemark();
        contactsRemark.setId(UUIDUtil.getUUID());
        contactsRemark.setCreateBy(user.getName());
        contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
        contactsRemark.setContactsId(contacts.getId());
        count = contactsRemarkMapper.insertSelective(contactsRemark);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
        }
//        保存客户备注信息
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setId(UUIDUtil.getUUID());
        customerRemark.setCreateBy(user.getName());
        customerRemark.setCreateTime(DateTimeUtil.getSysTime());
        customerRemark.setCustomerId(customer.getId());
        count = customerRemarkMapper.insertSelective(customerRemark);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
        }
//        5、将线索和市场活动的关系转换到联系人和市场活动的关系中
//        5.1、查询线索关联的市场活动
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(activityRelation.getActivityId());
            count = contactsActivityRelationMapper.insert(contactsActivityRelation);
            if (count ==0) {
                throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
            }
        }
//       6、 如果转换过程加入了新的交易则需要，进行创建交易的操作
        if ("1".equals(isCreateTransaction)) {
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(clue.getOwner());;
            tran.setCreateBy(user.getName());
            tran.setCreateTime(DateTimeUtil.getSysTime());
            count = tranMapper.insertSelective(tran);
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
            }
//      7.1、创建交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setCreateBy(user.getName());
            tranHistory.setTranId(tran.getId());
            count = tranHistoryMapper.insertSelective(tranHistory);
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
            }
//            7.2、创建交易备注
            TranRemark tranRemark = new TranRemark();
            tranRemark.setId(UUIDUtil.getUUID());
            tranRemark.setCreateBy(user.getName());
            tranRemark.setCreateTime(DateTimeUtil.getSysTime());
            tranRemark.setTranId(tran.getId());
            count = tranRemarkMapper.insertSelective(tranRemark);
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
            }
        }
        //          9、删除线索与市场活动的关系
        Example example = new Example(ClueActivityRelation.class);
        example.createCriteria().andEqualTo("clueId", id);
        count = clueActivityRelationMapper.deleteByExample(example);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
        }
//         10、删除线索
        count = clueMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_CLUE_CONVERT);
        }


    }

    @Override
    public List<Activity> queryActivity(String id, String name) {
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
        List<String> ids = new ArrayList<>();
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            ids.add(activityRelation.getActivityId());
        }
        Example example = new Example(Activity.class);
        example.createCriteria().andIn("id", ids).andLike("name", "%" + name + "%");
        List<Activity> activities = activityMapper.selectByExample(example);
        for (Activity activity : activities) {
            activity.setOwner(activity.getName());
        }
        return activities;
    }
}
