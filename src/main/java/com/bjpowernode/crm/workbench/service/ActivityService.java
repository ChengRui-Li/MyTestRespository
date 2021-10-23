package com.bjpowernode.crm.workbench.service;

import cn.hutool.poi.excel.ExcelWriter;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.bean.Clue;

import java.util.List;

public interface ActivityService {

    List<Activity> list(int page,int pageSize,Activity activity);

    Activity queryById(String id);

    List<User> queryUsers();

    ResultVo saveOrUpdate(Activity activity, User user);

    void deleteBench(String ids);

    Activity toDetail(String id);

    void addActivityRemark(ActivityRemark activityRemark, User user);

    void updateActivityRemark(ActivityRemark activityRemark, User user);

    void deleteActivityRemark(String id);

    ExcelWriter exportExcel();
}
