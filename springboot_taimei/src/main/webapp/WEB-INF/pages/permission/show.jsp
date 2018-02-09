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
	/*width:100%;
height:35px; */
	border-bottom: 1px solid #dedede;
}

ul li {
	list-style-type: none;
	float: left;
	line-height: 35px;
	text-indent: 10px;
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

.show_title {
	height: 27px;
	line-height: 22px;
	width: 100%;
	background: url(../images/bg01.gif) repeat-x left bottom;
	float: left;
	text-align: right;
	border-bottom: 1px solid #ccc;
}

.show_content ul {
	/* width:100%; */
	float: left;
	text-decoration: none;
	text-align: center;
	color: #000;
}

.mask {
	margin-top: 0 auto;
	width: 100%;
	height: 100%;
	background: #000;
	opacity: 0.6;
	top: 0;
	filter: alpha(opacity = 60);
	left: 0;
	display: block;
	position: absolute;
	z-index: 10;
	display: block;
}

.warp {
	width: 960px;
	height: 1100px;
	margin: 0 auto;
	position: relative;
	z-index: 11;
	display: block;
}

.show_content {
	width: 96%;
	height: 90%;
	margin: 40px auto;
	background: #fff;
	position: relative;
	z-index: 11;
}

.hide_btn {
	float: right;
	cursor: pointer;
	font-style: normal;
	padding-right: 10px;
	color: #fff;
	font-weight: bold;
}

.show_title b {
	display: block;
	float: left;
	padding-left: 27px;
	background: url(../images/bg02.gif) no-repeat 5px 4px;
	color: #fff;
}
</style>
<script type="text/javascript">
function selectAll(){
	$('#datalist :checkbox').attr('checked',$(obj).attr("checked"));
}
</script>
</head>
<body onload="setPermission()">
	<div class="mask"></div>
	<div class="warp">
		<div class="show_content"></div>
	</div>
	<script type="text/javascript">
function setPermission(){
	var row = $("#datalist").bootstrapTable('getSelections');
	if(row.length==1){
		var companyId = $(window.parent.document.body).find("#myCompanyList").val();
		$.ajax({
			url:'${pageContext.request.contextPath}/role/showPermission',
			data:{
				companyId:companyId,
				roleId:1
			},
			type:'post',
			success:function(data){
				if(data.opResult == '0'){
					showorhide(1);
					$(".show_content").empty();
					var operation = '<div class="show_title"><em class="hide_btn" onclick="showorhide(0)">关闭</em><b>菜单资源列表</b></div>';
					operation += '<div id="menuList">';
					if(data.menuList!=null&&data.menuList.length>0){
						var list = data.menuList;
						for(var i=0;i<list.length;i++){
							operation += '<table style="margin-left:100px;width:693px;"><tr>';
							if(list[i].type==1){
								if(list[i].chiledren!=null&&list[i].chiledren.length>0){
									var chiledren = list[i].chiledren;
									operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span>';
									for(var j = 0;j<chiledren.length;j++){
										operation += '<table style="margin-left:30px;"><tr>';
										if(chiledren[j].type==1){
											operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}else{
											operation += '<td><input style="width:13px;" name="menu" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span><td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span><td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}
										operation += '</td></tr></table>';
									}
									operation += '</td>';
								}else{
									operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span></td>';
								}
							}else{
								if(list[i].chiledren!=null&&list[i].chiledren.length>0){
									var chiledren = list[i].chiledren;
									operation += '<td><input style="width:13px;" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span>';
									for(var j = 0;j<chiledren.length;j++){
										operation += '<table style="margin-left:30px;"><tr>';
										if(chiledren[j].type==1){
											operation += '<td><input style="width:13px;" name="menu" checked="checked" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}else{
											operation += '<td><input style="width:13px;" name="menu" type="checkbox" value="'+chiledren[j].id+'" /><span>'+chiledren[j].name+'</span>';
											var resources = chiledren[j].resources;
											if(resources!=null&&resources.length>0){
												operation += '<table style="margin-left:60px;"><tr>';
												for(var k=0;k<resources.length;k++){
													if(k%6==0&&k!=0){
														if(resources[k].type==1){
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '</tr></table><table style="margin-left:60px;"><tr><td><input name="resource" style="width:13px;" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}else{
														if(resources[k].type==1){
															operation += '<td><input style="width:13px;" name="resource" checked="checked" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}else{
															operation += '<td><input style="width:13px;" name="resource" type="checkbox" value="'+resources[k].id+'" /><span>'+resources[k].name+'</span></td>';
														}
													}
												}
												operation += '</tr></table>';
											}
										}
										operation += '</td></tr></table>';
									}
								}else{
									operation += '<td><input style="width:13px;" name="menu" type="checkbox" value="'+list[i].id+'" /><span>'+list[i].name+'</span></td>';
								}
							}
							operation += '</tr></table>';
						}
						operation += '<table style="width:693px;"><tr text-algin="center"><td style="text-align: center;"><input class="btn btn-success" type="button" value="保存" onclick="test()"></td></tr></table></div>';
					}else{
						operation += '<table style="width:693px;"><tr text-algin="center"><td>没有可以显示的菜单</td></tr></table></div>';
					}
					$(".show_content").append(operation);
				}else{
					alert(data.message);
				}
			}
		});
	}else{
		alert('请选择一条数据');
	}
}

function test(){
	var obj= document.getElementById("menuList");
	var checkboxs = obj.getElementsByTagName("input");
	if(checkboxs.length>0){
		for(var i=0;i<checkboxs.length;i++){
			if(checkboxs[i].type=="checkbox"&&checkboxs[i].name=="menu"&&checkboxs[i].checked){
				alert("sb");
			}
		}
	}
}
</script>
	<%@include file="/WEB-INF/pages/common/common.jsp"%>
	<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
	<%@include file="/WEB-INF/pages/permission/js/common.jsp"%>
</body>
</html>