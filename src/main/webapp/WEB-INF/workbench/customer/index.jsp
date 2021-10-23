<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/jquery/bs_pagination/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bs_pagination/en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
<script type="text/javascript">$(function(){
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });});</script>
</head>
<body>

	<!-- 创建客户的模态窗口 -->
	<div class="modal fade" id="createCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="saveForm">
					
						<div class="form-group">
							<label for="create-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-customerOwner" name="owner">
                                    <option selected>请选择</option>
                                    <c:forEach items="${users}" var="user">
                                        <option value="${user.id}">${user.name}</option>
                                    </c:forEach>
								</select>
							</div>
							<label for="create-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-customerName" name="name">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-website" name="website">
                            </div>
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone" name="phone">
							</div>
						</div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe" name="describe"></textarea>
							</div>
						</div>
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="create-contactSummary" name="contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="create-nextContactTime" name="nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address1" name="address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveCustomer()">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改客户的模态窗口 -->
	<div class="modal fade" id="editCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="updateForm">
                        <input type="hidden" id="edit-id" name="id"/>
						<div class="form-group">
							<label for="edit-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-customerOwner" name="owner">
                                    <option selected>请选择</option>
								</select>
							</div>
							<label for="edit-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-customerName" name="name">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website" name="website">
                            </div>
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" name="phone">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe" name="description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary" name="contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="edit-nextContactTime" name="nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address" name="address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateCustomer()">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	

	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>客户列表</h3>
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
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="phone">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司网站</div>
				      <input class="form-control" type="text" id="website">
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="searchBtn">查询</button>
				  
				</form>
			</div>

			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal" onclick="createCustomerModal()"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" onclick="editCustomerModal()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" onclick="deleteBench()"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="father"/></td>
							<td>名称</td>
							<td>所有者</td>
							<td>公司座机</td>
							<td>公司网站</td>
						</tr>
					</thead>
					<tbody id="customerTbody">
			<%--			<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点</a></td>
							<td>zhangsan</td>
							<td>010-84846003</td>
							<td>http://www.bjpowernode.com</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点</a></td>
                            <td>zhangsan</td>
                            <td>010-84846003</td>
                            <td>http://www.bjpowernode.com</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="customerPage"></div>
		</div>
        </div>
	</div>
<script>
    //重新执行下分页编码
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
    //分页查询及其搜索
    //定义刷新客户页面数据的方法
    refreshCustomerList(1,2)
    function refreshCustomerList(page,pageSize) {
    //    发送请求查询页面数据
        $.get("${pageContext.request.contextPath}/workbench/customer/list",
            {
                'page' : page,
                'pageSize': pageSize,
            // 查询所需的条件数据
                'name': $('#name').val(),
                'owner': $('#owner').val(),
                'phone': $('#phone').val(),
                'website': $('#website').val()
            },
            function (data) {
            //返回的data数据是pageInfo其中含有已经分页的customerlList数据
            //清空列表
                $('#customerTbody').html("")
                var customerList = data.list;
            //    遍历客户集合将每个客户拼到表格内部
                for (var i = 0;i<customerList.length;i++) {
                    var customer = customerList[i];
                    $('#customerTbody').append("<tr>\n" +
                        "\t\t\t\t\t\t\t<td><input type=\"checkbox\" value=\""+customer.id+"\" class=\"son\" onclick=\"change()\" /></td>\n" +
                        "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='${pageContext.request.contextPath}/toView/workbench/customer/detail?id="+customer.id+"';\">"+customer.name+"</a></td>\n" +
                        "\t\t\t\t\t\t\t<td>"+customer.owner+"</td>\n" +
                        "\t\t\t\t\t\t\t<td>"+customer.phone+"</td>\n" +
                        "\t\t\t\t\t\t\t<td>"+customer.website+"</td>\n" +
                        "\t\t\t\t\t\t</tr>");
                }

                //分页插件
                $("#customerPage").bs_pagination({
                    currentPage: data.pageNum, // 页码
                    rowsPerPage: data.pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数
                    totalPages: data.pages, // 总页数
                    totalRows: data.total, // 总记录条数
                    visiblePageLinks: 4, // 显示几个卡片
                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage: function (event, obj) {
                        refreshCustomerList(obj.currentPage, obj.rowsPerPage);
                    }
                });
            },'json');
    }

    //手动打开创建客户模态窗口
    function createCustomerModal() {
        $('#createCustomerModal').modal('show');

    }
    //保存添加的客户
    function saveCustomer() {
        var saveForm = $('#saveForm').serialize();
        $.get("${pageContext.request.contextPath}/workbench/customer/saveOrUpdate",saveForm,function (data) {
            if (data.ok) {
                layer.alert(data.message,{icon:6})
                refreshCustomerList(1,2)
            }
        },'json');
    }
