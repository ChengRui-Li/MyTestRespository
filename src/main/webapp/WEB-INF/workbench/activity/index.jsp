<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/jquery/bs_pagination/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bs_pagination/en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>

</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form" id="saveForm">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner" name="owner">
                                </select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName" name="name">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime" name="startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime" name="endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost" name="cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe" name="description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveActivity()">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form" id="updateForm">
                        <input type="hidden" name="id" id="id">

						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner" name="owner">
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" name="name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" name="startDate">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" name="endDate">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" name="cost">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe" name="description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateActivity()">更新</button>
				</div>
			</div>
		</div>
	</div>




	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="startTime"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="endTime">
				    </div>
				  </div>
				  <%--该处必须使用button，因为是使用点击触发异步查询的，--%>
                  <%--如果使用了type=""submit会导致整个页面刷新刷新(在服务器回复之后)--%>
                  <%--因此如果使用submit异步查询将会失效--%>
				  <button type="button" id="queryBtn" class="btn btn-default">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
                 <%--   onclick方法必须写在,<button></button>标签的左标签中--%>
				  <button type="button" class="btn btn-primary" data-toggle="modal"onclick="openCreateActivity()"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal"onclick="openEditActivity()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" onclick="deleteBench()"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				  <button type="button" class="btn btn-primary" onclick="exportExcel()"><span class="glyphicon glyphicon-arrow-down"></span>下载</button>
				</div>

			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="father"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">--%>
							<%--<td><input type="checkbox" /></td>--%>
							<%--<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
                            <%--<td>zhangsan</td>--%>
							<%--<td>2020-10-10</td>--%>
							<%--<td>2020-10-20</td>--%>
						<%--</tr>--%>
                        <%--<tr class="active">--%>
                            <%--<td><input type="checkbox" /></td>--%>
                            <%--<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
                            <%--<td>zhangsan</td>--%>
                            <%--<td>2020-10-10</td>--%>
                            <%--<td>2020-10-20</td>--%>
                        <%--</tr>--%>
					</tbody>
				</table>
			</div>

			<div style="height: 50px; position: relative;top: 30px;">
                <div id="activityPage"></div>
			</div>
		</div>
	</div>

<script>
    var rsc_bs_pag = {
        go_to_page_title: 'Go to page',
        rows_per_page_title: 'Rows per page',
        current_page_label: 'Page',
        current_page_abbr_label: 'p.',
        total_pages_label: 'of',
        total_pages_abbr_label: '/',
        total_rows_label: 'of',
        rows_info_records: 'records',
        go_top_text: '首页',
        go_prev_text: '上一页',
        go_next_text: '下一页',
        go_last_text: '末页'
    };
    refresh(1, 3);
    //定义刷新页面数据的方法
    function refresh(page,pageSize){
        $.get("${pageContext.request.contextPath}/workbench/activity/list",{
            'page':page,//当前页码
            'pageSize':pageSize,//每页记录数
        //   查询的条件数据
            'name': $('#name').val(),
            'owner': $('#owner').val(),
            'startDate': $('#startTime').val(),
            'endDate': $('#endTime').val()
        },function (data) {
                var pageInfo = data;
            //清空列表内容
                $('#activityBody').html("");
            //      该处循环出错会导致分页插件无法显示
            //      本次出错我将i<pageInfo.list.length 错误的写成i<=pageInfo.length
            //      导致循环无法结(pageInfo.list[pageInfo.length]无法取出集合中的
            //      数据导致程序运行错误，由于程序是自上而下，因此无法运行后面的
            //      分页插件，而导致前台的页面的分页页码无法正常显示，而且后台不会报错)
            for(var i = 0;i < pageInfo.list.length;i++) {
                var activity = pageInfo.list[i];
                $('#activityBody').append("<tr class=\"active\">\n" +
                    "\t\t\t\t\t\t\t<td><input type=\"checkbox\" value="+activity.id+" class='son'onclick='change()' /></td>\n" +
                    "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='${pageContext.request.contextPath}/toView/workbench/activity/detail?id="+activity.id+"';\">"+activity.name+"</a></td>\n" +
                    "                            <td>"+activity.owner +"</td>\n" +
                    "\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
                    "\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
                    "\t\t\t\t\t\t</tr>");
            }
                //分页插件
                $("#activityPage").bs_pagination({
                    currentPage: pageInfo.pageNum, // 页码
                    rowsPerPage: pageInfo.pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数
                    totalPages: pageInfo.pages, // 总页数
                    totalRows: pageInfo.total, // 总记录条数
                    visiblePageLinks: 4, // 显示几个卡片
                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage : function(event, obj){
                        refresh(obj.currentPage,obj.rowsPerPage);
                    }
                });
        },'json');
    };
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

    //      多条件查询功能(参数1:事件的名称；参数2:事件触发的函数。)
    $('#queryBtn').bind('click',function () {
        refresh(1, 3);

    });


    //  多选全选js代码
    $('#father').click(function () {
/*        //    方法一判断法
        if ($(this).prop('checked')) {
        //    使用类选择器
            $('.son').prop('checked', true);
        }else{
        //    使用类选择器
            $('.son').prop('checked', false);
        }*/
    //    方法二把判断放入prop方法中减少代码量
        $('.son').prop('checked', $(this).prop('checked'));
    });
