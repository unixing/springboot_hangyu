<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/login/login.css" rel="stylesheet">
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<link rel="icon" href="/img/icon/logo.png">
<script src="/js/jquery1.8.3.js"></script>
</head>
<body>
	<div class="log-box">
		<div class="log-login">
			版本号:2.2.3.1118_beta
			<div class="log-login-box">
				<div class="log-login-switch">
					<div>账号登录</div>
					<div>扫码登录</div>
				</div>
				<div class="log-login-user">
					<div>
						<p class="log-user">账号</p>
						<div class="log-user-input">
							<input id="log-user" type="text" placeholder="请输入账号名" />
							<!--  <div>账号不存在</div> -->
						</div>
						<p class="log-pas">密码</p>
						<div class="log-pas-input">
							<input id="log-pas" type="password" placeholder="请输入密码" />
							<!--  <div>密码错误</div> -->
						</div>
						<div class="log-remPas">
							<input type="checkbox" /> 记住账号
						</div>
						<div class="log-login-btn">登录</div>
						<hr class="log-login-line">
						<p class="log-login-forget">忘记密码</p>

					</div>
					<div>
						<img src="./../images/login/login.png" alt="微信二维码">
						<p>微信扫一扫登录</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/js/login/login.js"></script>

</body>
</html>