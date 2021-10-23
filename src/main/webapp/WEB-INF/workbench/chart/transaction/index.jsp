<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
    <title></title>
    <script src="/crm/jquery/ECharts/echarts.min.js"></script>
    <script src="/crm/jquery/jquery-1.11.1-min.js"></script>


</head>
<body>
<!-- 为 ECharts 准备一个定义了宽高的 DOM -->
<div id="main" style="width: 900px; transform: scale(0.9, 0.9);float:right;height:500px;margin-right: 550px;"></div>
<div id="main2" style="width: 900px;height:500px; transform: scale(0.8, 0.8); float: right;margin-right:-80px; margin-top: -460px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main2'));
    $.get("${pageContext.request.contextPath}/workbench/tran/chart/tranBarChart", {}, function (data) {
        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '交易阶段统计柱形图'
            },
            tooltip: {},
            legend: {
                data: ['阶段']
            },
            xAxis: {
                data: data.stages
            },
            yAxis: {},
            series: [
                {
                    name: '数量',
                    type: 'bar',
                    data: data.values
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }, 'json');
    $.get("${pageContext.request.contextPath}/workbench/tran/chart/tranPieChart", {}, function (data) {
        // 指定图表的配置项和数据
        option = {
            legend: {
                top: 'bottom'
            },
            toolbox: {
                show: true,
                feature: {
                    mark: { show: true },
                    dataView: { show: true, readOnly: false },
                    restore: { show: true },
                    saveAsImage: { show: true }
                }
            },
            series: [
                {
                    name: 'Nightingale Chart',
                    type: 'pie',
                    radius: [50, 250],
                    center: ['50%', '50%'],
                    roseType: 'area',
                    itemStyle: {
                        borderRadius: 8
                    },
                    data:data
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option);
    }, 'json');



</script>
</body>
</html>
