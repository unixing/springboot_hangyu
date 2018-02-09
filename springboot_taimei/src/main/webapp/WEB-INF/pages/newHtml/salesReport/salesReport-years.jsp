<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>销售报表</title>
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReport-years.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReports.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReports-new.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
</head>
<body>
	<div class="sr-box">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
                    <li class="tltle-sel tltle-selI" id="_permissions_13">&#xe628;<span>销售报表</span></li>
                    <li class="tltle-sel" title="航线历史运营统计" id="_permissions_1"><a class="page-fun-change" href="/airline?flagpage=1">&#xe629;</a></li>
                    <li class="tltle-sel" title="销售动态" id="_permissions_5"><a class="page-fun-change" href="/buyTicketReport">&#xe624;</a></li>
                    <li class="tltle-sel" title="销售数据" id="_permissions_8"><a class="page-fun-change" href="/SalesData/accountCheck">&#xe688;</a></li>                    
                    <li class="tltle-sel" title="客票监控" id="_permissions_5"><a class="page-fun-change" href="/ticketMonitor">&#xe6a9;</a></li>

					<%-- <li class="tltle-sel tltle-selI" id="_permissions_13"> <span>销售报表</span>
					</li>
					<li class="tltle-sel" title="航线历史运营统计" id="_permissions_1"><a
						class='page-fun-change' href="/airline?flagpage=1">&#xe629;</a></li>
					<li class="tltle-sel" title="销售动态" id="_permissions_5"><a class='page-fun-change'
						href="/buyTicketReport">&#xe624;</a></li>
					<li class='tltle-sel' title='销售数据' id="_permissions_8"><a class='page-fun-change'
						href='/SalesData/accountCheck'>&#xe688;</a></li> --%>
				</ul>
			</div>
			<div class="sr-box-head-inquire">
				<div class="_set-place-duplexing">
					<div class="_set-name">航线</div>
					<div class="_set-begin">
						<input type="text" id="cityChoice" placeholder="始发地"
							class="selectCity c-selectCity c-selectChange" maxlength="8">
					</div>
					<div class="_set-change">&#xe65d;</div>
					<div class="_set-begin">
						<input type="text" id="cityChoice2" placeholder="到达地"
							class="selectCity c-selectCity c-selectChange" maxlength="8">
					</div>
				</div>
				<div class="_set-place">
					<div class="_set-name">经停</div>
					<div class="_set-begin">
						<input type="text" id="cityChoice3" placeholder="可选"
							class="selectCity c-selectCity c-selectChange-stp" maxlength="8">
					</div>
				</div>
				<div class="_set-flight">
					<div class="_set-name">航班号</div>
					<div id="flt_nbr_Count" class="_set-list-set"></div>
				</div>
				<div class="_set-choose">
					<input type="checkbox" id="exceptionData" value="on"
						checked="checked">
					<div>异常数据</div>
				</div>
				<div class="_set-choose">
					<input type="checkbox" id="exceptionFlyData" value="on"
						checked="checked">
					<div>异常航班</div>
				</div>
				<div class="_set-query">查询</div>
			</div>
		</div>
		<div class="abnormalData_prompt"></div>
		<div class="sr-box-body">
				<div class="TM-loading" id="TM-sloading" style="width: 80.7%;height: 100%;left: 50px;top: 40px;"></div>
				<!-- 航段选择器 -->
 				<div class="sales-check">
				    <div class="lhead"  tag="off">
				        <p>航段选择<span class="sales-check-open">&#xe67e;</span></p>
				    </div>
				    <div class="sales-check-co">
				    	<ul class="leglist">
				    	</ul>	
				    </div>
				    <span class="sales-check-close">&#xe67e;</span>
				</div>
			<div class="sr-box-body-report">
				<ul>
					<li onclick="changeSalesReport(0)"><p>&#xe678;</p>
						<div>日报</div></li>
					<li onclick="changeSalesReport(1)"><p>&#xe88c;</p>
						<div>周报</div></li>
					<li onclick="changeSalesReport(2)"><p>&#xe88d;</p>
						<div>月报</div></li>
					<li onclick="changeSalesReport(3)"><p>&#xe820;</p>
						<div>季报</div></li>
					<li onclick="changeSalesReport(4)" class="set-liset"><p>&#xe679;</p>
						<div>年报</div></li>
				</ul>
			</div>
			<div class="sr-box-body-chart">
				<div class="sr-box-body-chart-income guest-table">
					<p class="p-height">
						收入<span>（元）</span>
					</p>
					<div class="d-height sr-year-char3">
						<div class="information-head">
							<p>年营收合计</p>
							<div id="yearIncome"></div>
						</div>
						<div class="information-head">
							<p>均班收入</p>
							<div id="classAvgInner"></div>
						</div>
						<div class="information-head">
							<p>年小时营收</p>
							<div id="hourIncome"></div>
						</div>
						<div class="information-head">
							<p>年座公里收入</p>
							<div id="raskIncome"></div>
						</div>
					</div>
					<div id="income" class="graph-table"></div>
				</div>
				<div class="sr-box-body-chart-income guest-table">
					<p class="p-height">平均客座率</p>
					<div class="d-height sr-year-char3">
						<div class="information-head">
							<p>年客座率</p>
							<div id="yearAvgPlf"></div>
						</div>
					</div>
					<div id="avg_set_ine" class="graph-table"></div>
				</div>
				<div class="sr-box-body-chart-income guest-table">
					<p class="p-height">总人数</p>
					<div class="d-height sr-year-char3">
						<div class="information-head">
							<p>年总人数/总班数</p>
							<div id="yearTotalPC"></div>
						</div>
					</div>
					<div id="person_dct" class="graph-table"></div>
				</div>
				<div class="sr-box-body-chart-income guest-table">
					<p class="p-height">均班人数</p>
					<div class="d-height sr-year-char3">
						<div class="information-head">
							<p>年均班人数/总班数</p>
							<div id="yearAvgPC"></div>
						</div>
					</div>
					<div id="person_avg" class="graph-table"></div>
				</div>
			</div>
			            
            <!-- 表格部分 -->
            <div class="TMtable-container" id="TMtable">
                <div class="TMtable-wrap">
                    <div class="offsetContainer">
                        <div class="headArea">
                            <h1>GJ8858/60 2017年2月27日-3月5日销售周报</h1>
                            <p class="btn-export-out">导出表格</p>
                        </div>
                        <div class="tableArea">
                            <table id="h-table">
                                <thead><tr><th>年度</th><th>散团总收入（元）</th><th>均班营收（元）</th><th>散团总人数（人）</th><th>均班人数（人）</th><th>平均客座率（%）</th><th>平均座公里收入（元）</th></tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            <table id="lonhon">
                                <thead><tr><th>日期</th><th>航段</th><th>客量（人）</th><th>均班客量（人）</th><th>综合客座率（%）</th><th>营收（元）</th><th>均班营收（元）</th><th>座公里收入（元）</th><th>小时收入（元）</th><th>平均折扣（%）</th><th>平均团队折扣（%）</th></tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    
                </div>
            </div>      
			
			<!-- 图表切换 -->
			<div class="chart-changer" state="chart"><span>&#xe750;</span></div>
			
			<div class="reportErr">
				<div id='err-one'><img src="/err/errNodata.png" ></div>
        		<div id='err-two'><img src="/err/err2.png"></div>
			</div>
			<div class="sr-box-body-date">
				<div class="sr-box-body-date-head">
					<ul class="set-years">
						<li onclick="lastyears()">&#xe648;</li>
						<li id="years"></li>
						<li onclick="nextyears()">&#xe648;</li>
					</ul>
				</div>
				<div class="sr-box-body-date-body"></div>
				<div class="sr-box-body-date-footer">
					<ul id="income-set">
						<li class="checkBtn" id="goFltNbr"></li>
						<li class="checkBtn checkFlt">往返</li>
						<li class="checkBtn" id="backFltNbr"></li>
					</ul>
					<div class="bc"></div>
					<div class="change-line">
						<div class="change-line-f">
							<p id="departureItia"></p>
							<div id="departureCity"></div>
						</div>
						<div class="change-line-o">
							<p id="passItia"></p>
							<div id="passCity"></div>
						</div>
						<div class="change-line-l">
							<p id="arriveItia"></p>
							<div id="arriveCity"></div>
						</div>
					</div>
				</div>
			</div>
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
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery.fullcalendar.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/salesReport-years.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/searchForm.js"></script>
</body>
</html>