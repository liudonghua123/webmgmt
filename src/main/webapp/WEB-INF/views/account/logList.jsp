<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>管理日志</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<p>
		<a class="btn" href="${ctx}/admin/user/">返回</a>
	</p>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center-align">
		<thead><tr><th>用户</th><th>操作</th><th>日期</th><th>内容</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${logs.content}" var="log">
			<tr>
				<td>${log.userName}</td>
				<td>${log.logAction}</td>
				<td>${log.dateTime}</td>
				<td>${log.content}</td>
				<td><a href="${ctx}/admin/log/delete/${log.id}"  class="btn">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${logs}" paginationSize="5"/>

</body>
</html>
