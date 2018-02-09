<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/css/login/login.css"
	type="text/css" rel="stylesheet">
<title>航班时刻表查询--登陆界面</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
	
		
	if (window != window.top) {
		//把子页面的地址，显示到顶级页面去
		top.location.href = location.href; 
	}

	$(function() {
		$(document.documentElement).bind("keydown", function(e) {
			if (e.keyCode == 13) { //回车键
				submitForm();
			}
		});
	});

	function resetForm() {
		$("#loginForm").form("clear");
	}
</script>
<body>
	<div class="login-text">
		<form id="loginForm" method="post"
			action="${pageContext.request.contextPath}/checkLogin">
			<table class="table-css">
				<tr>
					<td>用户名：</td>
					<td><input name="username" value="" type="text"
						placeholder="请输入用户名" onblur="check_user(this.value)" /></td>
					<td><span id="yhm">请输入用户名</span></td>
				</tr>
				<tr>
					<td>密&emsp;码：</td>
					<td><input name="password" value="" type="password"
						placeholder="请输入密码" onblur="check_pwd(this.value)" /></td>
					<td><span id="mm">请输入密码</span></td>
				</tr>
			</table>
			<input class="login-bt" type="submit" value="登录" /> <input
				class="login-bt" type="reset" value="取消" />
		</form>
	</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/login/login.js"></script>
<%@include file="/WEB-INF/pages/common/commonAjax.jsp"%>
</html>
