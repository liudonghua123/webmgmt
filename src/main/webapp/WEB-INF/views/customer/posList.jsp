<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>POS管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<h3><small>经销商:</small> <strong>${customer.name}</strong></h3>
	<p>
		<a class="btn" href="${ctx}/user/customer/${customer.id}/pos/create">创建POS</a>
		<a class="btn" href="${ctx}/user/customer/">返回</a>
	</p>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center-align">
		<thead><tr><th>终端编号</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${poss.content}" var="pos">
			<tr>
				<td><a href="${ctx}/user/customer/${customer.id}/pos/update/${pos.id}"  class="btn btn-primary">${pos.terminalNumber}</a></td>
				<td><a href="${ctx}/user/customer/${customer.id}/pos/delete/${pos.id}"  class="btn">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${poss}" paginationSize="5"/>

</body>
</html>
