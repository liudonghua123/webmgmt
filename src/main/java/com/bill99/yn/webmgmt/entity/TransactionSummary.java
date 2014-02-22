package com.bill99.yn.webmgmt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "ss_transaction_summary")
public class TransactionSummary extends IdEntity {
	private Date summaryDate;
	private String customerName;
	private double transactionCash;
	private double returnGoodsCash;
	private double transactionFee;
	private double authorizationFee;
	private double settleCash;
	private int transactionCount;
	private int returnGoodsCount;
	private int successfulCount;
	private int failedCount;
	

	public TransactionSummary() {
		super();
	}

	public TransactionSummary(Date summaryDate, String customerName, double transactionCash,
			double returnGoodsCash, double transactionFee, double authorizationFee, double settleCash,
			int transactionCount, int returnGoodsCount, int successfulCount, int failedCount) {
		super();
		this.summaryDate = summaryDate;
		this.customerName = customerName;
		this.transactionCash = transactionCash;
		this.returnGoodsCash = returnGoodsCash;
		this.transactionFee = transactionFee;
		this.authorizationFee = authorizationFee;
		this.settleCash = settleCash;
		this.transactionCount = transactionCount;
		this.returnGoodsCount = returnGoodsCount;
		this.successfulCount = successfulCount;
		this.failedCount = failedCount;
	}

	public Date getSummaryDate() {
		return summaryDate;
	}

	public void setSummaryDate(Date summaryDate) {
		this.summaryDate = summaryDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getTransactionCash() {
		return transactionCash;
	}

	public void setTransactionCash(double transactionCash) {
		this.transactionCash = transactionCash;
	}

	public double getReturnGoodsCash() {
		return returnGoodsCash;
	}

	public void setReturnGoodsCash(double returnGoodsCash) {
		this.returnGoodsCash = returnGoodsCash;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public double getAuthorizationFee() {
		return authorizationFee;
	}

	public void setAuthorizationFee(double authorizationFee) {
		this.authorizationFee = authorizationFee;
	}

	public double getSettleCash() {
		return settleCash;
	}

	public void setSettleCash(double settleCash) {
		this.settleCash = settleCash;
	}

	public int getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(int transactionCount) {
		this.transactionCount = transactionCount;
	}

	public int getReturnGoodsCount() {
		return returnGoodsCount;
	}

	public void setReturnGoodsCount(int returnGoodsCount) {
		this.returnGoodsCount = returnGoodsCount;
	}

	public int getSuccessfulCount() {
		return successfulCount;
	}

	public void setSuccessfulCount(int successfulCount) {
		this.successfulCount = successfulCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}