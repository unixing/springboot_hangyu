<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>销售动态</title>
<link rel="stylesheet" type="text/css" href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/dateKJ.css">
<link rel="stylesheet" type="text/css"href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/publicsControls.css" />
<link rel="stylesheet" type="text/css" media="all"href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/bootstrap.css">
<link rel="stylesheet" type="text/css" media="all"href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/salesDynamic/salesDynamic.css" />
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageCss/coalesce.css" rel="stylesheet">
<link href="/css/css<%=request.getSession().getServletContext().getAttribute("versionn") %>/TMcommon.css" rel="stylesheet">	
<link href="/err/ed.css" rel="stylesheet">
</head>
<body onselectstart="return false" unselectable="return false">
	<div class="lin-historical">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel" title="销售报表" id="_permissions_13"><a class='page-fun-change' href="/salesReport">&#xe628;</a></li>
					<li class="tltle-sel" title="航线历史收益统计" id="_permissions_1"><a class='page-fun-change' href="/airline?flagpage=1">&#xe629;</a></li>
					<li class="tltle-sel tltle-selI" id="_permissions_5">&#xe624;<span>销售动态</span></li>
					<li class='tltle-sel' title='销售数据' id="_permissions_8"><a class='page-fun-change' href='/SalesData/accountCheck'>&#xe688;</a></li>
                    <li class='tltle-sel' title='客票监控' id="_permissions_5"><a class='page-fun-change' href='/ticketMonitor'>&#xe6a9;</a></li>
				</ul>
			</div>
			<div class="sr-box-head-inquire">
				<ul>
					<li style="position: relative"><span>日期</span><input
						readonly="readonly" style="width: 200px;" type="text"
						id='startEndDate' placeholder="起止日期"></li>
					<li style="position: relative"><span>输入航班号查询</span><input
						style="width: 100px;" type="text" id="inputNum"
						placeholder="如MU1234"></li>
					<li style="position: relative">
						<div class="_set-flight">
							<div class="_set-name">航线</div>
							<div id="flt_nbr_Count" class="_set-list-set"></div>
						</div>
					</li>

				</ul>
				<div class="sr-box-head-btn">查询</div>
			</div>
		</div>
		<div class="lin-historical-body">
			<div class="TM-loading" id="TM-sloading" style="top: 39px;"></div>
			<div class="lin-historical-body-navigation">
				<div class="lin-historical-body-box-titles"></div>
			</div>
			<div class="lin-historical-body-box">
				<div class="lin-historical-body-bar"></div>
			</div>
			<div class="sr-box-body-date">
				<div class="membder-background addMemo-body">
					<div class="time-line time-lineNone">
						<p>航班</p>
						<div class="time-line-fli"></div>
						<div class="change-line">
							<div class="change-line-f1">
								<p></p>
								<div></div>
							</div>
							<div class="change-line-o1">
								<p></p>
								<div></div>
							</div>
							<div class="change-line-l1">
								<p></p>
								<div></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
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
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesReportJS/jquery.fullcalendar.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/war.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/jquery.mousewheel.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/coalesce.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/salesDynamic/salesDynamic.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/moment.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/newPageJs/daterangepicker.js"></script>
</body>
</html>