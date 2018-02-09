<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>及时通讯</title>
<script type="text/javascript" src="/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
	function login(){
		var data = {
				username:$("[name=username]").val(),
				password:$("[name=password]").val()
		}
		$.ajax({
			url:'/login',
			data:data,
			success:function(data){
				if(data.success){
					window.location.href = "/chat";
				}else{
					window.location.href = "/login.jsp";
				}
			}
		})
	}
</script>
</head>
<body>
	<div >
		<form id="loginForm">
			<table >
				<tr>
					<td>用户名：</td>
					<td><input name="username" value="admin" type="text" placeholder="请输入用户名" /></td>
					<td><span id="yhm">请输入用户名</span></td>
				</tr>
				<tr>
					<td>密&emsp;码：</td>
					<td><input name="password" value="admin" type="password" placeholder="请输入密码"  /></td>
					<td><span id="mm">请输入密码</span></td>
				</tr>
			</table>
				<input  type="button" onclick="login()"  value="登录" />
				<input  type="reset" value="取消" />
		</form>
	</div>
</body>
</html>