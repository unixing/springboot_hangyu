<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>销售报表</title>
<link
	href="/css/css<%=request.getSession().getServletContext()
					.getAttribute("versionn")%>/newPageCss/dateKJ.css"
	rel="stylesheet">
<link
	href="/css/css<%=request.getSession().getServletContext()
					.getAttribute("versionn")%>/newPageCss/salesReports-day.css"
	rel="stylesheet">
<link
	href="/css/css<%=request.getSession().getServletContext()
					.getAttribute("versionn")%>/newPageCss/coalesce.css"
	rel="stylesheet">
<link
	href="/css/css<%=request.getSession().getServletContext()
					.getAttribute("versionn")%>/newPageCss/salesReports-new.css"
	rel="stylesheet">
<link
	href="/css/css<%=request.getSession().getServletContext()
					.getAttribute("versionn")%>/TMcommon.css"
	rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext()
					.getAttribute("versionn")%>/newPageCss/spae.css">
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
				</ul>
			</div>

			<div class="sr-box-head-inquire">
				<div class="_set-place-duplexing">
					<div class="_set-name">航线</div>
					<div class="_set-begin">
						<input type="text" id="dpt_AirPt_Cd" placeholder="始发地"
							class="selectCity c-selectCity c-selectChange" maxlength="8">
					</div>
					<div class="_set-change">&#xe65d;</div>
					<div class="_set-begin">
						<input type="text" id="arrv_Airpt_Cd" placeholder="到达地"
							class="selectCity c-selectCity c-selectChange" maxlength="8">
					</div>
				</div>
				<div class="_set-place">
					<div class="_set-name">经停</div>
					<div class="_set-begin">
						<input type="text" id="pas_stp" placeholder="可选"
							class="selectCity c-selectCity c-selectChange-stp" maxlength="8">
					</div>
				</div>
				<div class="_set-flight">
					<div class="_set-name">航班号</div>
					<div id="flt_nbr_Count" class="_set-list-set"></div>
				</div>
				<div class="_set-choose">
					<input type="checkbox" id="isIncludeExceptionData" value="on"
						checked="checked">
					<div>异常数据</div>
				</div>
				<div class="_set-choose">
					<input type="checkbox" id="isIncludeExceptionFlyData" value="on"
						checked="checked">
					<div>异常航班</div>
				</div>
				<div class="_set-query">查询</div>
			</div>

		</div>
		<div class="abnormalData_prompt"></div>
		<div class="sr-box-body" style="display: flex;">
			<!-- 航段选择器 -->
			<div class="sales-check">
				<div class="lhead" tag="off">
					<p>
						航段选择（可复选）<span class="sales-check-open">&#xe67e;</span>
					</p>
				</div>
				<div class="sales-check-co">
					<ul class="leglist">
					</ul>
				</div>
				<span class="sales-check-close">&#xe67e;</span>
			</div>

			<div class="sr-box-body-report">
				<ul>
					<li onclick="changeSalesReport(0)" class="set-liset"><p>&#xe678;</p>
						<div>日报</div></li>
					<li onclick="changeSalesReport(1)"><p>&#xe88c;</p>
						<div>周报</div></li>
					<li onclick="changeSalesReport(2)"><p>&#xe88d;</p>
						<div>月报</div></li>
					<li onclick="changeSalesReport(3)"><p>&#xe820;</p>
						<div>季报</div></li>
					<li onclick="changeSalesReport(4)"><p>&#xe679;</p>
						<div>年报</div></li>
				</ul>
			</div>
			<div class="table_1">
				<section id="spae_sector_cont"
					class="spae_sector_cont spae_sector_cont_c">
					<div class="exportItem1">
						<div class="exportExl" onclick="expor()">
							<span>&#xe645;</span>&nbsp;&nbsp;导出表格
						</div>
					</div>
					<div class="exportTitle">
						<span>HU7305/06</span>&nbsp;&nbsp;2017年4月1日销售报表&nbsp;&nbsp;
					</div>
					<div>
						<ul class="lin_historical_er">
							<li>总飞时间：<span id="_space_time"></span></li>
							<li>航线：<span id="_space_hx"></span></li>
							<li><span id="_space_zwbj_j"></span>座位布局数：<span id="_space_zwbj_jnum"></span></li>
							<li><span id="_space_zwbj_c"></span>座位布局数：<span id="_space_zwbj_cnum"></span></li>
							<li>散团总营收：<span id="_space_stzys"></span></li>
							<li>小时营收：<span id="_space_ssys"></span></li>
							<li>综合客座率：<span id="_space_zhkzl"></span></li>
						</ul>
					</div>
					<table id="ta" class="spae_table" cellpadding="0" cellspacing="0"></table>
					<table id="ta1" class="spae_table" cellpadding="0" cellspacing="0"></table>
				</section>
				<div class="sr-box-body-chart">
					<!-- 图表主体区域 -->
					<div class="sr-box-body-chart-income Income-table tbl">
						<p class="p-height">
							收入信息<span>(元)</span>
						</p>
						<div class="d-height">
							<ul>
								<li>
									<p>往返合计</p>
									<div></div>
								</li>
								<li>
									<p id="qone"></p>
									<div></div>
								</li>
								<li>
									<p id="hone"></p>
									<div></div>
								</li>
								<li>
									<p>总飞时间：</p>
								</li>
								<li><input id="flyTime" placeholder="总飞行时间"
									onchange="exchate();" maxlength="5"> <input type="hidden" id="datee">
								</li>
							</ul>
						</div>
						<div class="graph-table">
							<canvas id="income" width="100%" height="100%"></canvas>
							<ul class="graph-table-inf">
								<li class="graph-table-inf-goandback" id="goandbackshouru">
									<div>
										散团收入:<span id="goAndBackstzsrData"></span>
									</div>
									<div>
										小时收入:<span id="goAndBackxssrData"></span>
									</div>
									<div>
										座公里收入:<span id="goAndBackset_Ktr_IneData"></span>
									</div>
								</li>
								<li class="graph-table-inf-go" id="goshouru">
									<div>
										散团收入:<span id="gostzsrData"></span>
									</div>
									<div>
										小时收入:<span id="goxssrData"></span>
									</div>
									<div>
										座公里收入:<span id="goset_Ktr_IneData"></span>
									</div>
								</li>
								<li class="graph-table-inf-back" id="backshouru">
									<div>
										散团收入:<span id="backstzsrData"></span>
									</div>
									<div>
										小时收入:<span id="backxssrData"></span>
									</div>
									<div>
										座公里收入:<span id="backset_Ktr_IneData"></span>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="sr-box-body-chart-income guest-table tbl">
						<p class="p-height">
							客座率<span>(%)</span>
						</p>
						<div class="d-height">
							<ul>
								<li>
									<p>往返综合</p>
									<div></div>
								</li>
								<li>
									<p id="qone2"></p>
									<div></div>
								</li>
								<li>
									<p id="hone2"></p>
									<div></div>
								</li>
							</ul>
						</div>
						<div class="graph-table">
							<canvas id="canvas" width="100%" height="100%"></canvas>
							<ul class="graph-table-inf graph-table-inf2">
								<li class="graph-table-inf-goandback" id="goandbackkz">
									<div id="goAndBackegs_Lod_FtsData"></div>
								</li>
								<li class="graph-table-inf-go" id="gokz">
									<div id="goegs_Lod_FtsData"></div>
								</li>
								<li class="graph-table-inf-back" id="backkz">
									<div id="backegs_Lod_FtsData"></div>
								</li>
							</ul>
						</div>
					</div>
					<div class="sr-box-body-chart-BrokenLine tbl">
						<div class="income-set">
							<div>客量/折扣</div>

							<ul id="income-set">
								<li class="Flight-num"></li>
								<li>往返</li>
								<li class="Flight-num"></li>
							</ul>
						</div>
						<div class="graph-table">
							<canvas id="graph-line" width="100%" height="100%"></canvas>
							<div id="matter"></div>
						</div>
					</div>
					<div class="tbl" id="dayliReport"></div>
				</div>
			</div>
			<div class="spae_sector" id="spae_sector" tag="graph"
				style="right: 302px">
				<span class="spae_sector_text"></span>
			</div>

			<div class="reportErr">
				<div id='err-one'>
					<img src="/err/errNodata.png">
				</div>
				<div id='err-two'>
					<img src="/err/err2.png">
				</div>
			</div>
			<div class="sr-box-body-date">
				<div class="membder-background addMemo-body">
					<div class="easyui-fullCalendar time-box"></div>
					<div class="time-line">
						<p>航班</p>
						<div class="time-line-fli"></div>
						<div class="change-line">
							<div class="change-line-f">
								<p></p>
								<div></div>
							</div>
							<div class="change-line-o">
								<p></p>
								<div></div>
							</div>
							<div class="change-line-l">
								<p></p>
								<div></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="SD-hd-null">
		<p>暂无数据！
		<p>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/role.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/echarts.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery.fullcalendar.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/salesReports-day.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/searchForm.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/city/city-data.js"></script>
	<script type="application/javascript"
	src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/exportTable.js"></script>
</body>
</html>