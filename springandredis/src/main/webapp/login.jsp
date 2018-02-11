<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
</head>
<SCRIPT type="text/javascript" src="/js/jquery-3.2.1.js"></SCRIPT>
<script type="text/javascript">
	function login(){
		var username = $('#username').val();
		var password = $('#password').val();
		$.ajax({
			url:'/login',
			type:'post',
			data:{
				username:username,
				password:password
			},
			success:function(data){
				if(data!=null&&data.opResult=='0')
					alert('添加成功');
			}
		});
	}
</script>
<body>
	<DIV style="height: 100px;width:100px">
		用户名：<INPUT type="text" id="username"/>
		密码：<INPUT type="password" id="password"/>
		<input type="button" value="登录" onclick="login()">
	</DIV>
</body>
</html>