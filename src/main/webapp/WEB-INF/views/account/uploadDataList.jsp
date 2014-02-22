<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理上传数据</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<p>
		<a class="btn" href="${ctx}/admin/user/">返回</a>
	</p>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center-align">
		<thead><tr><th>日期</th><th>内容</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${logs.content}" var="log">
			<tr>
				<td>${log.dateTime}</td>
				<td>${log.content}</td>
				<td><a href="${ctx}/admin/data/log/delete/${log.id}"  class="btn">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${logs}" paginationSize="5"/>
	
	<form id="inputForm" action="${ctx}/admin/data/uploadData" method="post" enctype="multipart/form-data" class="form-horizontal">
		<fieldset>
			<legend><small>上传数据文件</small></legend>
			<div class="control-group">
				<div class="controls">
					<input type="file" id="file" name="file" class="btn-primary" title="上传数据文件" />
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			$('input[type=file]').bootstrapFileInput();
		});
	</script>
</body>
</html>
