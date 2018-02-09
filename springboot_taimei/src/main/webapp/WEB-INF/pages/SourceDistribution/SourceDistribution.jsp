<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

<title>客源组成</title>
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
<link href="/css/newPageCss/publicsControls.css" rel="stylesheet">
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.class1 {
	display: inline;
}

.class2 {
	display: inline;
}
</style>
</head>

<body>
	<% 
				Object sessionValues=session.getAttribute("companyItia_session"); 
			%>
	<input name="dpt_AirPt_Cd" hidden="hidden" value="<%=sessionValues%>"
		id="cityChoice" size="14">
	<div class="sr-box-body-report" style="height: 100%">
		<ul>
			<!--                 <li id="li1"  class="liset" style="padding-top: 170px; height: 30%" onclick='getAirPortData(0);'><div>航班动态列表</div></li> -->
			<!--                 <li id="li2" style="padding-top: 170px;height: 30%" onclick='getAirPortData(1);'><div>异常情况统计</div></li> -->
		</ul>
	</div>
	<div class="sr-box">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel">&#xe629;</li>
					<li class="tltle-sel tltle-selI"> <span>客源组成</span>
					</li>
					<li class="tltle-sel">&#xe62a;</li>
					<li class="tltle-sel">&#xe62b;</li>
				</ul>
			</div>
			<div class="sr-box-head-inquire" style="margin-right: 0;">
				<ul>
					<li><span>开始时间</span><input id="lcl_Dpt_Tm" type="text"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="开始时间">
					</li>
					<li><span>结束时间</span><input id="lcl_Arrv_Tm" type="text"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="结束时间">
					</li>
					<li><span>始发点</span><input class="selectCity"
						id="dpt_AirPt_Cd" type="text" placeholder="始发地"></li>
					<li><span>到达地</span><input class="selectCity"
						id="arrv_Airpt_Cd" type="text" placeholder="到达地"></li>
					<li><span>经停</span><input class="selectCity" id="pas_stp"
						type="text" placeholder="经停地"></li>
					<li><span>航班号</span><input id="flt_nbr" type="text"
						placeholder="航班号"></li>
				</ul>
				<div class="sr-box-head-btn" onclick="check()">查询</div>
			</div>
		</div>

		<div class="sr-box-body" style="height: 1288px;">
			<div class="sr-box-body-chart" style="height: 100%; width: 100%">
				<div class="sr-box-body-chart-income Income-table"
					style="height: 100%; width: 100%">
					<p id="isp2" style="margin-top: 26px; margin-left: 0px;">
						<span style="margin-left: 6%; font-size: 18px">国内客源分布</span>
					<div id="main" style="height: 30%; width: 100%" align="center"></div>
					<!-- 				</p> -->
					<p id="isp2" style="margin-top: -22px; margin-left: 0px;">
						<span id="mcity" style="margin-left: 6%; font-size: 18px"></span>
					<div id="main1" style="height: 30%; width: 100%" align="center"></div>
					<!-- 				</p> -->
					<p id="isp2" style="margin-top: -22px; margin-left: 0px;">
						<span style="margin-left: 6%; font-size: 18px">男女比例</span> <span
							style="margin-left: 40%; font-size: 18px">年龄层次比例</span>
					<table style="height: 35%; width: 99%;">
						<tr>
							<td style="height: 15%; width: 50%;">
								<div id="main2" style="height: 99%; width: 99%;"></div>
							</td>
							<td style="height: 15%; width: 50%;">
								<div id="main3" style="height: 99%; width: 99%;"></div>
							</td>
						</tr>
					</table>
					<!-- 				</p> -->
				</div>
			</div>
		</div>
	</div>
	<!-- bootstrap表格需要的js文件 -->
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<script type="text/javascript"
		src="/js/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="/js/newPageJs/salesReportJS/airport-controls.js"></script>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
	<%@include
		file="/WEB-INF/pages/SourceDistribution/js/SourceDistribution.jsp"%>
</body>
</html>