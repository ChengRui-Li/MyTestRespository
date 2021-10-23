<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});
		
		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});
		
		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});
	});
	
</script>

</head>
<body>
	
	<!-- 修改市场活动备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<%-- 备注的id --%>
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改备注</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <input type="hidden" id="id"/>
                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="noteContent"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
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
                    <h4 class="modal-title" id="myActivityModalLabel">修改市场活动</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form" id="updateForm">

                        <div class="form-group">
                            <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-marketActivityOwner">
                                </select>
                            </div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-startTime">
                            </div>
                            <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-endTime">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost" value="5,000">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3 id="activityDetailTitle"></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" data-toggle="modal" onclick="openEditActivity()"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger" onclick="deleteBench()"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="owner"></b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;" >名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="name"></b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">开始日期</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="startDate"></b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="endDate"></b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">成本</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="cost"></b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"id="createOwner"></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"id="editOwner"></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;" id="activityDetail">
				<b>
					市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 30px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>




		<%--<!-- 备注1 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="/crm/image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>

		<!-- 备注2 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="${pageContext.request.contextPath}/image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>--%>
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" onclick="addActivityRemarks()">保存</button>
				</p>
			</form>
		</div>
	</div>
	<div style="height: 200px;"></div>

<script>

   function refresh(activity){

       $.get("${pageContext.request.contextPath}/workbench/activity/detail",
           { 'id':'${id}'},
           function (data) {
               var activity = data;
               //把返回的数据设置到页面上 市场活动-发传单 <small>2020-10-10 ~ 2020-10-20</small>
               //
               $('#activityDetailTitle').html("市场活动-"+activity.name +"<small>"+activity.startDate+"~"+activity.endDate+"</small>");
               $('#owner').html(activity.owner)
               $('#name').html(activity.name)
               $('#startDate').html(activity.startDate)
               $('#endDate').html(activity.endDate)
               $('#cost').html(activity.cost)
               $('#createOwner').html("<b>"+activity.createBy+"&nbsp;&nbsp;</b><small style=\"font-size: 10px; color: gray;\">"+activity.createTime+"</small>")
               $('#editOwner').html("<b >"+activity.editBy+"&nbsp;&nbsp;</b><small style=\"font-size: 10px; color: gray;\">"+activity.editTime+"</small>")
               //        拼接备注信息
               var activityRemarks = activity.activityRemarks;
               $('.remarkDiv').remove();
               for (var i = 0;i<activityRemarks.length;i++) {
                   var activityRemark = activityRemarks[i];
                   $('#remarkDiv').before("<div id="+activityRemark.id+" class=\"remarkDiv\" style=\"height: 60px;\">\n" +
                       "\t\t\t<img title="+activityRemark.createBy+" src="+activityRemark.img+" style=\"width: 30px; height:30px;\">\n" +
                       "\t\t\t<div style=\"position: relative; top: -40px; left: 40px;\" >\n" +
                       "\t\t\t\t<h5>"+activityRemark.noteContent+"</h5>\n" +
                       "\t\t\t\t<font color=\"gray\">市场活动</font> <font color=\"gray\">-</font> <b>"+activity.name+"</b> <small style=\"color: gray;\"> "+activityRemark.createTime+"由"+activityRemark.createBy+"</small>\n" +
                       "\t\t\t\t<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">\n" +
                       "\t\t\t\t\t<a class=\"myHref\" onclick=\"openModal('"+activityRemark.noteContent+"','"+activityRemark.id+"')\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>\n" +
                       "\t\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                       "\t\t\t\t\t<a class=\"myHref\" onclick=\"deleteActivityRemark('"+activityRemark.id+"')\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>\n" +
                       "\t\t\t\t</div>\n" +
                       "\t\t\t</div>\n" +
                       "\t\t</div>");

                   $(".remarkDiv").mouseover(function(){
                       $(this).children("div").children("div").show();
                   });

                   $(".remarkDiv").mouseout(function(){
                       $(this).children("div").children("div").hide();
                   });

                   $(".myHref").mouseover(function(){
                       $(this).children("span").css("color","red");
                   });

                   $(".myHref").mouseout(function(){
                       $(this).children("span").css("color","#E6E6E6");
                   });
               }

           },'json');

   }

    $.get("${pageContext.request.contextPath}/workbench/activity/detail",
        { 'id':'${id}'},
        function (data) {
            var activity = data;
    //把返回的数据设置到页面上 市场活动-发传单 <small>2020-10-10 ~ 2020-10-20</small>
    //
            $('#activityDetailTitle').html("市场活动-"+activity.name +"<small>"+activity.startDate+"~"+activity.endDate+"</small>");
            $('#owner').html(activity.owner)
            $('#name').html(activity.name)
            $('#startDate').html(activity.startDate)
            $('#endDate').html(activity.endDate)
            $('#cost').html(activity.cost)
            $('#createOwner').html("<b>"+activity.createBy+"&nbsp;&nbsp;</b><small style=\"font-size: 10px; color: gray;\">"+activity.createTime+"</small>")
            $('#editOwner').html("<b >"+activity.editBy+"&nbsp;&nbsp;</b><small style=\"font-size: 10px; color: gray;\">"+activity.editTime+"</small>")
            refresh(activity)

    },'json');

    //    手动打开编辑模态窗口
    function openEditActivity(id) {
            $('#editActivityModal').modal('show');
            //    类选择器是一个元素数组，其中是js元素
            //    获取已选择的元素的id
                      id = '${id}';
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

    function deleteBench(id){
            layer.alert("确定删除该数据吗？", {
                time: 0 //不自动关闭
                ,btn: ['确定', '取消']
                ,yes: function(index){
                    //点击确定按钮后，关闭弹窗
                    layer.close(index);
                    //  定义一个数组
                    var ids = [];
                    id = '${id}';
                    ids.push(id);
                    //      join:把数组中的内容以指定分隔符的形式拼接成字符串
                    //         异步删除
                    $.post("${pageContext.request.contextPath}/workbench/activity/deleteBench",{'ids':ids.join()},function (data) {
                        if (data.ok) {
                            layer.alert(data.message, {icon: 6});
                            // window.location="http://localhost:8080/crm/toView/workbench/index?akdjlkjfl="
                            //自动返回上一个页面
                                window.history.go(-1);
                            //定义超时时间1ms
                                setTimeout("go()",1);
                    }
                },'json');
            }
        });
    }
//    添加市场活动备注
    function addActivityRemarks() {
        $.post("${pageContext.request.contextPath}/workbench/activity/addActivityRemark",
            {
                'activityId':'${id}',//备注外键，市场活动主键
                'noteContent': $('#remark').val()
            },function (data) {
                if (data.ok) {
                    layer.alert(data.message, {icon: 6});
                    refresh();
                }
        },'json');
    }
//    手动弹出修改备注的模态窗口
   function openModal(noteContent,id){
        //手动弹出模态框
        $('#editRemarkModal').modal('show');
        $('#noteContent').val(noteContent);
   //     把备注主键设置到隐藏域中
        $('#id').val(id);
   }
//   更新备注信息
   $('#updateRemarkBtn').click(function () {
       $.post("${pageContext.request.contextPath}/workbench/activity/updateActivityRemark",
           {
               'noteContent': $('#noteContent').val(),
               'id': $('#id').val()
           },function (data) {
               if (data.ok) {
                   $('#editRemarkModal').modal('hide');
                   refresh();
               }
       },'json');
   });
//    删除备注信息
   function deleteActivityRemark(id) {
       layer.alert("确定删除该备注数据吗？", {
           time: 0 //不自动关闭
           ,btn: ['确定', '取消']
           ,yes: function(index){
               //点击确定按钮后，关闭弹窗
               layer.close(index);
               //      join:把数组中的内容以指定分隔符的形式拼接成字符串
               //         异步删除
               $.post("${pageContext.request.contextPath}/workbench/activity/deleteActivityRemark",{'id':id},function (data) {
                   if (data.ok) {
                       layer.alert(data.message, {icon: 6});
                       refresh();
                   }
               },'json');
           }
       });

   }





</script>
</body>
</html>