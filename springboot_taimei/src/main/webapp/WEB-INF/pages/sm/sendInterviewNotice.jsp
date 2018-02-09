<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>面试通知短信</title>
</head>
<script type="text/javascript" src="js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/jquery1.8.3.js"></script>
<script type="text/javascript" src="js/js<%=request.getSession().getServletContext().getAttribute("versionn") %>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
function backToIndex(){
	location.href = 'indexd';
}
});
function sendInterviewNotice(){
	var phone = $('#phone').val();
	var name=$('#name').val();
	var time = $('#time').val();
	if(phone==''||name==''||time==''){
		alert('输入框不能为空');
		return;
	}
	$.ajax({
		url:'/sendInterviewNotice',
		data:{
			phone:phone,
			name:name,
			time:time
		},
		type:'post',
		success:function(data){
			if(data!=null){
				if(data.opResult=='3'){
					alert('参数中有空值');
				}else if(data.opResult=='2'){
					alert('服务器繁忙');
				}else if(data.opResult=='1'){
					alert('发送失败');
				}else if(data.opResult=='0'){
					alert('发送成功');
				}
			}else{
				alert('后台异常');
			}
		}
	});
}
function clear(){
	$('#phone').val('');
	$('#name').val('');
	$('#time').val('');
}
</script>
<body>
<table>
	<tr>
		<td colspan="2" align="center">面试通知</td>
	</tr>
	<tr>
		<td align="center">手机号：</td>
		<td><input id="phone" maxlength="11"/></td>
	</tr>
	<tr>
		<td align="center">姓名：</td>
		<td><input id="name" maxlength="20"/></td>
	</tr>
	<tr>
		<td align="center">时间：</td>
		<td><input id="time" maxlength="16" onclick="WdatePicker({dateFmt:'MM-dd HH:mm'})"/></td>
	</tr>
	<tr>
		<td align="center"><input type="button" value="发送" onclick="sendInterviewNotice()"/></td>
		<td align="center"><input type="button" value="清空" onclick="clear()"/></td>
	</tr>
</table>
</body>
</html>