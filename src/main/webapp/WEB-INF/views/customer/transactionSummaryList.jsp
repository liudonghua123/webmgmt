<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>交易汇总查看</title>
	<style type="text/css">
		label {
		    display: block;
		    padding-left: 15px;
		    text-indent: -15px;
		}
		input[type="checkbox"] {
		    width: 13px;
		    height: 13px;
		    padding: 0;
		    margin:0;
		    vertical-align: middle;
		    position: relative;
		    top: -1px;
		    *overflow: hidden;
		}	
	</style>
	<script type="text/javascript">
	function submitForm() {
		var startDate = $('#startDateTime').val();
		var endDate = $('#endDateTime').val();
		if(!startDate) {
			startDate = "2000-01-01";
		}
		if(!endDate) {
			var d = new Date();
			endDate = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		}
		$('#startDateTime').val(startDate + " 00:00:00");
		$('#endDateTime').val(endDate + " 23:59:59");
		return true;
	}
	
	$(document).ready(function() {
		$('.datetimepicker').datetimepicker({
			language:  'zh-CN',
			minView: 'month',
		    format: 'yyyy-mm-dd'
		});
		
		$("input[type='checkbox'][name^='col_']").click(function() {
			// checkbox name's pattern like 'col_2', 'col_12'
		    var index = $(this).attr('name').substr(4);
		    $('td:nth-child(' + index+ '),th:nth-child(' + index+ ')', 'table.none-checkbox-table').toggle();
		    // save the selected index
		    var newArray = new Array();
		    $("input[type='checkbox'][name^='col_']:checked").each(function(key,value) {
		    	newArray.push($(this).attr("name").substr(4));
		    });
		    $.cookie('transaction_summary_default_columns', newArray.join(',') , { expires: 365, path: '/'});
		});
		
		// init
		$("input[type='checkbox'][name^='col_']").prop('checked', false);
		var array = new Array();
		for(var i = 0; i < 11; i ++) {
		    array.push(i+1);
		}
		$.each(array, function(index, value) {
			$('td:nth-child(' + value+ '),th:nth-child(' + value+ ')', 'table.none-checkbox-table').hide();
		});
		// set defaults shown columns
		var transaction_summary_default_columns = [1,2,3,5,7];
		var cookie_transaction_summary_default_columns = $.cookie('transaction_summary_default_columns');
		if(cookie_transaction_summary_default_columns) {
			transaction_summary_default_columns = cookie_transaction_summary_default_columns.split(",");
		}
		$.each(transaction_summary_default_columns, function( index, value ) {
			$("input[type='checkbox'][name='col_" + value + "']").prop('checked', true);
			$('td:nth-child(' + value+ '),th:nth-child(' + value+ ')', 'table.none-checkbox-table').show();
		});
		
	});
	</script>	
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="pull-right">
			<form class="form-search form-inline" action="#" onsubmit="return submitForm()">
				<label class="control-label">开始时间:</label>
				<input type="text" name="startDateTime"  id="startDateTime" class="input-medium required datetimepicker" value="<fmt:formatDate value="${startDateTime}" pattern="yyyy-MM-dd" />"/>
				<label class="control-label">结束时间:</label>
				<input type="text" name="endDateTime" id="endDateTime"  class="input-medium required datetimepicker" value="<fmt:formatDate value="${endDateTime}" pattern="yyyy-MM-dd" />"/>
				<input type="submit" class="btn" id="search_btn" value="查询">
		    </form>
	    </div>
	</div>
	
	<h3><c:if test="${!empty customer}"><small>经销商:</small> <strong>${customer.name}</strong></c:if></h3>
	<p>
		<a class="btn" href="${ctx}/user/customer/">返回</a>
	</p>
	<table class="table table-striped table-bordered table-condensed table-center-align">
		<tr>
			<td><label><input type="checkbox" name="col_1" checked="checked" />交易时间</label></td>
			<td><label><input type="checkbox" name="col_2" checked="checked" />商户</label></td>
			<td><label><input type="checkbox" name="col_3" checked="checked" />交易金额</label></td>
			<td><label><input type="checkbox" name="col_4" checked="checked" />退货金额</label></td>
			<td><label><input type="checkbox" name="col_5" checked="checked" />交易手续费</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_6" checked="checked" />预授权手续费</label></td>
			<td><label><input type="checkbox" name="col_7" checked="checked" />结算金额</label></td>
			<td><label><input type="checkbox" name="col_8" checked="checked" />交易笔数</label></td>
			<td><label><input type="checkbox" name="col_9" checked="checked" />退货笔数</label></td>
			<td><label><input type="checkbox" name="col_10" checked="checked" />成功笔数</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_31" checked="checked" />失败笔数</label></td>
			<td/>
			<td/>
			<td/>
			<td/>
		</tr>
	<table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center-align none-checkbox-table">
		<thead>
			<tr>
				<th>交易时间</th>
				<th>商户</th>
				<th>交易金额</th>
				<th>退货金额</th>
				<th>交易手续费</th>
				<th>预授权手续费</th>
				<th>结算金额</th>
				<th>交易笔数</th>
				<th>退货笔数</th>
				<th>成功笔数</th>
				<th>失败笔数</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${transactionSummarys.content}" var="transactionSummary">
			<tr>
				<td><fmt:formatDate value="${transactionSummary.summaryDate}" pattern="yyyy-MM-dd" /></td>
				<td>${transactionSummary.customerName}</td>
				<td>${transactionSummary.transactionCash}</td>
				<td>${transactionSummary.returnGoodsCash}</td>
				<td>${transactionSummary.transactionFee}</td>
				<td>${transactionSummary.authorizationFee}</td>
				<td>${transactionSummary.settleCash}</td>
				<td>${transactionSummary.transactionCount}</td>
				<td>${transactionSummary.returnGoodsCount}</td>
				<td>${transactionSummary.successfulCount}</td>
				<td>${transactionSummary.failedCount}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${transactionSummarys}" paginationSize="5"/>

</body>
</html>
