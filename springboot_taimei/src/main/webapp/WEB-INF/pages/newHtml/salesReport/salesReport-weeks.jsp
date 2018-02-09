<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>销售报表</title>
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReport-weeks.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReports.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/calendar.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReports-new.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
<script type="text/javascript">
var title = '周销售报表';
</script>
</head>
<body>
	<div class="SD-hd-null">
		<p>暂无数据！<p>
	</div>
	<div class="sr-box">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
                    <li class="tltle-sel tltle-selI" id="_permissions_13">&#xe628;<span>销售报表</span></li>
                    <li class="tltle-sel" title="航线历史运营统计" id="_permissions_1"><a class="page-fun-change" href="/airline?flagpage=1">&#xe629;</a></li>
                    <li class="tltle-sel" title="销售动态" id="_permissions_5"><a class="page-fun-change" href="/buyTicketReport">&#xe624;</a></li>
                    <li class="tltle-sel" title="销售数据" id="_permissions_8"><a class="page-fun-change" href="/SalesData/accountCheck">&#xe688;</a></li>                    
                    <li class="tltle-sel" title="客票监控" id="_permissions_5"><a class="page-fun-change" href="/ticketMonitor">&#xe6a9;</a></li>
					<%-- <li class="tltle-sel tltle-selI" id="_permissions_13"> <span>销售报表</span> --%>
					<%-- </li>
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
			<!-- 航段选择器 -->
			<div class="sales-check">
			    <div class="lhead"  tag="off">
			        <p>航段选择（可复选）<span class="sales-check-open">&#xe67e;</span></p>
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
					<li onclick="changeSalesReport(1)" class="set-liset"><p>&#xe88c;</p>
						<div>周报</div></li>
					<li onclick="changeSalesReport(2)"><p>&#xe88d;</p>
						<div>月报</div></li>
					<li onclick="changeSalesReport(3)"><p>&#xe820;</p>
						<div>季报</div></li>
					<li onclick="changeSalesReport(4)"><p>&#xe679;</p>
						<div>年报</div></li>
				</ul>
			</div>
			
			
			
			
			
			
			
			<!-- 图表部分 -->
			<div class="sr-box-body-chart" >
				<!-- 图表主体区域 -->
				
			
				<div id="chart-wrap">
					<div class="sr-box-body-chart-income guest-table" >
						<p class="p-height">
							收入<span>（元）</span>
						</p>
						<div class="d-height sr-year-char3">
							<div class="information-head">
								<p>周营收合计</p>
								<div id="allIncome"></div>
							</div>
							<div class="information-head">
								<p>周小时营收</p>
								<div id="hourIncome"></div>
							</div>
							<div class="information-head">
								<p>周座公里收入</p>
								<div id="raskIncome"></div>
							</div>
						</div>
						<div id="income" class="graph-table"></div>
					</div>
					<div class="sr-box-body-chart-income guest-table">
						<p class="p-height">综合客座率</p>
						<div class="d-height sr-year-char3">
							<div class="information-head">
								<p>周客座率</p>
								<div id="allAvgPlf"></div>
							</div>
						</div>
						<div id="avg_set_ine" class="graph-table"></div>
					</div>
					<div class="sr-box-body-chart-income guest-table">
						<p class="p-height">平均折扣</p>
						<div class="d-height sr-year-char3">
							<div class="information-head">
								<p>周平均折扣</p>
								<div id="allAvgDis"></div>
							</div>
						</div>
						<div id="person_dct" class="graph-table"></div>
					</div>
					<div class="sr-box-body-chart-income guest-table">
						<p class="p-height">
							人数<span>（人）</span>
						</p>
						<div class="d-height sr-year-char3">
							<div class="information-head">
								<p>周合计总人数</p>
								<div id="allTotalPC"></div>
							</div>
						</div>
						<div id="person_avg" class="graph-table">
							<div id="p-number"></div>
							<div id="combined"></div>
						</div>
					</div>
				</div>
			</div>
			
			
            
            <!-- 表格部分 -->
            <div class="TMtable-container" id="TMtable">
                <div class="TMtable-wrap">
                    <div class="offsetContainer">
                        <div class="headArea">
                            <h1>销售周报</h1>
                            <p class="btn-export-out">导出表格</p>
                        </div>
                        <div class="tableArea">
                            <table>
                                <thead>
                                    <tr>
                                    </tr>
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
				<div class="membder-background addMemo-body">
					<div class="time-line">
						<ul id="income-set">
							<li class='checkBtn' id="goFltNbr"></li>
							<li class='checkBtn checkFlt'>往返</li>
							<li class='checkBtn' id="backFltNbr"></li>
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
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/calendar.js"></script>
	<script type="text/javascript" 
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/searchForm.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/salesReport-weeks.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
</body>
</html>