<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>太美航空</title>
<% if("truee".equals(request.getSession().getAttribute("versionn"))){ %>
		<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<META HTTP-EQUIV="expires" CONTENT="0">
	<%} %>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
</head>
<body>
	<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">航线网络规划</a>》<a
		href="#">时刻分布</a>
	</font>
	<br>
	<br>
	<aside>
		<table class="table">
			<tr>
				<td>
					<form id="systemLog-searchForm" method="post"
						enctype="application/x-www-form-urlencoded">
						<% 
						Object sessionValues=session.getAttribute("companyName_session"); 
					%>
						始发地: <input name="dpt_AirPt_Cd" id="dpt_AirPt_Cd"
							style="width: 98px" value="<%=sessionValues %>"> 到达地: <input
							name="arrv_Airpt_Cd" id="arrv_Airpt_Cd" style="width: 98px"
							placeholder="如:上海"> 采集时间:<select id="cc2" name="get_tim"
							class="btn btn-default dropdown-toggle "
							style="appearance: none; -moz-appearance: none; -webkit-appearance: none;">
						</select> <select name="goOrReturn" id="go"
							class="btn btn-default dropdown-toggle "
							style="appearance: none; -moz-appearance: none; -webkit-appearance: none;">
							<option value="go">单程</option>
							<option value="return">往返</option>
						</select> <input type="button" class="btn btn-success" name="mySbumit"
							id="btn" value="查询" onclick="getData();"></input>
					</form>
				</td>
				<td>
					<form method="post"
						action="${pageContext.request.contextPath}/jxlExcel">
						<input type="hidden" id="titles" name="titles" value="广州">
						<input type="hidden" id="colums" name="colums" value="十堰">
						<input type="submit" id="exec" class="btn btn-info"
							value="结果导出为excel格式">
					</form>
				</td>
				<td>
					<!-- 						<a href="javascript:void(0)" class="btn btn-warning" onclick="getNewData();" >采集最新的数据</a> -->
				</td>
			</tr>
		</table>

		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<table id="MyTimeTable">
					</table>
				</div>
			</div>
		</section>
	</aside>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/timetable/js/timetable.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>