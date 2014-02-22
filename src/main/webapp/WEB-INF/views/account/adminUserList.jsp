<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>用户管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<p><a href="${ctx}/admin/user/create" class="btn">添加用户</a><a href="${ctx}/admin/data/" class="btn">上传数据</a><a href="${ctx}/admin/log/" class="btn">管理日志</a></p>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center-align">
		<thead><tr><th>登录名</th><th>用户名</th><th>身份证号</th><th>手机号</th><th>注册时间<th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a href="${ctx}/admin/user/update/${user.id}" class="btn btn-primary">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.idNumber}</td>
				<td>${user.phoneNumber}</td>
				<td>
					<fmt:formatDate value="${user.registerDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
				</td>
				<td>
					<shiro:hasRole name="admin"></shiro:hasRole>
					<shiro:lacksRole name="admin"></shiro:lacksRole> <!-- not work -->
					<c:choose>
					    <c:when test="${fn:contains(user.roles, 'admin')}">
					   		<button class="btn disabled">删除</button>
					    </c:when>
					    <c:otherwise>
							<a href="${ctx}/admin/user/delete/${user.id}" class="btn">删除</a>
					    </c:otherwise>
					</c:choose>					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