//    搜索
    $('#searchBtn').bind('click',function () {
        refreshCustomerList(1,2);
    });
//全选多选
    $('#father').click(function () {
        $('.son').prop('checked',$(this).prop('checked'))
    });
    //当给动态生成的元素添加事件函数，会自动的把元素创建出来


function change() {
    //获取勾中的son的个数
    var checkedLength = $('.son:checked').length;
    //获取所有son的个数
    var length = $('.son').length;
    if(checkedLength == length){
        //全部勾中了
        $('#father').prop('checked',true);
    }else{
        $('#father').prop('checked',false);
    }
}



//   编辑客户信息第一步数据回显
//    手动打开编辑窗口
function editCustomerModal() {
    var checkedLength = $('.son:checked').length;
    if (checkedLength == 0) {
        layer.alert("未选择需要选择的项目", {icon: 5});
    }else if (checkedLength > 1) {
        layer.alert("一次只能修改一个项目", {icon: 5});
    } else {
    //        手动打开编辑模态窗口
        $('#editCustomerModal').modal('show');
    //获取js数组中的js对象并转化为jQuery对象(并获取已经选择的id)
        var id =$($('.son:checked')[0]).val();
        $.get("${pageContext.request.contextPath}/workbench/customer/queryById",{'id':id},function (data) {
        //    通过设置值将通过id查询到的customer数据回显到编辑模态窗口中去|
            var customer = data;
                $('#edit-customerName').val(customer.name);
                $('#edit-website').val(customer.website);
                $('#edit-phone').val(customer.phone);
                $('#edit-describe').val(customer.description);
                $('#edit-contactSummary').val(customer.contactSummary);
                $('#edit-nextContactTime').val(customer.nextContactTime);
                $('#edit-address').val(customer.address);
                $('#edit-customerName').val(customer.name);
                $('#edit-customerName').val(customer.name);
                $('#edit-id').val(customer.id)
        //异步查询所有者信息并拼到所有者下拉框选项中
            $.get("${pageContext.request.contextPath}/workbench/customer/queryUsers",function (data) {
                var userList = data;
                var content = "";
                for (var i = 0;i<userList.length;i++) {
                    if (userList[i] == customer.owner) {
                        content += "<option selected value=" + userList[i].id + ">" + userList[i].name + "</option>";
                    } else {
                        content += "<option value="+userList[i].id+">"+userList[i].name+"</option>";
                    }

                    $('#edit-customerOwner').html(content);

                }


            },'json');
        },'json');

    }
}
    //编辑客户信息第二步更新数据
    function updateCustomer() {
        var updateForm = $('#updateForm').serialize();
        $.get("${pageContext.request.contextPath}/workbench/customer/saveOrUpdate",updateForm,function (data) {
            if (data.ok) {
                layer.alert(data.message,{icon:6})
                refreshCustomerList(1,2)
            }
        },'json');

    }
//删除客户信息
    function deleteBench() {
        var checkedLength = $('.son:checked').length;
        if (checkedLength == 0) {
            layer.alert("至少选择一项需要删除的项目", {icon: 5});
        }else {
            layer.alert("确定删除勾中的"+checkedLength+"条数据吗？", {
                time: 0 //不自动关闭
                ,btn: ['确定', '取消']
                ,yes: function(index) {
                    //点击确定按钮后，关闭弹窗
                    layer.close(index);
                    var ids = [];
                    $('.son:checked').each(function () {
                        var id = $(this).val()
                        ids.push(id)
                    });
                    $.get("${pageContext.request.contextPath}/workbench/customer/deleteCustomer",{'ids':ids.join()},function (data) {
                        if (data.ok) {
                            layer.alert(data.message,{icon:6})
                        }
                        refreshCustomerList(1,2)
                    },'json');

                }});

        }
    }

</script>

</body>
</html>