<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="${pageContext.request.contextPath}/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
<script type="text/javascript">
	$(function(){
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
	});
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						  <div class="form-group has-feedback">
						    <input type="text" id="searchInput" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="searchResult">

<%--							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
                <div class="modal-footer" id="searchBtn">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" id="bind"  onclick="bind()" class="btn btn-primary" data-dismiss="modal">确定</button>
                </div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;" >

	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	

		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control time" id="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
		    	<option>请选择</option>
                <c:forEach items="${map['stage']}" var="stage">
                    <option value="${stage.value}">${stage.text}</option>
                </c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
              <input type="hidden" id="activityId"/>
		    <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#searchActivityModal" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text"  class="form-control" id="activity" placeholder="点击上面搜索" readonly>
		  </div>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">

	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" onclick="convert()" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
<script>
    //日历插件
    (function($){
        $.fn.datetimepicker.dates['zh-CN'] = {
            days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
            daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
            daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
            months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            today: "今天",
            suffix: [],
            meridiem: ["上午", "下午"]
        };
    }(jQuery));
    /*日历插件*/
    $(".time").datetimepicker({
        language:  "zh-CN",
        format: "yyyy-mm-dd",//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),//初始化当前日期
        autoclose: true,//选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn : true,
        pickerPosition: "bottom-left"
    });



    //先发一个请求查询一下转换页面显示的数据
    $.get("${pageContext.request.contextPath}/workbench/clue/queryById", {
        'id': '${id}'
    }, function (data) {
        var clue = data;
        $('#title').html("<h4>转换线索 <small>"+clue.fullname+"-"+clue.company+"</small></h4>")
        $('#create-customer').text("新建客户:"+clue.company+"");
        $('#create-contact').text("新建联系人:"+clue.fullname+"");
        $('#owner').html("记录的所有者:<br> <b>"+clue.owner+"</b>");
    },'json');

    function convert(){$.post("${pageContext.request.contextPath}/workbench/clue/convert",{
        'id':'${id}',//线索主键
        'isCreateTransaction': $('#isCreateTransaction').val(),//是否勾选创建交易
    //    交易数据
        'money': $('#amountOfMoney').val(),
        'name': $('#tradeName').val(),
        'expectedDate': $('#expectedClosingDate').val(),
        'stage': $('#stage').val(),
        'activityId': $('#activityId').val()
    },function (data) {
        if (data.ok) {
            layer.alert(data.message, {icon: 6});
        }
        //data:resultVo
    },'json');}

    $('#isCreateTransaction').change(function () {
        if ($(this).prop('checked')) {
            $(this).val("1")
        }else{
            $(this).val("0")
        }
    });
   // 给市场活动搜索框添加回车事件
    $("#searchInput").keypress(function (event){
        if (event.keyCode == 13) {
        //    异步查询已经绑定线索的市场活动
            $.get("${pageContext.request.contextPath}/workbench/clue/queryActivity",
                {
                    'id'  : '${id}',
                    'name':  $(this).val()
                }, function (data) {
                    var activitys = data;
                    for (var i = 0;i<activitys.length;i++) {
                        var activity = activitys[i];
                        $('#searchResult').append("<tr>\n" +
                        "\t\t\t\t\t\t\t\t<td><input class='son' value="+activity.id+" type=\"radio\" name=\"activity\"/></td>\n" +
                        "\t\t\t\t\t\t\t\t<td>" + activity.name + "</td>\n" +
                        "\t\t\t\t\t\t\t\t<td>" + activity.startDate + "</td>\n" +
                        "\t\t\t\t\t\t\t\t<td>" + activity.endDate + "</td>\n" +
                        "\t\t\t\t\t\t\t\t<td>" + activity.owner + "</td>\n" +
                        "\t\t\t\t\t\t\t</tr>");
                    }





                }, 'json');
        }
    });

    $("#bind").click(function () {
        var activityId = $('.son:checked')[0].value;
        $('#activityId').val(activityId);
        // 获取勾中的市场活动的名称
        var activityName = $($('.son:checked')[0]).parent().next().text();
        $('#activity').val(activityName);
});


</script>

</body>
</html>