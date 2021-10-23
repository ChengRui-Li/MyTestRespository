package com.bjpowernode.crm.workbench.controller;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @RequestMapping("workbench/activity/list")
    public PageInfo list(int page,int pageSize,Activity activity){
        List<Activity> list = activityService.list(page,pageSize,activity);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
    @RequestMapping("/workbench/activity/queryById")
    public Activity queryById(String id){
        return activityService.queryById(id);}

    @RequestMapping("/workbench/activity/queryUsers")
    public List<User> queryUsers(){
        return activityService.queryUsers();
    }
    @RequestMapping("/workbench/activity/saveOrUpdate")
    public ResultVo saveOrUpdate(Activity activity, HttpSession session){
        ResultVo resultVo = new ResultVo();
        try {
//            获取登录用户
            User user = CommonUtil.getUserBySession(session);
            resultVo = activityService.saveOrUpdate(activity, user);
        } catch (CrmException e) {
        resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/activity/deleteBench")
    public ResultVo deleteBench(String ids){
        ResultVo resultVo = new ResultVo();
        try {
            activityService.deleteBench(ids);
            resultVo.setOk(true);
            resultVo.setMessage("删除市场活动成功");

        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
//    跳转到市场活动详情的方法
    @RequestMapping("/workbench/activity/detail")
    public Activity detail(String id){
        return activityService.toDetail(id);
    }
//    添加备注
    @RequestMapping("/workbench/activity/addActivityRemark")
    public ResultVo addActivityRemarks(ActivityRemark activityRemark,HttpSession session){
        ResultVo resultVo = new ResultVo();
        try {
//            获取已登录用户
            User user = CommonUtil.getUserBySession(session);
            activityService.addActivityRemark(activityRemark,user);
            resultVo.setOk(true);
            resultVo.setMessage("添加市场活动备注成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/activity/updateActivityRemark")
    public ResultVo updateActivityRemark(ActivityRemark activityRemark,HttpSession session){
        ResultVo resultVo = new ResultVo();
        try {
            User user = CommonUtil.getUserBySession(session);
            activityService.updateActivityRemark(activityRemark,user);
            resultVo.setOk(true);
            resultVo.setMessage("更新市场活动备注成功");
        } catch (CrmException e) {
           resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
//    删除市场活动备注
    @RequestMapping("/workbench/activity/deleteActivityRemark")
   public ResultVo deleteActivityRemark(String id){
        ResultVo resultVo = new ResultVo();

        try {
            activityService.deleteActivityRemark(id);
            resultVo.setOk(true);
            resultVo.setMessage("删除市场活动成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }


}
