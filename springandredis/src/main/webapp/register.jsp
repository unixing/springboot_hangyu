<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>注册</title>
</head>
<SCRIPT type="text/javascript" src="/js/jquery-3.2.1.js"></SCRIPT>
<SCRIPT type="text/javascript">
	function register(){
		var username = $('#username').val();
		var password = $('#password').val();
		$.ajax({
			url:'/register',
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
</SCRIPT>
<body>
	<DIV style="height: 100px;width:100px">
		用户名：<INPUT type="text" id="username"/>
		密码：<INPUT type="text" id="password"/>
		<input type="button" value="注册" onclick="register()">
	</DIV>
</body>
</html>