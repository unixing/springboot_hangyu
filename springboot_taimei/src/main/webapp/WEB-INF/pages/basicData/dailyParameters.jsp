<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>太美航空</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>

<%@include file="/WEB-INF/pages/common/css.jsp"%>
</head>
<body>
	<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">数据管理</a>》<a
		href="#">报表参数设置</a>
	</font>
	<br>
	<br>
	<aside>
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<div id="toolbar" class="btn-group">
						<button onclick="addData()" id="btn_add" type="button"
							class="btn btn-default">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
						</button>
						<button onclick="updateData()" id="btn_edit" type="button"
							class="btn btn-default">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
						</button>
						<button onclick="deleteData()" id="btn_delete" type="button"
							class="btn btn-default">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
						</button>
					</div>
					<table id="parametersTable">
					</table>
					<div id="updateTable" style="display: none;">
						<form id="dataform">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th colspan="4"
											style="text-align: center; vertical-align: middle;">新增数据</th>
									</tr>
								</thead>
								<tr>
									<td style="text-align: right; vertical-align: middle;">
										开始时间：</td>
									<td style="text-align: left; vertical-align: middle;"><input
										id="start_time" type='text' width="140px;" name="start_time"
										class="Wdate" onfocus="WdatePicker()" value="" /></td>
									<td style="text-align: right; vertical-align: middle;">
										开始时间：</td>
									<td style="text-align: left; vertical-align: middle;"><input
										id="end_time" type='text' width="140px;" name="end_time"
										class="Wdate" onfocus="WdatePicker()" value="" /></td>
								</tr>
								<tr>
									<td style="text-align: right; vertical-align: middle;">
										代理费率：</td>
									<td style="text-align: left; vertical-align: middle;"><input
										name="agence_price" id="agence_price" style="width: 140px"
										placeholder="代理费率">%</td>
									<td style="text-align: right; vertical-align: middle;">
										小时成本：</td>
									<td style="text-align: left; vertical-align: middle;"><input
										name="hour_price" id="hour_price" style="width: 140px"
										placeholder="小时成本"> <input name="id" id="id"
										style="width: 140px" type="hidden"></td>
								</tr>
								<tr>
									<td style="text-align: right; vertical-align: middle;">
										座位数：</td>
									<td style="text-align: left; vertical-align: middle;"
										colspan="3"><input name="fly_site_min" id="fly_site_min"
										style="width: 100px" placeholder="最小座位数">到 <input
										name="fly_site" id="fly_site" style="width: 100px"
										placeholder="最大座位数"></td>
								</tr>
								<tr>
									<td colspan="4"
										style="text-align: center; vertical-align: middle;">
										<button id="btn_sav" onclick="doSaveOrUpdate()" type="button"
											class="btn btn-default">
											<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>保存
										</button>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</section>
	</aside>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/basicData/js/dailyParametersJS.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>