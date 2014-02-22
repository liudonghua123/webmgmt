<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>交易明细查看</title>
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
			startDate = "2000-01-01 00:00:00";
		}
		if(!endDate) {
			var d = new Date();
			endDate = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
		}
		$('#startDateTime').val(startDate);
		$('#endDateTime').val(endDate);
		return true;
	}
	
	$(document).ready(function() {
		$('.datetimepicker').datetimepicker({
			language:  'zh-CN',
		    format: 'yyyy-mm-dd hh:ii:ss'
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
		    $.cookie('transaction_detail_default_columns', newArray.join(',') , { expires: 365, path: '/'});
		});
		
		// init
		$("input[type='checkbox'][name^='col_']").prop('checked', false);
		var array = new Array();
		for(var i = 0; i < 31; i ++) {
		    array.push(i+1);
		}
		$.each(array, function(index, value) {
			$('td:nth-child(' + value+ '),th:nth-child(' + value+ ')', 'table.none-checkbox-table').hide();
		});
		// set default shown columns
		var transaction_detail_default_columns = [1,2,3,4,5];
		var cookie_transaction_detail_default_columns = $.cookie('transaction_detail_default_columns');
		if(cookie_transaction_detail_default_columns) {
			transaction_detail_default_columns = cookie_transaction_detail_default_columns.split(",");
		}
		$.each(transaction_detail_default_columns, function( index, value ) {
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
				<input type="text" name="startDateTime"  id="startDateTime" class="input-medium required datetimepicker" value="<fmt:formatDate value="${startDateTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
				<label class="control-label">结束时间:</label>
				<input type="text" name="endDateTime" id="endDateTime"  class="input-medium required datetimepicker" value="<fmt:formatDate value="${endDateTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
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
			<td><label><input type="checkbox" name="col_1" checked="checked" />系统参考号</label></td>
			<td><label><input type="checkbox" name="col_2" checked="checked" />原交易系统参考号</label></td>
			<td><label><input type="checkbox" name="col_3" checked="checked" />省份</label></td>
			<td><label><input type="checkbox" name="col_4" checked="checked" />城市</label></td>
			<td><label><input type="checkbox" name="col_5" checked="checked" />交易类型</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_6" checked="checked" />终端名称</label></td>
			<td><label><input type="checkbox" name="col_7" checked="checked" />缩略卡号</label></td>
			<td><label><input type="checkbox" name="col_8" checked="checked" />发卡机构编号</label></td>
			<td><label><input type="checkbox" name="col_9" checked="checked" />卡组织</label></td>
			<td><label><input type="checkbox" name="col_10" checked="checked" />交易金额</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_11" checked="checked" />原交易金额</label></td>
			<td><label><input type="checkbox" name="col_12" checked="checked" />交易时间</label></td>
			<td><label><input type="checkbox" name="col_13" checked="checked" />交易标志</label></td>
			<td><label><input type="checkbox" name="col_14" checked="checked" />授权码</label></td>
			<td><label><input type="checkbox" name="col_15" checked="checked" />应答码</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_16" checked="checked" />外部跟踪编号</label></td>
			<td><label><input type="checkbox" name="col_17" checked="checked" />外部客户号</label></td>
			<td><label><input type="checkbox" name="col_18" checked="checked" />服务渠道跟踪编号</label></td>
			<td><label><input type="checkbox" name="col_19" checked="checked" />结算金额</label></td>
			<td><label><input type="checkbox" name="col_20" checked="checked" />结算时间</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_21" checked="checked" />终端编号</label></td>
			<td><label><input type="checkbox" name="col_22" checked="checked" />原交易终端编号</label></td>
			<td><label><input type="checkbox" name="col_23" checked="checked" />终端操作员编号</label></td>
			<td><label><input type="checkbox" name="col_24" checked="checked" />产品编号</label></td>
			<td><label><input type="checkbox" name="col_25" checked="checked" />订单报价币别</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_26" checked="checked" />订单报价金额</label></td>
			<td><label><input type="checkbox" name="col_27" checked="checked" />订单扣款币别</label></td>
			<td><label><input type="checkbox" name="col_28" checked="checked" />订单扣款金额</label></td>
			<td><label><input type="checkbox" name="col_29" checked="checked" />汇率提供标志</label></td>
			<td><label><input type="checkbox" name="col_30" checked="checked" />汇率方向</label></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="col_31" checked="checked" />汇率</label></td>
			<td/>
			<td/>
			<td/>
			<td/>
		</tr>
	<table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center-align none-checkbox-table">
		<thead>
			<tr>
				<th>系统参考号</th>
				<th>原交易系统参考号</th>
				<th>省份</th>
				<th>城市</th>
				<th>交易类型</th>
				<th>终端名称</th>
				<th>缩略卡号</th>
				<th>发卡机构编号</th>
				<th>卡组织</th>
				<th>交易金额</th>
				<th>原交易金额</th>
				<th>交易时间</th>
				<th>交易标志</th>
				<th>授权码</th>
				<th>应答码</th>
				<th>外部跟踪编号</th>
				<th>外部客户号</th>
				<th>服务渠道跟踪编号</th>
				<th>结算金额</th>
				<th>结算时间</th>
				<th>终端编号</th>
				<th>原交易终端编号</th>
				<th>终端操作员编号</th>
				<th>产品编号</th>
				<th>订单报价币别</th>
				<th>订单报价金额</th>
				<th>订单扣款币别</th>
				<th>订单扣款金额</th>
				<th>汇率提供标志</th>
				<th>汇率方向</th>
				<th>汇率</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${transactionDetails.content}" var="transactionDetail">
			<tr>
				<td>${transactionDetail.systemReferenceNumber}</td>
				<td>${transactionDetail.orginalTransactionSystemReferenceNumber}</td>
				<td>${transactionDetail.province}</td>
				<td>${transactionDetail.city}</td>
				<td>${transactionDetail.transactionType}</td>
				<td>${transactionDetail.customerName}</td>
				<td>${transactionDetail.shortCardNumber}</td>
				<td>${transactionDetail.publishCardInstitute}</td>
				<td>${transactionDetail.cardOrganization}</td>
				<td>${transactionDetail.transactionCash}</td>
				<td>${transactionDetail.originalTransactionCash}</td>
				<td><fmt:formatDate value="${transactionDetail.transactionDatetime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${transactionDetail.transactionFlag}</td>
				<td>${transactionDetail.authorizationCode}</td>
				<td>${transactionDetail.responseCode}</td>
				<td>${transactionDetail.externalTraceNumber}</td>
				<td>${transactionDetail.externalCustomerNumber}</td>
				<td>${transactionDetail.serviceChannelTraceNumber}</td>
				<td>${transactionDetail.settleCash}</td>
				<td>${transactionDetail.settleDatetime}</td>
				<td>${transactionDetail.terminalNumber}</td>
				<td>${transactionDetail.originalTerminalNumber}</td>
				<td>${transactionDetail.operatorNumber}</td>
				<td>${transactionDetail.productNumber}</td>
				<td>${transactionDetail.orderCashType}</td>
				<td>${transactionDetail.orderOfferCash}</td>
				<td>${transactionDetail.orderPayCashType}</td>
				<td>${transactionDetail.orderPayCash}</td>
				<td>${transactionDetail.rateProvideFlag}</td>
				<td>${transactionDetail.rateOrientation}</td>
				<td>${transactionDetail.rate}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${transactionDetails}" paginationSize="5"/>

</body>
</html>
