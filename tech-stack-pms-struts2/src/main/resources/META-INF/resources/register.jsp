<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>xx大学选课系统注册</title>
<link href="js/dwz/themes/css/login.css" rel="stylesheet" type="text/css" />
<script src="js/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<style type="text/css">
.info{font-size: 12px;color: red;margin-left: 80px;}
</style>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a style="font-size:25px">xx大学选课系统</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#" target="_blank"></a></li>
					</ul>
				</div>
				<h2 class="login_title" style="font-size:16px">学生注册</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm" >
				<form action="register_userSave.action" method="post">
					<p>
						<label>用户名：</label>
						<input name="loginName" type="text" style="width:140px;height:20px;" class="login_input" />
						<br/>
						<span class="info">${loginNameMsg}</span>
						<br/>
					</p>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;码：</label>
						<input name="loginPwd" type="password" style="width:140px;height:20px;" class="login_input" />
						<br/>
					</p>
					<div class="login_bar" style="margin-left:10px;">
						<input  type="submit" value="保存" />
						<a href="login_loginPage.action" style="color:blue" >登录</a>
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="js/dwz/themes/default/images/login_banner.jpg" /></div>
		</div>
	</div>
<script type="text/javascript">
</script>
</body>
</html>