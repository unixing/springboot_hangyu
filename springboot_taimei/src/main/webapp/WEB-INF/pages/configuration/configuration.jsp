<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">数据管理</a>》<a
		href="#">配置采集号信息</a></font>
	<br>
	<br>
	<button class="btn btn-primary" type="button" onclick="edit()" id="btn">添加</button>
	<aside>
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<table id="configurationTable">
					</table>
				</div>
			</div>
		</section>
	</aside>
	<div class="modal" id="configuration_edit">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加采集号</h4>
				</div>
				<div class="modal-body">
					<form id="configuration_add">
						<input hidden id="id" name="id"> 公司名称:<input type="text"
							id="isc_Tem" name="isc_Tem" class="form-control"
							aria-describedby="basic-addon1"> eterm配置号:<input
							type="text" id="etm_Usr" name="etm_Usr" class="form-control"
							aria-describedby="basic-addon1"> eterm配置号密码:<input
							type="text" id="etm_Psw" name="etm_Psw" class="form-control"
							aria-describedby="basic-addon1"> 工作号:<input type="text"
							id="off_ID" name="off_ID" class="form-control"
							aria-describedby="basic-addon1"> 工作号密码:<input type="text"
							id="off_Pwd" name="off_Pwd" class="form-control"
							aria-describedby="basic-addon1"> 级别:<input type="text"
							id="acc_Lvl" name="acc_Lvl" class="form-control"
							aria-describedby="basic-addon1"> etermIP:<input
							type="text" id="etm_IP" name="etm_IP" class="form-control"
							aria-describedby="basic-addon1"> eterm端口:<input
							type="text" id="etm_Pot" name="etm_Pot" class="form-control"
							aria-describedby="basic-addon1"> 自动采集时间(时):<input
							readonly="readonly" id="aic_Tie" name="aic_Tie"
							class="form-control" onclick="WdatePicker({dateFmt:'HH:mm:ss'})"
							aria-describedby="basic-addon1"> 冲突等待时间(分):<input
							type="text" id="dte_Aic_Rrt" name="dte_Aic_Rrt"
							class="form-control" aria-describedby="basic-addon1">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="submitConfig();">保存</button>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/configuration/js/configuration.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>