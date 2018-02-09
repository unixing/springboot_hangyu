<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- jQuery 2.0.2 -->
<script src="${pageContext.request.contextPath}/js/jquery.min.js"
	type="text/javascript"></script>
<!-- jquery - UI -->
<script
	src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.min.js"
	type="text/javascript"></script>
<!-- bootstrap -->
<script src="${pageContext.request.contextPath}/js/bootstrap.js"
	type="text/javascript"></script>
<!-- web设计器--设计流程图(raphael) -->
<script src="${pageContext.request.contextPath}/js/raphael-min.js"
	type="text/javascript"></script>
<!-- 时间序列线图 -->
<script
	src="${pageContext.request.contextPath}/js/plugins/morris/morris.js"
	type="text/javascript"></script>
<!-- jQuery线状图插件Sparkline  -->
<script
	src="${pageContext.request.contextPath}/js/plugins/sparkline/jquery.sparkline.min.js"
	type="text/javascript"></script>
<!-- jvectormap 中国地图 -->
<script
	src="${pageContext.request.contextPath}/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"
	type="text/javascript"></script>
<!-- jvectormap 中国地图 -->
<script
	src="${pageContext.request.contextPath}/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"
	type="text/javascript"></script>
<!-- jquery fullcalendar 日历日程插件 -->
<script
	src="${pageContext.request.contextPath}/js/plugins/fullcalendar/fullcalendar.min.js"
	type="text/javascript"></script>
<!-- 关于knob.js进度插件 - -->
<script
	src="${pageContext.request.contextPath}/js/plugins/jqueryKnob/jquery.knob.js"
	type="text/javascript"></script>
<!-- 时间插件 -->
<script
	src="${pageContext.request.contextPath}/js/plugins/daterangepicker/daterangepicker.js"
	type="text/javascript"></script>
<!-- 基于 Bootstrap 框架实现的所见即所得的 HTML 编辑器 -->
<script
	src="${pageContext.request.contextPath}/js/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"
	type="text/javascript"></script>
<!-- iCheck自定义复选框和单选按钮组件(jQuery) -->
<script
	src="${pageContext.request.contextPath}/js/plugins/iCheck/icheck.min.js"
	type="text/javascript"></script>
<!-- 项目框架需要的js文件 -->
<script src="${pageContext.request.contextPath}/js/AdminLTE/app.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/js/AdminLTE/dashboard.js"
	type="text/javascript"></script>
<!-- 图标需要的js文件 -->
<script src="${pageContext.request.contextPath}/echarts/echarts.js"
	type="text/javascript"></script>
<!-- 扫描form表单的js文件 -->
<script src="${pageContext.request.contextPath}/js/searchForm.js"
	type="text/javascript"></script>
<!-- bootstrap表格需要的js文件 -->
<script src="${pageContext.request.contextPath}/js/bootstrap-table.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap-table-zh-CN.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap-table-export.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/tableExport.js"
	type="text/javascript"></script>
<!-- bootstrap表格编辑的js文件 -->
<script
	src="${pageContext.request.contextPath}/js/bootstrap-editable/bootstrap-editable.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap-editable/bootstrap-table-editable.js"
	type="text/javascript"></script>
<!-- 城市联动需要的js文件 -->
<script src="${pageContext.request.contextPath}/js/city/city-data.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/js/city/hzw-city-picker.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
function getCompanyList(){
	$(window.parent.document.body).find('#myCompanyList').empty();
	var option = '<option value="0">公司列表</option>';
	$.ajax({
		url:'${pageContext.request.contextPath}/company_getList',
		data:[],
		type:'post',
		async:false,
		success:function(data){
			if(data.opResult=="0"){
				if(data!=null&&data.list!=null&&data.list.length>0){
					var list=data.list;
					option = '';//重置下拉列表
					for(var i=0;i<list.length;i++){
						if(list[i].type==1){
							option += '<option selected="selected" value="'+list[i].id+'">'+list[i].cpyNm+'</option>';
						}else{
							option += '<option value="'+list[i].id+'">'+list[i].cpyNm+'</option>';
						}
					}
				}
				$(window.parent.document.body).find('#myCompanyList').append(option);
			}else{
				alert(data.message);
			}
		}
	});
}
function alert(txt,isSystem){
	if(isSystem){
		window.constructor.prototype.alert.call(window,txt);
		return;
	}
	var shield = document.createElement("DIV");
	shield.id = "shield";
	shield.style.position = "absolute";
	shield.style.left = "0px";
	shield.style.top = "0px";
	shield.style.width = "100%";
	shield.style.height = document.body.scrollHeight+"px";
	shield.style.background = "#333";
	shield.style.textAlign = "center";
	shield.style.zIndex = "10000";
	shield.style.filter = "alpha(opacity=30)";
	shield.style.MozOpacit=0.3;
	shield.style.opacity=0.3;
	var alertFram = document.createElement("DIV");
	alertFram.id="alertFram";
	alertFram.style.position = "fixed";
	alertFram.style.left = "55%";
	alertFram.style.top = "20%";
	alertFram.style.marginLeft = "-225px";
	alertFram.style.marginTop = "-75px";
	alertFram.style.width = "380px";
	alertFram.style.height = "200px";
	alertFram.style.background = "rgb(204, 204, 204)";
	alertFram.style.textAlign = "center";
	alertFram.style.lineHeight = "150px";
	alertFram.style.zIndex = "10001";
	strHtml = "<ul style=\"list-style:none;margin:0px;padding:0px;width:100%;\">\n";
	strHtml += " <li style=\"background:#3c8dbc;width:382px;text-align:left;padding-left:12px;font-size:16px;height:35px;line-height:35px;border:1px solid #3c8dbc;\">[航运收益系统提示]</li>\n";
	strHtml += " <li class=\"alert_li\" style=\"width:382px;background:#fff;text-align:center;font-size:14px;height:150px; border-left:1px solid #3c8dbc;border-right:1px solid #3c8dbc;\"><p class=\"alert_p\" style=\"width:380px; padding-top:55px; line-height:14px;\">"+txt+"</p></li>\n";
	strHtml += " <li style=\"background:#3c8dbc;width:382px;text-align:center;font-weight:bold;height:35px;line-height:35px; border:1px solid #3c8dbc;\"><input type=\"button\" value=\"确 定\" onclick=\"doOk()\"  style=\"width:80px; height:24px; background:#3c8dbc; border:none; border-radius:5px;\" /></li>\n";
	strHtml += "</ul>\n";
//	$(".alert_p").css("padding-top",(150-parseInt($(".alert_p").height()))/2+"px");
	alertFram.innerHTML = strHtml;
	document.body.appendChild(alertFram);
	document.body.appendChild(shield);
	var c = 0;
	this.doAlpha = function(){
		if (c++ > 20){clearInterval(ad);return 0;}
		shield.style.filter = "alpha(opacity="+c+");";
	}
	var ad = setInterval("doAlpha()",5);
	this.doOk = function(){
		$(alertFram).remove();
		$(shield).remove();
	};
	alertFram.focus();
	document.body.onselectstart = function(){return false;};
}
function closePage(id,functionName){
	$("#"+id).empty();
	var func = eval(functionName);
	new func();
}
</script>
<%-- <script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script> --%>