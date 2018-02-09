<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>航班动态</title>
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/flightDynamic.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/calendar.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
</head>
<body>
	<div class="layer"></div>
	<div class="sr-box">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel" title="历史运营统计" id="_permissions_2"><a
						class='page-fun-change' href="/outPort">&#xe629;</a></li>
					<li class="tltle-sel tltle-selI" id="_permissions_6">&#xe665;<span>航班动态</span></li>
					<li class="tltle-sel" title="时刻分布" id="_permissions_7"><a class='page-fun-change'
						href="/processTask">&#xe666;</a></li>
				</ul>
			</div>
		</div>
		<div class="_customers">
			<div class="TM-loading" id="TM-sloading"></div>  
			<div class="_customers-nav">
				<div class="_customers-nav-a set">
					<div>
						<span>航班</span> <span>动态</span> <span>列表</span>
					</div>
				</div>
				<div class="_customers-nav-a">
					<div>
						<span>异常</span> <span>情况</span> <span>统计</span>
					</div>
				</div>
			</div>
			<div class="_customers-box"></div>
			<div class="reportErr">
				<div id='err-one'><img src="/err/errNodata.png" ></div>
        		<div id='err-two'><img src="/err/err2.png"></div>
			</div>
			<div class="sr-box-body-date">
				<div class="air-weather">
<%-- 					<p class="air-name">${companyName_session}机场</p> --%>					
					<div id="weather" style="font-size: 16px;">
							<p class="ptxt"></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/role.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/echarts.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/calendar.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/jquery.mousewheel.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/AdminLTE/myWeather.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/flightDynamic.js"></script>
</body>
</html>