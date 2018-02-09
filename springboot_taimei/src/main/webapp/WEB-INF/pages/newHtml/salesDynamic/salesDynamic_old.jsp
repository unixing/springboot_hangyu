<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>销售动态</title>
<link href="/css/newPageCss/dateKJ.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/css/newPageCss/publicsControls.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="/css/newPageCss/bootstrap.css">
<link rel="stylesheet" type="text/css" media="all"
	href="/css/newPageCss/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
	href="/css/newPageCss/salesDynamic/salesDynamic.css" />
</head>
<body>
	<div class="lin-historical">
		<div class="sr-box-head">
			<div class="sr-box-head-classify">
				<ul>
					<li class="tltle-sel">&#xe61b;</li>
					<li class="tltle-sel">&#xe62a;</li>
					<li class="tltle-sel tltle-selI">&#xe629;<span>销售动态</span></li>
					<li class="tltle-sel">&#xe62b;</li>
				</ul>
			</div>
			<div class="sr-box-head-inquire">
				<ul>
					<li style="position: relative"><span>日期</span><input
						style="width: 250px;" type="text" id='startEndDate'
						placeholder="起止日期"></li>
					<li style="position: relative"><span>输入航班号查询</span><input
						style="width: 200px;" type="text" id="inputNum"
						placeholder="如MU1234"></li>
				</ul>
				<div class="sr-box-head-btn">查询</div>
			</div>
		</div>
		<div class="lin-historical-body">
			<div class="lin-historical-body-navigation"></div>
			<div class="lin-historical-body-box">
				<div class="lin-historical-body-box-titles">
					<div>起飞日期</div>
					<div>客座率/客量</div>
				</div>
				<div class="element-lines-box"></div>
			</div>
			<div class="sr-box-body-date">
				<div class="membder-background addMemo-body">
					<div class="choose">
						<h3 class="choose-title">&nbsp;&nbsp;&nbsp;销售动态</h3>
						<!-- <div class="choose-set">
                        <div>
                            <div class="choose-font-top choose-year-top">&#xe61e;</div>
                            <div class="choose-time achieve-year">2016</div>
                            <div class="choose-font-bottom choose-year-bottom">&#xe61e;</div>
                        </div>
                        <div>年</div>
                        <div>
                            <div class="choose-font-top choose-month-top">&#xe61e;</div>
                            <div class="choose-time achieve-month">9</div>
                            <div class="choose-font-bottom choose-month-bottom">&#xe61e;</div>
                        </div>
                    </div> -->
					</div>
					<!-- <div class="calendar"></div>  -->
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

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/newPageJs/salesReportJS/jquery1.8.3.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/newPageJs/salesReportJS/jquery.easyui.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/newPageJs/salesReportJS/jquery.fullcalendar.js"></script>
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/js/newPageJs/salesDynamic/salesDynamic.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/newPageJs/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/newPageJs/moment.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/newPageJs/daterangepicker.js"></script>

</body>
</html>