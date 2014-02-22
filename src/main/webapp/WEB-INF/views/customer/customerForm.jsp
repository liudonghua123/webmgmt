<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>经销商管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/user/customer/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${customer.id}"/>
		<fieldset>
			<legend><small>经销商管理</small></legend>
			<div class="control-group">
				<label class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="name" name="name" value="${customer.name}" class="input-large" placeholder="输入名称" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">手机号:</label>
				<div class="controls">
					<input type="text" id="phoneNumber" name="phoneNumber" value="${customer.phoneNumber}" class="input-large required" placeholder="输入手机号" />
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
