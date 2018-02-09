<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>航班动态</title>
<!-- bootstrap 3.0.2 -->
<%-- <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css" /> --%>
<link href="${pageContext.request.contextPath}/css/bootstrap-table.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/css/airline_dynamic/airline_dynamic.css"
	rel="stylesheet" type="text/css" />
<link href="/css/newPageCss/salesReports.css" rel="stylesheet">
<link href="/css/newPageCss/dateKJ.css" rel="stylesheet">
<link href="/css/newPageCss/salesReports-day.css" rel="stylesheet">
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
	<% 
				Object sessionValues=session.getAttribute("companyName_session"); 
			%>
	<input name="dpt_AirPt_Cd" hidden="hidden" value="<%=sessionValues%>"
		id="cityChoice" size="14">
	<div class="sr-box-body-report" style="height: 100%">
		<ul>
			<li id="li1" class="liset" style="padding-top: 170px; height: 32%"
				onclick='getAirPortData(0);'><div>航班动态列表</div></li>
			<li id="li2" style="padding-top: 170px; height: 33%"
				onclick='getAirPortData(1);'><div>异常情况统计</div></li>
		</ul>
	</div>
	<div class="sr-box" style="height: 100%">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel">&#xe629;</li>
					<li class="tltle-sel tltle-selI"> <span>航班动态</span>
					</li>
					<li class="tltle-sel">&#xe62a;</li>
					<li class="tltle-sel">&#xe62b;</li>
				</ul>
			</div>
			<div class="sr-box-head-inquire" style="margin-right: 0;">
				<ul>
					<li><span>时间</span> <input id="cc2" name="lcl_Dpt_Day"
						placeholder="请选择月份" onfocus="new WdatePicker(this)"></li>
				</ul>
				<div class="sr-box-head-btn" onclick="sessionData()">查询</div>
			</div>
		</div>

		<div class="sr-box-body" style="height: 95.3%;">
			<div class="sr-box-body-chart" style="height: 100%; width: 100%">
				<div class="sr-box-body-chart-income Income-table"
					style="height: 100%; width: 100%">
					<p class="p-height" id='isen'
						style="font-size: 20px; font-weight: 900">
						班期时刻表 <span
							style="margin-right: 100px; margin-left: 80%; font-size: 16; font-weight: normal">
							<input type="button" id="b_color" class="b_color"
							onclick="clickd(0);" value="进港"
							style="border-width: 0px; padding-bottom: 0px; color: white; width: 60px; margin-right: -3px;">
							<input type="button" id="c_color" class="c_color"
							onclick="clickd(1);" value="出港"
							style="border-width: 0px; padding-bottom: 0px; color: white; width: 60px;">
						</span>
					</p>
					<table id="MyTimeTable"
						style="color: white; font-size: 16px; width: 100%;">
					</table>
					<p id="isp" style="margin-top: 120px; margin-left: 0px;" hidden="">
						<span style="margin-left: 6%; font-size: 22px">月准点率</span> <span
							style="margin-left: 6%; font-size: 22px">延误班次(班)</span> <span
							style="margin-left: 6%; font-size: 22px">取消班次(班)</span> <span
							style="margin-left: 6%; font-size: 22px">正常班次(班)</span><br>
						<span style="margin-left: 6%; font-size: 22px"> </span> <span
							style="margin-left: 160px; font-size: 22px"> </span> <span
							style="margin-left: 190px; font-size: 22px"> </span> <span
							style="margin-left: 210px; font-size: 22px"> </span><br>
					</p>
					<p id="isp2" style="margin-top: 100px; margin-left: 0px;" hidden="">
						<span style="margin-left: 6%; font-size: 18px">异常原因TOPS</span>
					<div id="main" style="height: 60%; width: 100%" align="center"></div>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- bootstrap表格需要的js文件 -->
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
	<%@include file="/WEB-INF/pages/airline_dynamic/js/airline_dynamic.jsp"%>
	<div hidden=""></div>
</body>
</html>