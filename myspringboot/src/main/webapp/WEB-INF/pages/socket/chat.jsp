<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="org.ldd.ssm.hangyu.utils.UserContext" %>
<%@ page import="org.ldd.ssm.hangyu.domain.Employee" %>
<html>
<head>
<style>
body {
    padding: 20px;
}
#message {
    height: 300px;
    border:1px solid;
    overflow: auto;
}
.red {
        color: #faddde;
        border: solid 1px #980c10;
        background: #d81b21;
        background: -webkit-gradient(linear, left top, left bottom, from(#ed1c24), to(#aa1317));
        background: -moz-linear-gradient(top,  #ed1c24,  #aa1317);
        filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#ed1c24', endColorstr='#aa1317');
}
.green {
        color: #FFFF00;
        border: solid 1px #980c10;
        background: #d81b21;
        background: -webkit-gradient(linear, left top, left bottom, from(#ed1c24), to(#aa1317));
        background: -moz-linear-gradient(top,  #ed1c24,  #aa1317);
        filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#ed1c24', endColorstr='#aa1317');
}
</style>
<%
	Employee emp = UserContext.getUser();
	Long name = emp.getId();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天页面</title>
<script type="text/javascript" src="/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/js/socket/socket.js"></script>
<script type="text/javascript">
	val = "<%=name%>";
</script>
</head>
<body onload="startWebSocket();">
<h1>欢迎来到聊天室</h1>
	登录状态：
	<span id="denglu" style="color: red;">正在登录</span>
	<br> 昵称：
	<span id="userName"></span>
	<br>
	<br> 请选择聊天对象：
	<span id="spanl"></span>
	<br> 聊天框：
	<div id="message"></div>
	<br>
	<br> 发送内容：
	<input type="text" id="writeMsg" value="嗨~"/>
	<input type="button" value="发送" onclick="sendMsg()"/>
	<input type="button" value="发送百度" onclick="iframeSend()"/>
</body>
</html>