<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>经销商管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/user/customer/${customer.id}/pos/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${pos.id}"/>
		<fieldset>
			<legend><small>POS管理</small></legend>
			<div class="control-group">
				<label class="control-label">终端编号:</label>
				<div class="controls">
					<input type="text" id="terminalNumber" name="terminalNumber" value="${pos.terminalNumber}" class="input-large" placeholder="输入名称" />
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
