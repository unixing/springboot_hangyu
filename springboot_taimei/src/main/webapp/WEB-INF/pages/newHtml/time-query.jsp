<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>时刻分布</title>
<link rel="stylesheet" type="text/css"
	href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/publicsControls.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesReports.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/time-query.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">
<link href="/err/ed.css" rel="stylesheet">
</head>
<body>
	<div class="sr-box">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel" title="历史运营统计" id="_permissions_2"><a
						class='page-fun-change' href="/outPort">&#xe629;</a></li>
					<li class="tltle-sel" title="航班动态" id="_permissions_6"><a class='page-fun-change'
						href="/airline_dynamic">&#xe665;</a></li>
					<li class="tltle-sel tltle-selI" id="_permissions_7">&#xe666;<span>时刻分布</span></li>
				</ul>
			</div>
			<div class="sr-box-head-inquire">
				<form id="excelForm"
					action="${pageContext.request.contextPath}/jxlExcel">
					<input type="hidden" id="dpt_AirPt_Cd" name="dpt_AirPt_Cd">
					<input type="hidden" id="Arrv_Airpt_Cd" name="Arrv_Airpt_Cd">
					<ul>
						<li style="position: relative"><span>始发点</span><input
							type="text" id="cityChoice" placeholder="始发地"
							class="selectCity c-selectCity c-selectChange"></li>
						<li style="position: relative"><span>到达地</span><input
							type="text" id="cityChoice2" placeholder="到达地"
							class="selectCity c-selectCity c-selectChange"></li>
						<!-- <li style="position: relative">
	                    <span>到达地</span><input type="text" id="cityChoice3" onfocus="WdatePicker({dateFmt: 'HH:mm'})" placeholder="到达地">
	                </li> -->
						<li style="position: relative"><span>航司</span><input
							type="text" id="airline" placeholder="如MU"></li>
						<li style="position: relative" class="drop-down"><span
							class="drop-down-show" id="goOrReturn">单程</span> <input
							type="hidden" name="goOrReturn" id="flightLine" />
							<ul class="drop-down-list">
								<li>单程</li>
								<li>往返</li>
							</ul></li>
					</ul>
					<input type="hidden" name="get_tim" id="collectTime" />
					<div class="sr-box-head-btn" onclick="send()">查询</div>
				</form>
			</div>
		</div>
		<div class="sr-box-body">
			<div class="TM-loading" id="TM-sloading"></div>
			<div class="time-q-box">
				<div class="_time-head">
					<ul class="_time-head-box">
						<li class="_time-head-title">班期时刻表</li>
						<li class="_time-head-collect">采集时间：
							<div id='get_tim'></div>
						</li>
						<!--  <li class="_time-head-update">最新更新数据</li> -->
						<li class="_time-head-set">
							<div class="_set" title="自定义展示项目">
								<div>&#xe646;</div>
								<ul class="_set-list">
									<li><input type="checkbox" checked="checked"> <span>始发地</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>到达地</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>航班号</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>机型</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>出发时间</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>出发机场</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>到达时间</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>到达机场</span>
									</li>
									<li><input type="checkbox" checked="checked"> <span>班期</span>
									</li>
								</ul>
							</div>
							<div class="_export-table" onclick="exportExcel()" title="导出时刻表">&#xe645;</div>
						</li>
					</ul>
				</div>
				<div class="err_time">
					<div id='err-one'><img src="/err/errNodata.png" ></div>
        			<div id='err-two'><img src="/err/err2.png"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/role.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/My97DatePicker/WdatePicker.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/airport-controls.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/jquery.mousewheel.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/time-query.js"></script>
</body>
</html>