<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>经销商管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称：</label> <input type="text" name="search_LIKE_name" class="input-medium" value="${param.search_LIKE_name}"> 
				<button type="submit" class="btn" id="search_btn">搜索</button>
		    </form>
	    </div>
	</div>
	
	<p>
		<a class="btn" href="${ctx}/user/customer/create">创建经销商</a>
		<a href="${ctx}/user/customer/transactionDetail/"  class="btn">查看交易明细</a>
		<a href="${ctx}/user/customer/transactionSummary/"  class="btn">查看交易汇总</a>
	</p>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center-align">
		<thead><tr><th>名称</th><th>手机号</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${customers.content}" var="customer">
			<tr>
				<td><a href="${ctx}/user/customer/update/${customer.id}"  class="btn btn-primary">${customer.name}</a></td>
				<td>${customer.phoneNumber}</td>
				<td>
					<a href="${ctx}/user/customer/${customer.id}/pos/"  class="btn">管理终端</a>
					<a href="${ctx}/user/customer/${customer.id}/transactionDetail/"  class="btn">查看交易明细</a>
					<a href="${ctx}/user/customer/${customer.id}/transactionSummary/"  class="btn">查看交易汇总</a>
					<a href="${ctx}/user/customer/delete/${customer.id}"  class="btn">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${customers}" paginationSize="5"/>

</body>
</html>
