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
<script type="text/javascript">

	$(function(){
		
		
		
	});
	
</script>
</head>
<body>

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text"id="customerName">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="stage">
					  	<option value="">请选择</option>
                        <c:forEach items="${map['stage']}" var="stage">
                            <option value="${stage.value}">${stage.text}</option>
                        </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="type">
					  	<option value="">请选择</option>
                        <c:forEach items="${map['transactionType']}" var="tranType">
                            <option value="${tranType.value}">${tranType.text}</option>
                        </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="source">
						  <option value="">请选择</option>
                          <c:forEach items="${map['source']}" var="source">
                              <option value="${source.value}">${source.text}</option>
                          </c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text"id="contactName">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='${pageContext.request.contextPath}/toView/workbench/transaction/save';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="editTran()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="father" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tranTbody">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='${pageContext.request.contextPath}/toView/workbench/transaction/detail?id=3b0b6ee661db47c9bd199e0f10cd4bcf';">动力节点-交易01</a></td>
							<td>动力节点</td>
							<td>谈判/复审</td>
							<td>新业务</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>李四</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点-交易01</a></td>
                            <td>动力节点</td>
                            <td>谈判/复审</td>
                            <td>新业务</td>
                            <td>zhangsan</td>
                            <td>广告</td>
                            <td>李四</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 20px;">
                <div id="cluePage"></div>
				</div>
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
    refresh(1,2);
    function refresh(page,pageSize) {

        $.get("${pageContext.request.contextPath}/workbench/tran/list",
            {
                'page':page,
                'pageSize':pageSize,

            // 搜索框中的参数
            'owner':$('#owner').val(),
            'name':$('#name').val(),
            'customerId':$('#customerName').val(),
            'stage':$('#stage').val(),
            'type':$('#type').val(),
            'source':$('#source').val(),
            'contactId':$('#contactName').val()
            },function (data) {
                var tranList = data.list;
                $('#tranTbody').html("");
                for (var i = 0;i < tranList.length;i++) {
                    tran = tranList[i];
                    $('#tranTbody').append("<tr>\n" +
                        "\t\t\t\t\t\t\t<td><input class=\"son\" onclick=\"change()\" value=\""+tran.id+"\" type=\"checkbox\" /></td>\n" +
                        "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='/crm/toView/workbench/transaction/detail?id="+tran.id+"';\">"+tran.name+"</a></td>\n" +
                        "\t\t\t\t\t\t\t<td>"+tran.customerId+"</td>\n" +
                        "\t\t\t\t\t\t\t<td>"+tran.stage+"</td>\n" +
                        "\t\t\t\t\t\t\t<td>"+tran.type+"</td>\n" +
                        "\t\t\t\t\t\t\t<td>"+tran.owner+"</td>\n" +
                        "\t\t\t\t\t\t\t<td>"+tran.source+"</td>\n" +
                        "\t\t\t\t\t\t\t<td>"+tran.contactsId+"</td>\n" +
                        "\t\t\t\t\t\t</tr>");
                }

                //分页插件
                $("#cluePage").bs_pagination({
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
                    onChangePage : function(event, obj){
                        refresh(obj.currentPage,obj.rowsPerPage);
                    }
                });

        },'json');
    }

    $('#searchBtn').bind('click', function () {
        refresh(1, 2)
    });
//  全选
    $('#father').click(function () {
        $('.son').prop('checked',$('#father').prop('checked'));
    });
//多选控制全选按钮
function change() {
//获取选中的数据的条数
    var checkedLength = $('.son:checked').length;
//    获取所有子选框的数量
    var length = $('.son').length;
//    判断勾选的条数是否与子选框的数量相同
    if (checkedLength == length) {
        $('#father').prop('checked', true);
    } else {
        $('#father').prop('checked', false);
    }
}
//点击编辑按钮跳转页面并带参数id到
    function editTran() {

        //取出动态生成的勾选的需要修改的数据
        var id = $($('.son:checked')[0]).val();
        window.location.href='${pageContext.request.contextPath}/toView/workbench/transaction/edit?id='+id+'';
}



</script>



</body>
</html>