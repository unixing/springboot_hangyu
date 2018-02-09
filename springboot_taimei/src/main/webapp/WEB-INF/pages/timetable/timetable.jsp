<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html lang="en">
<head>
<% if("truee".equals(request.getSession().getAttribute("versionn"))){ %>
		<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<META HTTP-EQUIV="expires" CONTENT="0">
	<%} %>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>销售报表</title>
<%--     <%@include file="/WEB-INF/pages/common/css.jsp"%> --%>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/bootstrap-table.css"
	rel="stylesheet" type="text/css" />
<!-- 城市的联动框下拉样式 -->
<link
	href="${pageContext.request.contextPath}/css/city/hzw-city-picker.css"
	rel="stylesheet" type="text/css">
<link href="/css/newPageCss/salesReports.css" rel="stylesheet">
<link href="/css/newPageCss/dateKJ.css" rel="stylesheet">
<link href="/css/newPageCss/salesReports-day.css" rel="stylesheet">
</head>
<body>
	<div class="sr-box">
		<div class="sr-box-head" style="background-color: rgba(0, 0, 0, 0.6)">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel tltle-selI"> <span>时刻分布</span>
					</li>
					<li class="tltle-sel">&#xe629;</li>
					<li class="tltle-sel">&#xe62a;</li>
					<li class="tltle-sel">&#xe62b;</li>
				</ul>
			</div>
			<div class="sr-box-head-inquire" style="margin-right: 0;">
				<ul>
					<li><span>始发点</span><input class="selectCity"
						id="dpt_AirPt_Cd" type="text" placeholder="始发地"></li>
					<li><span>到达地</span><input class="selectCity"
						id="arrv_Airpt_Cd" type="text" placeholder="到达地"></li>
					<li><span>航司</span><input id="pas_stp" type="text"
						placeholder="如MU"></li>
					<li><select id="flt_nbr_Count" name="flt_nbr_Count"
						class="btn" style="-moz-appearance: none; padding: 2px 44px;">
							<option value="go">单程</option>
							<option value="return">往返</option>
					</select></li>
				</ul>
				<div class="sr-box-head-btn" onclick="getData()">查询</div>
			</div>
		</div>
		<div class="sr-box-body">
			<div class="sr-box-body-chart" style="height: 95.3%; width: 100%">
				<div class="sr-box-body-chart-income Income-table"
					style="height: 100%; width: 100%">
					<p class="p-height" style="font-size: 20px; font-weight: 900">
						班期时刻表 <span
							style="margin-right: 100px; margin-left: 80%; font-size: 16; font-weight: normal">
							采集时间:<select id="cc2" name="get_tim"
							class="btn btn-default dropdown-toggle "
							style="background-color: #13233d; -moz-appearance: none; padding-top: 1px; padding-bottom: 1px; border-width: 0px; color: white;">
						</select>
						</span>
					</p>

					<table id="MyTimeTable" style="color: white;">
					</table>

				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/timetable/js/timetable.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>