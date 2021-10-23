package com.bjpowernode.crm.workbench.bean;

import lombok.Data;

import java.util.List;

@Data
public class BarChartVo {
    private List<String> stages;
    private List<Long> values;
}
