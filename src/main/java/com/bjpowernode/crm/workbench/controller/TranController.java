package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.util.CommonUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.BarChartVo;
import com.bjpowernode.crm.workbench.bean.PieChartVo;
import com.bjpowernode.crm.workbench.bean.StageVo;
import com.bjpowernode.crm.workbench.bean.Tran;
import com.bjpowernode.crm.workbench.service.TranService;
import com.github.pagehelper.PageInfo;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TranController {
    @Autowired
    private TranService tranService;


    @RequestMapping("/workbench/tran/list")
    public PageInfo list(int page, int pageSize, Tran tran){
        List<Tran> tranList = tranService.list(page, pageSize, tran);
        PageInfo<Tran> pageInfo = new PageInfo<>(tranList);
        return pageInfo;
    }
    @RequestMapping("/workbench/transaction/queryCustomerName")
    public List<String> queryCustomerName(String customerName){
            List<String> names = tranService.queryCustomerName(customerName);
        return names;
    }
    @RequestMapping("/workbench/transaction/stage2Possibility")
    public String stage2Possibility(String stage, HttpSession session){

        Map<String, String> stage2Possibility = (Map<String, String>) session.getServletContext().getAttribute("stage2PossibilityMap");

        return stage2Possibility.get(stage);
    }
    @RequestMapping("/workbench/transaction/queryStages")
    public Map<String,Object> queryStages(String id, HttpSession session,Integer index){

       Map<String,String> stage2PossibilityMap = ( Map<String,String>)session.getServletContext().getAttribute("stage2PossibilityMap");
        User user = CommonUtil.getUserBySession(session);
        Map<String, Object> map = tranService.queryStages(id,stage2PossibilityMap,index,user);
        return map;
    }
    @RequestMapping("/workbench/tran/chart/tranBarChart")
    public BarChartVo tranBarChart(){
        return tranService.tranBarChart();
    }
    @RequestMapping("/workbench/tran/chart/tranPieChart")
    public List<PieChartVo> tranPieChart(){
        return tranService.tranPieChart();
    }




}
