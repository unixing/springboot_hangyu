<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>太美航空</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
<style type="text/css">
.clear {
	clear: both;
}

ul {
	width: 100%;
}

ul li {
	list-style-type: none;
	line-height: 35px;
}

ul li input {
	line-height: 22px;
	width: 150px;
	border: 1px solid #dedede;
}

ul li span {
	font-size: 16px;
	font-weight: bold;
}
</style>
<script type="text/javascript">
function selectAll(){
	$('#datalist :checkbox').attr('checked',$(obj).attr("checked"));
}
</script>
</head>
<body onload="search()">
	<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">航线网络规划</a>》<a
		href="#">航线航班管理</a>
	</font>
	<br>
	<br>
	<aside>
		<table class="table">
			<tr>
				<td><input type="button" class="btn btn-success" value="添加"
					onclick="addshow();"> <input type="button"
					class="btn btn-success" value="修改" onclick="updateshow();">
					<input type="button" class="btn btn-success" value="批量删除"
					onclick="batchdel();"> <input type="button"
					class="btn btn-success" value="停飞" onclick="groundOrGoAround(2);">
					<input type="button" class="btn btn-success" value="复飞"
					onclick="groundOrGoAround(1);"></td>
				<td>
					<form id="systemLog-searchForm" method="post"
						enctype="application/x-www-form-urlencoded">
						机场名称: <input name="airport" style="width: 120px"
							placeholder="请输入机场名称"> <input type="button"
							class="btn btn-success" id="btn" value="查询" onclick="search();"></input>
					</form>
				</td>
			</tr>
		</table>
		<section class="content">
			<div class="row">
				<div class="col-md-12" id="datalist"></div>
			</div>
		</section>
	</aside>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/flight/js/flightinfo.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>