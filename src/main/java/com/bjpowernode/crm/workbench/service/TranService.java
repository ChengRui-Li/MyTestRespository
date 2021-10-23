package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.BarChartVo;
import com.bjpowernode.crm.workbench.bean.PieChartVo;
import com.bjpowernode.crm.workbench.bean.StageVo;
import com.bjpowernode.crm.workbench.bean.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<String> queryCustomerName(String customerName);

    Map<String,Object> queryStages(String id, Map<String,String> stage2PossibilityMap, Integer index, User user);

    List<Tran> list(int page, int pageSize, Tran tran);

    BarChartVo tranBarChart();

    List<PieChartVo> tranPieChart();
}
