<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<%@include file="/WEB-INF/pages/common/css.jsp"%>
<link
	href="${pageContext.request.contextPath}/css/bootstrap-datatime/bootstrap-datetimepicker.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<font size="2" color="gray"> <br> &emsp;当前位置:<a href="#">数据管理</a>》<a
		href="#">疑惑数据查询</a>
	</font>
	<br>
	<br>
	<aside>
		[以下所有选项皆为可选选项]
		<form id="dataCheck">
			开始日期: <input type='text' name="startDate" class="Wdate"
				onclick="WdatePicker({maxDate:new Date()})" id='datetimepicker4' />
			结束日期: <input type='text' name="endDate" class="Wdate"
				onclick="WdatePicker({maxDate:new Date()})" id='datetimepicker4' /><br>
			始发地: <input name="dpt_AirPt_Cd" id="dpt_AirPt_Cd" style="width: 58px"
				placeholder="如:北京"> 到达地: <input name="arrv_Airpt_Cd"
				id="arrv_Airpt_Cd" style="width: 58px" placeholder="如:上海">
			航班号: <input name="flt_Nbr" id="flt_Nbr" style="width: 58px"
				placeholder="MU8888"> 公司: <input name="cpy_Nm"
				style="width: 58px"> 航线: <input name="flt_Rte_Cd"
				style="width: 58px"> 座位总数: <input name="tal_Nbr_Set"
				style="width: 58px"> 每班旅客: <input name="pgs_Per_Cls"
				style="width: 58px"> 团队人数: <input name="grp_Nbr"
				style="width: 58px"> 每班团队: <input name="ech_Cls_Grp"
				style="width: 58px"> 每班收入: <input name="tal_Nbr"
				style="width: 58px"> 座公里收入: <input name="set_Ktr_Ine"
				style="width: 58px"><br> 两舱比例: <input
				name="two_Tak_Ppt" style="width: 58px"> 全价比例: <input
				name="ful_Pce_Ppt" style="width: 58px"> 5折比例: <input
				name="nne_Dnt_Ppt" style="width: 58px"> 8.5折比例: <input
				name="eht_Five_Dnt_Ppt" style="width: 58px"> 8折比例: <input
				name="eht_Dnt_Ppt" style="width: 58px"> 7.5折比例: <input
				name="sen_Five_Dnt_Ppt" style="width: 58px"> 6.5折比例: <input
				name="six_Five_Dnt_Ppt" style="width: 58px"> 5.5折比例: <input
				name="fve_Fve_Dnt_Ppt" style="width: 58px"> 5折比例: <input
				name="fve_Dnt_Ppt" style="width: 58px"> 4.5折比例: <input
				name="fur_Fve_Dnt_Ppt" style="width: 58px"> 4折比例: <input
				name="fur_Dnt_Ppt" style="width: 58px"> 特殊舱比例: <input
				name="sal_Tak_Ppt" style="width: 58px"> <br> R舱比例: <input
				name="r_Tak_Ppt" style="width: 58px"> U舱比例: <input
				name="u_Tak_Ppt" style="width: 58px"> I舱比例: <input
				name="i_Tak_Ppt" style="width: 58px"> Z舱比例: <input
				name="z_Tak_Ppt" style="width: 58px"> E舱比例: <input
				name="e_Tak_Ppt" style="width: 58px"> A舱比例: <input
				name="a_Tak_Ppt" style="width: 58px"> O舱比例: <input
				name="o_Tak_Ppt" style="width: 58px"> S舱比例: <input
				name="s_Tak_Ppt" style="width: 58px"> H舱比例: <input
				name="h_Tak_Ppt" style="width: 58px"> X舱比例: <input
				name="x_Tak_Ppt" style="width: 58px"> 儿童: <input
				name="children" style="width: 58px"> <input type="button"
				class="btn btn-success" name="mySbumit" id="btn" value="查询"
				onclick="getData();"></input>
		</form>

		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<table id="MyTimeTable">
					</table>
				</div>
			</div>
		</section>
	</aside>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/dataCheck/js/dataCheck.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</body>
</html>