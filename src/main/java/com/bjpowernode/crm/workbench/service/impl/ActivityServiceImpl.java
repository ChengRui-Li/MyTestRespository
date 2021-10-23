package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.exception.CrmExceptionEnum;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;


    @Override
    public List<Activity> list(int page,int pageSize,Activity activity) {
//        创建example对象
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        String name = activity.getName();
        String owner = activity.getOwner();
        String startDate = activity.getStartDate();
        String endDate = activity.getEndDate();
//        判断市场活动名称是否为空
        if (StrUtil.isNotEmpty(name)) {
//        输入的市场活动名称不为空加入查询条件
            criteria.andLike("name", "%" + name + "%");
        }
//        判断所有者输入框是否为空
        if (StrUtil.isNotEmpty(owner)) {
//        不为空加入查询条件
//        首先根据输入的内容模糊查询出符合条件的用户
            Example userExample = new Example(User.class);
            userExample.createCriteria().andLike("name","%"+owner+"%" );
            List<User> userList = userMapper.selectByExample(userExample);
            List<String> idList = new ArrayList<>();
            for (User user : userList) {
                idList.add(user.getId());
            }
            criteria.andIn("owner", idList);
        }
//          判断开始日期是否为空
        if (StrUtil.isNotEmpty(startDate)) {
//         不为空加入查询条件
//         大于等于开始日期或者在该日期之后
            criteria.andGreaterThanOrEqualTo("startDate", startDate);
        }
//          判断结束日期是否为空
        if (StrUtil.isNotEmpty(endDate)) {
//         不为空加入查询条件
//         小于等于开始日期或者在该日期之前
            criteria.andLessThanOrEqualTo("endDate", endDate);
        }
        //        page:当前页码，pageSize:每页记录数
        PageHelper.startPage(page, pageSize);
        List<Activity> activities = activityMapper.selectByExample(example);
        for (Activity activity1 : activities) {
//            获取列表中用户的外键
            String owner1 = activity1.getOwner();
//            查询用户
            User user = userMapper.selectByPrimaryKey(owner1);
            activity1.setOwner(user.getName());
        }
        return activities;
    }

    @Override
    public Activity queryById(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    @Override
    public ResultVo saveOrUpdate(Activity activity, User user) {
        ResultVo resultVo = new ResultVo();
        if (activity.getId() == null) {
//        执行添加操作
            activity.setId(UUIDUtil.getUUID());
            activity.setCreateBy(user.getName());
            activity.setCreateTime(DateTimeUtil.getSysTime());
            int count = activityMapper.insertSelective(activity);
            resultVo.setMessage("添加市场活动成功");
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_ACTIVITY_ADD);
            }
        } else {
//            执行修改操作
//            记录修改的用户
            activity.setEditBy(user.getName());
            activity.setEditTime(DateTimeUtil.getSysTime());
            int count = activityMapper.updateByPrimaryKeySelective(activity);
            resultVo.setMessage("更新市场活动成功");
            if (count == 0) {
                throw new CrmException(CrmExceptionEnum.USER_ACTIVITY_UPDATE);
            }
        }
        resultVo.setOk(true);
        return resultVo;
    }

    @Override
    public void deleteBench(String ids) {
//      使用tkMapper删除
//        将字符串转换为list集合
        List<String> list = Arrays.asList(ids.split(","));
//       delete from tableName where id in(id1,id2)
        Example example = new Example(Activity.class);
        example.createCriteria().andIn("id", list);
        int count = activityMapper.deleteByExample(example);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_ACTIVITY_DELETEBENCH);
        }


    }

    @Override
    public Activity toDetail(String id) {
//        查询市场活动本身的数据
        Activity activity = activityMapper.selectByPrimaryKey(id);
//        查询备注的数据
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setActivityId(activity.getId());
        List<ActivityRemark> activityRemarks = activityRemarkMapper.select(activityRemark);
        activity.setActivityRemarks(activityRemarks);
//
        activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());
        return activity;
    }

    @Override
    public void addActivityRemark(ActivityRemark activityRemark, User user) {
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateBy(user.getName());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setImg(user.getImg());
        activityRemark.setOwner(user.getId());
        activityRemark.setEditFlag("0");
        int count = activityRemarkMapper.insertSelective(activityRemark);
        if (count==0) {
            throw new CrmException(CrmExceptionEnum.USER_ACTIVITY_REMARK);
        }

    }

    @Override
    public void updateActivityRemark(ActivityRemark activityRemark, User user) {
        activityRemark.setEditBy(user.getName());
        activityRemark.setEditFlag("1");
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        int count = activityRemarkMapper.updateByPrimaryKeySelective(activityRemark);
        if (count==0) {
            throw new CrmException(CrmExceptionEnum.USER_ACTIVITY_UPDATEREMARK);
        }

    }

    @Override
    public void deleteActivityRemark(String id) {
        int count = activityRemarkMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new CrmException(CrmExceptionEnum.USER_ACTIVITY_DELETEREMARK);
        }
    }

    @Override
    public ExcelWriter exportExcel() {
        Example example = new Example(Activity.class);
//        获取类的属性的数量
        Map<String, EntityColumn> propertyMap = example.getPropertyMap();
//
        List<Activity> activities = activityMapper.selectAll();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 定义单元格背景色
        StyleSet style = writer.getStyleSet();
        // 第二个参数表示是否也设置头部单元格背景
        style.setBackgroundColor(IndexedColors.WHITE, false);
        writer.merge(propertyMap.size() - 1, "市场活动统计数据");
        // 通过工具类创建writer，默认创建xls格式
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(activities, true);
        return writer;
    }


}
