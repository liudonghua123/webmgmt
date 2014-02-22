<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>登录页</title>
</head>

<body>
	<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
	<%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
		</div>
	<%
	}
	%>
		<div class="control-group">
			<label for="username" class="control-label">用户名:</label>
			<div class="controls">
				<input type="text" id="username" name="username"  value="${username}" class="input-medium required" placeholder="输入用户名"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码:</label>
			<div class="controls">
				<input type="password" id="password" name="password" class="input-medium required" placeholder="输入密码"/>
			</div>
		</div>
		<div class="control-group">
			<label for="captcha" class="control-label">验证码:</label>
			<div class="controls">
				<input type="text" id="captcha" name="captcha" class="input-medium required" placeholder="输入验证码">
				<img alt="请输入验证码" id="randomCode" src="${ctx}/images/kaptcha.jpg" style="height: 30px">
				<a href="#" id="refresh" class="btn btn-info btn-small">刷新验证码</a>
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
				<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/>
				<span class="help-block">请管理员或普通经销商使用各自账号登陆系统</span>
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {
			$("#username").focus();
			
			$("#loginForm").validate();
			
			 $("#refresh").click(function() {
				 var random = new Date().getTime();
				 $("#randomCode").attr("src", "${ctx}/images/kaptcha.jpg?timestamp=" + random); 
			});  
		});
	</script>
</body>
</html>