//    同步选择框/当所有的子类复选框被选中时父类的复选框将会被自动选中
//    注意:动态生成的（class='son'的input元素）js对象会失效，解决办法是将动态生成的元素的事件委托给第一个，不是动态生成的父类元素
//    on（）；方法，参数一：事件名称；参数二:委托的元素
/*    $('#activityBody').on('click', '.son', function () {
    //    获取被选中的son的个数
        var checkedLength = $('.son:checked').length;
    //    获取所有son的个数
        var length = $('.son').length;
        if (length == checkedLength){
            $('#father').prop('checked',true)
        }else {
            $('#father').prop('checked',false)
        }
    });*/
//  给动态生成的元素添加change事件也可以解决动态js元素失效的问题
function change() {
    //    获取被选中的son的个数
    var checkedLength = $('.son:checked').length;
    //    获取所有son的个数
    var length = $('.son').length;
    if (length == checkedLength){
        $('#father').prop('checked',true)
    }else {
        $('#father').prop('checked',false)
    }

}

//    手动打开编辑模态窗口
function openEditActivity() {
    var checkedLength = $('.son:checked').length;
    if (checkedLength == 0) {
        layer.alert("未选择需要修改的目标项目", {icon: 5});
    }else if (checkedLength > 1) {
        layer.alert("一次只能修改一个项目", {icon: 5});
    } else {
        $('#editActivityModal').modal('show');
    //    类选择器是一个元素数组，其中是js元素
    //    获取已选择的元素的id
        var id = $($('.son:checked')[0]).val();
    //    根据选择的数据的id查询activity的数据
        $.get("${pageContext.request.contextPath}/workbench/activity/queryById", {
            'id':id
        }, function (data) {
            var activity = data;
        $('#edit-marketActivityName').val(activity.name )
        $('#edit-startTime').val(activity.startDate )
        $('#edit-endTime').val(activity.endDate )
        $('#edit-cost').val(activity.cost)
        $('#edit-describe').val(activity.description )
    //  设置主键到隐藏域
        $('#id').val(activity.id)
            $.get("${pageContext.request.contextPath}/workbench/activity/queryUsers", function (data) {
                var userList = data;
                var content = "";
                for (var i = 0;i < userList.length;i++) {
                    var user = userList[i];
                    //拼出下拉框选项的html代码
                    //第一种方法使得下拉框的默认选项为复选框选中的所有者
                    if (activity.owner == user.id) {
                        content += "<option selected value= " + user.id + ">" + user.name + "</option>";
                    } else {
                        content += "<option value="+user.id +">"+user.name +"</option>";
                    }
                    // content = content + "<option value="+user.id +">"+user.name +"</option>";
                }
                $('#edit-marketActivityOwner').html(content);
                //选中对应()内容的选项
                // $('#edit-marketActivityOwner').val(activity.owner);
            }, 'json');
        },'json');
    }
}
//     手动打开添加模态窗口
function openCreateActivity() {
    //    手动弹出添加模态窗口
    $('#createActivityModal').modal('show');
//  异步查询所有者信息（拼入下拉框选项中）
    $.get("${pageContext.request.contextPath}/workbench/activity/queryUsers",function (data) {
        var userList = data;
        var content = "";
        for (var i = 0;i < userList.length;i++) {
            var user = userList[i];
            content += "<option value="+user.id+">"+user.name+" </option>";
        }
        $('#create-marketActivityOwner').html(content);

    },'json');
}
function saveActivity() {
//      表单序列化(序列化结果可以直接作为参数传入请求)
    var saveForm = $('#saveForm').serialize();
    $.post("${pageContext.request.contextPath}/workbench/activity/saveOrUpdate", saveForm, function (data) {
        if (data.ok) {
        layer.alert(data.message, {icon: 6});
        refresh(1,4)
        }
    },'json');
}
    function updateActivity() {
//      表单序列化(序列化结果可以直接作为参数传入请求)
    var updateForm = $('#updateForm').serialize();
    $.post("${pageContext.request.contextPath}/workbench/activity/saveOrUpdate", updateForm, function (data) {
        if (data.ok) {
        layer.alert(data.message, {icon: 6});
        refresh(1,4)
        }
    },'json');
    }
    function deleteBench(){
    //    获取勾选的数据条数
        var checkedLength = $('.son:checked').length;
        if (checkedLength == 0) {
            layer.alert("至少选择一条数据", {icon: 5});
        } else {//信息框-例2
            layer.alert("确定删除勾中的"+checkedLength+"条数据吗？", {
                time: 0 //不自动关闭
                ,btn: ['确定', '取消']
                ,yes: function(index){
                    //点击确定按钮后，关闭弹窗
                    layer.close(index);
        //            定义一个数组
                    var ids = [];
        //            遍历所有已经将勾选的元素
                    $('.son:checked').each(function () {
                        var id = $(this).val();
                        ids.push(id);
                    });
        //      join:把数组中的内容以指定分隔符的形式拼接成字符串
        //         异步删除
                    $.post("${pageContext.request.contextPath}/workbench/activity/deleteBench",{'ids':ids.join()},function (data) {
                        if (data.ok) {
                        layer.alert(data.message, {icon: 6});
                        refresh(1,4)
                        }
                    },'json');
                }
            });
          }
        }
        function exportExcel() {
            location.href = "/crm/workbench/activity/exportExcel";;
            
        }
        
</script>

</body>
</html>