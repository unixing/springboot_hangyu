<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>客源组成</title>
<link rel="stylesheet" type="text/css" media="all"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/bootstrap.css">
<link rel="stylesheet" type="text/css" media="all"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/daterangepicker-bs3.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/customers.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReports-new.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
</head>
<body>
	<div class="sr-box">
		<div class="TM-loading" id="TM-sloading" style="top:40px;"></div>	
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel " title="共飞运营对比" id="_permissions_3"><a
						class='page-fun-change' href="/totalFlyAnalysis">&#xe61a;</a></li>
					<li class="tltle-sel" title="航线历史收益统计" id="_permissions_1"><a
						class='page-fun-change' href="/airline">&#xe629;</a></li>
					<li class="tltle-sel tltle-selI" id="_permissions_4">&#xe619;客源组成</li>
				</ul>
			</div>
			<div class="sr-box-head-inquire" id='sr-box-head-inquire'>
				<div class="_set-place-duplexing">
					<div class="_set-name">航线</div>
					<div class="_set-begin">
						<input type="text" id='cityChoice' placeholder="始发地"
							class="selectCity c-selectCity c-selectChange" maxlength="8">
					</div>
					<div class="_set-change">&#xe65d;</div>
					<div class="_set-begin">
						<input type="text" id='cityChoice2' placeholder="到达地"
							class="selectCity c-selectCity c-selectChange" maxlength="8">
					</div>
				</div>
				<div class="_set-time">
					<div class="_set-name">起止时间</div>
					<div class="_set-name-b">
						<input type="text" placeholder="时间" id='startEndDate' maxlength="16">
					</div>
				</div>
				<div class="_set-place">
					<div class="_set-name">经停</div>
					<div class="_set-begin">
						<input type="text" id='cityChoice3' placeholder="可选"
							class="selectCity c-selectCity c-selectChange-stp" maxlength="8">
					</div>
				</div>

				<div class="_set-flight">
					<div class="_set-name" id='flt_nbr_Count'>航班号</div>
					<div class="_set-list-set"></div>
				</div>
				<div class="_set-choose">
					<input id='isnon-stop' type="checkbox" checked="checked">
					<div>直飞</div>
				</div>
				<div class="_set-choose" id="divpas">
					<input id='ishas-stop' type="checkbox" checked="checked">
					<div>经停</div>
				</div>
				<div class="_set-query">查询</div>
			</div>
		</div>
		<div class="_customers">	
			<div class="_customers-nav"></div>
			<div class="_customers-box">
				<div class="_customers-box-c">
					<div id="sample1" class="sample"></div>
					<div id="sample2" class="sample"></div>
					<div class="sample fullwidth">
						<div id="sample3"></div>
						<div id="sample4"></div>
					</div>
				</div>
			</div>
			
		<div class="cus-closechart">
		<span>&#xe67e;</span>
		</div>
		</div>
		<!-- 改版热点图 lh 2017-4-6 -->
		<div class="cus-map" id="cus_map">
			<div class="heatmap" id="cus_heatmap"></div>
			<div class="cus_heatzoom">
       			<div>&#xe60d;</div>
      			<div>&#xe60e;</div>
			</div>
		</div>
		
		
		<!-- 无数据提示 -->
		<div class="err">
			<div id='err-one'><img src="/err/errNodata.png" ></div>
        	<div id='err-two'><img src="/err/err2.png"></div>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/role.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/echarts.js"></script>
<script type="text/javascript" src="/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/world.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/jquery.mousewheel.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
	<script type="text/javascript"
		src=".${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/customers.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/moment.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/daterangepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"></script>

</body>
</html>