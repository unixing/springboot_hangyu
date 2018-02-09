<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>历史运营统计</title>
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/publicsControls.css" />
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/airport-operating-history.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/spae.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">	
<link rel="stylesheet" type="text/css" media="all" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/bootstrap.css">
<link rel="stylesheet" type="text/css" media="all" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/line_historical/daterangepicker-bs3.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/line_historical/sy.css" rel="stylesheet/less">
<link href="http://cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css"	rel="stylesheet">
	
	
</head>
<body onselectstart="return false" unselectable="return false">
	<div class="lin-historical">
		<div class="TM-loading" id="TM-sloading"></div>
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel tltle-selI" id="_permissions_2">&#xe629;<span>历史运营统计</span></li>
					<li class="tltle-sel" title="航班动态" id="_permissions_6"><a class='page-fun-change'
						href="/airline_dynamic">&#xe665;</a></li>
					<li class="tltle-sel" title="时刻分布" id="_permissions_7"><a class='page-fun-change'
						href="/processTask">&#xe666;</a></li>
				</ul>
			</div>
			<div class="sr-box-head-inquire">
				<ul>
					<li>
						<span>起止时间</span><input type="text" id='reservation' readonly="readonly">
					</li>
					<li>
						<span>机场</span><input type="text" class="mid-width selectCity c-selectCity c-selectChange" id="cityChoice" placeholder="机场名">
					</li>
					<li>
						<span>航司</span><input type="text" class="mid-width" id="cpy_Nm" placeholder="如MU(可选)">
					</li>
					<li>
					</li>
					<li class="checkAArea">		
						<input id='exceptionData' checked="checked" type="checkbox">
							异常数据
						<input id='exceptionFlyData' checked="checked" type="checkbox">
							异常航班	
					</li>
					
				</ul>
				<div class="sr-box-head-btn" onclick='send()'>查询</div>
			</div>
		</div>
		<div class="lin-historical-body" style="height: 100%;">
			<div class="abnormalData_prompt"></div>
			<div class="lin-historical-body-navigation">
				<div class="Into set"></div>
				<div class="Out"></div>
				<div class="Total">汇总</div>
			</div>
			<div class="table_1" id="oldIO">
				<section id="spae_sector_cont" class="spae_sector_cont spae_sector_cont_c">
					<div class="exportItem1">
						<div class="exportExl" onclick="javascript:method1('ta')">
							<span>&#xe645;</span>&nbsp;&nbsp;导出表格
						</div>
					</div>
					<div class="exportTitle">
						<span id="_space_titles">
							<span>2017</span>年&nbsp;&nbsp;进港&nbsp;&nbsp;	
						</span>
						<div class="_export-table_box">
							<div class="_export-table _set-gB" onclick="segment()">汇总</div>
							<div class="_export-table" onclick="segment()">均班</div>
						</div>
					</div>
					<div>
						<ul class="lin_historical_er">
							<li>累计客流：0000元</li>
							<li>累计营收：0000元</li>
							<li>累计班次：0000元</li>
							<li>平均客座率：0000元</li>
						</ul>
					</div>
					<table id="ta" class="spae_table" cellpadding="0" cellspacing="0"></table>
				</section>
				<div class="lin-historical-body-box">
					<div id="inTrafficAnalysis"></div>
					<div id="analysis"></div>
					<div id="averageA"></div>
					<div id="kttr"></div>
					<div id="rp10"></div>
					<div id="tracpq10"></div>
					<div id="routes"></div>
					<div id="de-guest10"></div>
					<div id="deshift10"></div>
					<div id="sDepartment"></div>
				</div>
			</div>
			
			<!-- 航班运营模块 -->
			<div calss="table_1" id="newTotal"  style="display:none;">	
				<!-- 头部 -->
			    <div class="totalHead">
			        <p class="headp">
			            <span id="portName"></span>
			            &nbsp;&nbsp;     
			           	 航班运营情况       
			            <span id="timeRange"></span>
			        </p>
			        <div class="exportExl" onclick="javascript:method1('totalTable')">
			            <span>&#xe645;</span>
			            &nbsp;&nbsp;导出表格
			        </div>
			    </div>
			    <!-- 内容 -->
			    <div class="totalBody">
			        <table id="totalTable">
			        	<thead>
			            <tr>
			                <th>航班号</th>
			                <th>航线</th>
			                <th>航段</th>
			                <th>班次</th>
			                <th>团队客量(人)</th>
			                <th>散团总客量(人)</th>
			                <th>平均客座率(%)</th>
			                <th>团队收入(元)</th>
			                <th>散团总收入(元)</th>
			                <th>平均折扣(%)</th>
			                <th>平均座公里收入(元)</th>
			            </tr>
			            </thead>
			            <tbody id="totalTableBody">
			            </tbody>
			        </table>
			    </div>
			    
			    <!-- 固定表头 -->
			    <div class="totalBody copyTableWrap">
			        <table id="copyTable">
			        	<thead>
			            <tr>
			                <th>航班号</th>
			                <th>航线</th>
			                <th>航段</th>
			                <th>班次</th>
			                <th>团队客量(人)</th>
			                <th>散团总客量(人)</th>
			                <th>平均客座率(%)</th>
			                <th>团队收入(元)</th>
			                <th>散团总收入(元)</th>
			                <th>平均折扣(%)</th>
			                <th>平均座公里收入(元)</th>
			            </tr>
			            </thead>
			        </table>
			    </div>
			    
               <!-- 回到顶部 -->
               <div class="back-to-top" title="回到顶部"></div>
			    
			    
			</div>
			<div class="spae_sector" id="spae_sector" tag="graph">
    			<span class="spae_sector_text"></span>
			</div>
			
			<div class="reportErr">
				<div id='err-one'><img src="/err/errNodata.png" ></div>
        		<div id='err-two'><img src="/err/err2.png"></div>
			</div>
			
			<!-- 隐藏右侧日期和天气面板 -->
<!--   			<div class="lin-historical-time">
				<h3 class="right-title">机场记录运营情况统计</h3>
				<div class="sr-box-body-date-head">
					<ul class="set-years">
						<li onclick="lastyears()">&#xe663;</li>
						<li id="years"></li>
						<li onclick="nextyears()">&#xe664;</li>
					</ul>
				</div>
				<div class="sr-box-body-date-body"></div>
				<div class="air-weather">
					<div >
						<div id="weather" style="font-size: 16px;">
							<p class="ptxt"></p>
						</div>
					</div>
				</div>
			</div>
			 -->
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/role.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/echarts.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/AdminLTE/myWeather.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/airport-operating-history.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/jquery.mousewheel.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/exportTable.js"></script>
		
		
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/moment.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/line_historical/daterangepicker.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/line_historical/less.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/line_historical/month-chenge.js"></script>
</body>
</html>