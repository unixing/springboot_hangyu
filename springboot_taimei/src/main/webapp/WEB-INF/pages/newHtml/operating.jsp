<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>共飞运营对比</title>
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/publicsControls.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/bootstrap.css">
<link rel="stylesheet" type="text/css" media="all"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/operating.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/spae.css" />
</head>

<body onselectstart="return false" unselectable="return false">
	<div class="lin-historical">
		<div class="lin_historical_tip"></div>
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel tltle-selI" id="_permissions_3">&#xe61a;<span>共飞运营对比</span></li>
					<li class="tltle-sel" title="航线历史收益统计" id="_permissions_1"><a
						class='page-fun-change' href="/airline">&#xe629;</a></li>
					<li class="tltle-sel" title="客源组成" id="_permissions_4"><a class='page-fun-change'
						href="/SourceDistribution">&#xe619;</a></li>
				</ul>
			</div>
			<div class="sr-box-head-inquire">
				<ul>
					<li style="position: relative"><span>航线</span>
						<input type="text" id='cityChoice' placeholder="始发地"
						class="selectCity c-selectCity c-selectChange" maxlength="8"></li>
					<li>&#xe65d;</li>
					<li style="position: relative">
						<input type="text" id='cityChoice2' placeholder="到达地"
						class="selectCity c-selectCity c-selectChange" maxlength="8"></li>
					<li style="position: relative"><span>日期</span>
						<input type="text" readonly="readonly" style="width: 200px;"
						id='startEndDate' placeholder="起止日期"></li>
					<li style="position: relative"><span>经停地</span>
						<input type="text" id='cityChoice3' placeholder="(可选)"
						class="selectCity c-selectCity c-selectChange-stp" maxlength="8"></li>
				</ul>
				<div class="sr-inquire-set">
					<input id='exceptionData' type="checkbox" checked="checked" >含异常数据
				</div>
				<div class="sr-inquire-set">
					<input id='exceptionData1' type="checkbox" checked="checked" >含异常航班
				</div>
				<div class="sr-inquire-set">
					<input id='isnon-stop' checked="checked" type="checkbox">直飞
				</div>
				<div id="pasDiv" class="sr-inquire-set">
					<input id='ishas-stop' checked="checked" type="checkbox">经停
				</div>
				<div class="sr-box-head-btn" onclick='send()'>查询</div>
			</div>
		</div>
		<div class="lin-historical-body">		
			<div class="TM-loading" id="TM-sloading"></div>  	
			<div class="lin-historical-body-navigation">
			</div>			
			<div class="TMlackdata"><p><span>&#xe64a;</span>当前查询条件下，存在连续30天以上的数据缺失</p></div>
			<div class="table_1">
				<section id="spae_sector_cont" class="spae_sector_cont spae_sector_cont_c">
					<div class="exportItem1">
						<div class="exportExl" onclick="javascript:method1('ta')">
							<span>&#xe645;</span>&nbsp;&nbsp;导出表格
						</div>
					</div>
					<div class="exportTitle"><span id="_table_year_s">2017</span>年&nbsp;&nbsp;<span id="_table_month_s">7</span>月-<span id="_table_year_e">2017</span>年&nbsp;&nbsp;<span id="_table_month_e">12</span>月&nbsp;&nbsp;共飞对比</div>
					<div class="SD-view-t-label" id="SD-table-head"></div>
					<table id="ta" class="spae_table" cellpadding="0" cellspacing="0"></table>
				</section>
				<div class="lin-historical-body-box">
					<div id="container1"></div>
					<div id="container2"></div>
					<div id="container3"></div>
					<div id="container4"></div>		
				</div>
			</div>
			<div class="spae_sector" id="spae_sector" tag="graph">
    			<span class="spae_sector_text">&#xe750;</span>
			</div>
			
		</div>
		<div class="err">
			<div id='err-one'><img src="/err/errNodata.png" ></div>
        	<div id='err-two'><img src="/err/err2.png"></div>
		</div>
		<div class="SD-hd-null">
			<p>暂无该航线数据！<p>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/role.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/echarts.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"
		type="text/javascript"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/operating.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/jquery.mousewheel.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/moment.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/daterangepicker.js"></script>

	
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/exportTable.js"></script>
</body>
</html>