<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit">
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
<link
	href="${pageContext.request.contextPath}/css/bootstrap-editable/bootstrap-editable.css"
	rel="stylesheet" type="text/css">
<style type="text/css">
.cover {
	position: fixed;
	top: 0px;
	right: 0px;
	bottom: 0px;
	filter: alpha(opacity = 60);
	background-color: #777;
	z-index: 1002;
	left: 0px;
	display: none;
	opacity: 0.5;
	-moz-opacity: 0.5;
}
</style>
</head>
<body>
<body>
	<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">数据管理</a>》<a
		href="#">上传数据</a>
	</font>
	<h2 align="center" style="color: red">请注意,暂时只支持Execl文件,请选择正确的数据源上传
		如下:</h2>
	<div class="container" align="center">
		<form method="post" 
			action="${pageContext.request.contextPath}/NullExcel">
			<input type="submit" class="btn btn-warning" value="下载上传数据文件表头模板">
		</form>
		<br> <input id="file" type="file" class="btn btn-warning"
			name="file"><br> 请选择数据所属公司&ensp;&ensp;: <select
			id="cars" class="btn btn-default dropdown-toggle "
			style="appearance: none; -moz-appearance: none; -webkit-appearance: none;">
		</select> 请选择数据来源&ensp;&ensp;: <select id="dta_Sce" name="dta_Sce"
			class="btn btn-default dropdown-toggle "
			style="appearance: none; -moz-appearance: none; -webkit-appearance: none;">
			<option value="CZM">CZM</option>
		</select> <br> <br> <input id="upload_file" class="btn btn-danger"
			type="button" onclick="checkData();" value="验证数据">
	</div>
	<h2 id='textData' align="center" style="color: red"></h2>
	<div class="col-md-12">
		<table id="example2" class="table table-bordered table-hover"
			data-toggle="table">
			<thead>
				<tr>
					<th data-field="lcl_Dpt_Day" data-sortable="true">日期</th>
					<th data-field="dpt_AirPt_Cd" data-sortable="true">始发</th>
					<th data-field="arrv_Airpt_Cd" data-sortable="true">到达</th>
					<th data-field="flt_Nbr" data-editable="true" data-sortable="true">航班号</th>
					<th data-field="cpy_Nm" data-sortable="true">公司</th>
					<th data-field="flt_Rte_Cd" data-sortable="true">航线</th>
					<th data-field="tal_Nbr_Set" data-sortable="true">座位</th>
					<th data-field="pgs_Per_Cls" data-sortable="true">旅客</th>
					<th data-field="dta_Sce" data-sortable="true">数据来源</th>
					<th data-field="dta_Dte" data-sortable="true">导入日期</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="col-md-12">
		<div class="modal" id="externalData_edit">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">添加YB运价加航距</h4>
					</div>
					<div class="modal-body">
						<form id="externalData_add"></form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="systemdictionaryDlg-bottons" align="center">
		<a href="javascript:void(0)" class="btn btn-success" onclick="save();">确定上传</a>
		<a href="javascript:void(0)" class="btn btn-info" onclick="cancel();">取消上传</a>
	</div>
	<div id="cover" class="cover" align="center">
		<h1>
			<font color="#D9D919">请等待数据的验证</font>
		</h1>
	</div>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/updata/js/externalData.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>
