<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>太美航空</title>
<style>
.aixuexi {
	height: 100px;
}

.aixuexi img {
	width: 100%;
	height: 100%;
}
</style>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
<%@include file="/WEB-INF/pages/common/commonNew.jsp"%>
<%@include file="/WEB-INF/pages/index/js/homePageJS.jsp"%>
<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</head>

<script type="text/javascript">
</script>
<body>
	<font size="2" color="gray"> <br> &emsp;当前位置:<a
		href="${pageContext.request.contextPath}/getFlash">首页</a></font>
	<br>
	<br>
	<div class="row">
		<div class="col-md-3">
			<div class="aixuexi" style="position: relative; height: 100px;">
				<img
					src="${pageContext.request.contextPath}/images/homePage/shouyi1.png">
				<div
					style="position: absolute; height: 100px; z-indent: 2; left: 15px; top: 10px;">
					<font id="zrsy" size="6px" color="white"></font> <br /> <font
						color="white">元</font>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="aixuexi" style="position: relative; height: 100px;">
				<img
					src="${pageContext.request.contextPath}/images/homePage/zbc1.png">
				<div
					style="position: absolute; height: 100px; z-indent: 2; left: 15px; top: 10px;">
					<font id="zrzbc" size="6px" color="white"></font> <br /> <font
						color="white">班</font>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="aixuexi" style="position: relative; height: 100px;">
				<img
					src="${pageContext.request.contextPath}/images/homePage/in1.png">
				<div
					style="position: absolute; height: 100px; z-indent: 2; left: 15px; top: 10px;">
					<font id="zrrg" size="6px" color="white"></font> <br /> <font
						color="white">人</font>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="aixuexi" style="position: relative; height: 100px;">
				<img
					src="${pageContext.request.contextPath}/images/homePage/out1.png">
				<div
					style="position: absolute; height: 100px; z-indent: 2; left: 15px; top: 10px;">
					<font id="zrcg" size="6px" color="white"></font> <br /> <font
						color="white">人</font>
				</div>
			</div>
		</div>
	</div>

	<div class="row" style="height: 20px;">
		<div class="col-md-3"></div>
		<div class="col-md-3"></div>
		<div class="col-md-3"></div>
		<div class="col-md-3"></div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div id="flowData" style="height: 400px;"></div>
		</div>
		<div class="col-md-6">
			<div id="airportMap" style="height: 400px;"></div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-3">
			<a id="kzl" href="#" onclick="zhibiaoOnclick(1)">客座率</a> &nbsp &nbsp
			<a id="zgl" href="#" style="color: black" onclick="zhibiaoOnclick(2)">座公里</a>&nbsp&nbsp
			<a id="zsr" href="#" style="color: black" onclick="zhibiaoOnclick(3)">总收入</a>&nbsp&nbsp
		</div>
		<div class="col-md-3">敬请期待</div>
		<div class="col-md-3"></div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div id="zhibiaoData" style="height: 400px;"></div>
		</div>
		<div class="col-md-6">
			<div id="2" class="aixuexi" style="height: 400px;">
				<img src="${pageContext.request.contextPath}/images/homePage/MH.jpg">
			</div>
		</div>
	</div>
</body>
</html>