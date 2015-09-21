<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>tech-stack-sms2</title>
<link href="<%=basePath%>js/dwz/themes/css/login.css" rel="stylesheet" type="text/css" />  
<script src="<%=basePath%>js/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<style type="text/css">
.info{font-size: 12px;color: red;margin-left: 80px;}
</style>
</head>
<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a style="font-size:25px">tech-stack-sms2</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#" target="_blank"></a></li>
					</ul>
				</div>
				<h2 class="login_title" style="font-size:16px">用户登录</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm" >
				<form action="<%=basePath%>login_loginPage.action" method="post">
					<p class="info">${loginInfo}</p>
					<p>
						<label>用户名：</label>
						<input name="username" type="text" style="width:140px;height:20px;" class="login_input" />
						<br/>
						<span class="info">${loginNameMsg}</span>
					</p>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;码：</label>
						<input name="password" type="password" style="width:140px;height:20px;" class="login_input" />
						<br/>
						<span class="info">${loginPwdMsg}</span>
					</p>
					<div class="login_bar" style="margin-left:10px;">
						<input class="sub" type="submit" value="" style="display: inline;"/>
						<a href="register_register.action" style="color:blue" >注册</a>
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="<%=basePath%>js/dwz/themes/default/images/login_banner.jpg" /></div>
		</div>
	</div>
<script type="text/javascript">
</script>
</body>
</html>