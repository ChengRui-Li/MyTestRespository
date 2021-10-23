package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;
import com.bjpowernode.crm.workbench.bean.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ClueController {
    @Autowired
    private ClueService clueService;

    @RequestMapping("/workbench/clue/list")
    @ResponseBody
    public PageInfo clueList(int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        List<Clue> list = clueService.list(page,pageSize);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
    @RequestMapping("/workbench/clue/addClue")
    @ResponseBody
    public ResultVo addClue(Clue clue,HttpSession session){

        ResultVo resultVo = new ResultVo();

        try {
            clueService.addClue(clue,session);
            resultVo.setOk(true);
            resultVo.setMessage("添加线索成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/clue/clueActivityList")
    @ResponseBody
    public List<Activity> cluActivityList(String id){
    List<Activity> list = clueService.clueActivityList(id);
    return list;
}
    @RequestMapping("/workbench/clue/queryClueActivityList")
    @ResponseBody
    public List<Activity> queryClueActivityList(String id,String name){
        List<Activity> activityList = clueService.queryClueActivityList(id, name);
        return activityList;
    }
    @RequestMapping("/workbench/clue/bind")
    @ResponseBody
    public ResultVo bind(String ids,String id){
        ResultVo resultVo = new ResultVo();
        try {
            resultVo = clueService.bind(ids, id);
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/clue/unbind")
    @ResponseBody
    public ResultVo unbind(ClueActivityRelation clueActivityRelation){
        ResultVo resultVo = new ResultVo();
        try {
            clueService.unbind(clueActivityRelation);
            resultVo.setOk(true);
            resultVo.setMessage("解除市场活动绑定成功");
        } catch (CrmException e) {
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/clue/queryById")
    @ResponseBody
    public Clue queryById(String id){
       Clue clue = clueService.clueList(id);
        return clue;
    }
    @RequestMapping("/workbench/clue/convert")
    @ResponseBody
    public ResultVo convert(String id, String isCreateTransaction, HttpSession session, Tran tran){
        ResultVo resultVo = new ResultVo();
        try {
            User user = CommonUtil.getUserBySession(session);
            clueService.convert(id,user,isCreateTransaction,tran);
            resultVo.setOk(true);
            resultVo.setMessage("线索转换成功");
        } catch (CrmException e) {
          resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
    @RequestMapping("/workbench/clue/queryActivity")
    @ResponseBody
    public List<Activity> queryActivity(String id,String name){
        List<Activity> activities = clueService.queryActivity(id, name);
        return activities;
    }


}
