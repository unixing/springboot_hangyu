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
</head>
<body>
	<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">数据管理</a>》<a
		href="#">飞机基础数据维护</a>
	</font>
	<br>
	<br>
	<aside>
		<table class="table">
			<tr>
				<td>
					<form id="systemLog-searchForm" method="post"
						enctype="application/x-www-form-urlencoded">
						机型: <input name="dpt_AirPt_Cd" id="dpt_AirPt_Cd"
							style="width: 98px" placeholder="机型"> <input
							type="button" class="btn btn-success" name="mySbumit" id="btn"
							value="查询" onclick="getFlyTypeData();"></input>
					</form>
				</td>
				<td></td>
			</tr>
		</table>
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<input type="button" class="btn btn-success" name="mySbumit"
						id="btn" value="新增" onclick="getFlyTypeData();"></input> <input
						type="button" class="btn btn-success" name="mySbumit" id="btn"
						value="修改" onclick="getFlyTypeData();"></input> <input
						type="button" class="btn btn-success" name="mySbumit" id="btn"
						value="删除" onclick="getFlyTypeData();"></input>
					<table id="flyTable">
					</table>
					<table id="updateData">
					</table>
				</div>
			</div>
		</section>
	</aside>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/basicData/js/flyBasicDataJS.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>