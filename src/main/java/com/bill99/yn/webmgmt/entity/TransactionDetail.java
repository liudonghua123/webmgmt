package com.bill99.yn.webmgmt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "ss_transaction_detail")
public class TransactionDetail extends IdEntity {

	private String systemReferenceNumber;
	private String orginalTransactionSystemReferenceNumber;
	private String province;
	private String city;
	private String transactionType;
	private String customerName;
	private String shortCardNumber;
	private String publishCardInstitute;
	private String cardOrganization;
	private String transactionCash;
	private String originalTransactionCash;
	private Date transactionDatetime;
	private String transactionFlag;
	private String authorizationCode;
	private String responseCode;
	private String externalTraceNumber;
	private String externalCustomerNumber;
	private String serviceChannelTraceNumber;
	private String settleCash;
	private String settleDatetime;
	private String terminalNumber;
	private String originalTerminalNumber;
	private String operatorNumber;
	private String productNumber;
	private String orderCashType;
	private String orderOfferCash;
	private String orderPayCashType;
	private String orderPayCash;
	private String rateProvideFlag;
	private String rateOrientation;
	private String rate;
	
	public TransactionDetail() {
		super();
	}

	public TransactionDetail(String systemReferenceNumber,
			String orginalTransactionSystemReferenceNumber, String province, String city,
			String transactionType, String customerName, String shortCardNumber,
			String publishCardInstitute, String cardOrganization, String transactionCash,
			String originalTransactionCash, Date transactionDatetime, String transactionFlag,
			String authorizationCode, String responseCode, String externalTraceNumber,
			String externalCustomerNumber, String serviceChannelTraceNumber, String settleCash,
			String settleDatetime, String terminalNumber, String originalTerminalNumber,
			String operatorNumber, String productNumber, String orderCashType,
			String orderOfferCash, String orderPayCashType, String orderPayCash,
			String rateProvideFlag, String rateOrientation, String rate) {
		super();
		this.systemReferenceNumber = systemReferenceNumber;
		this.orginalTransactionSystemReferenceNumber = orginalTransactionSystemReferenceNumber;
		this.province = province;
		this.city = city;
		this.transactionType = transactionType;
		this.customerName = customerName;
		this.shortCardNumber = shortCardNumber;
		this.publishCardInstitute = publishCardInstitute;
		this.cardOrganization = cardOrganization;
		this.transactionCash = transactionCash;
		this.originalTransactionCash = originalTransactionCash;
		this.transactionDatetime = transactionDatetime;
		this.transactionFlag = transactionFlag;
		this.authorizationCode = authorizationCode;
		this.responseCode = responseCode;
		this.externalTraceNumber = externalTraceNumber;
		this.externalCustomerNumber = externalCustomerNumber;
		this.serviceChannelTraceNumber = serviceChannelTraceNumber;
		this.settleCash = settleCash;
		this.settleDatetime = settleDatetime;
		this.terminalNumber = terminalNumber;
		this.originalTerminalNumber = originalTerminalNumber;
		this.operatorNumber = operatorNumber;
		this.productNumber = productNumber;
		this.orderCashType = orderCashType;
		this.orderOfferCash = orderOfferCash;
		this.orderPayCashType = orderPayCashType;
		this.orderPayCash = orderPayCash;
		this.rateProvideFlag = rateProvideFlag;
		this.rateOrientation = rateOrientation;
		this.rate = rate;
	}

	public String getSystemReferenceNumber() {
		return systemReferenceNumber;
	}

	public void setSystemReferenceNumber(String systemReferenceNumber) {
		this.systemReferenceNumber = systemReferenceNumber;
	}

	public String getOrginalTransactionSystemReferenceNumber() {
		return orginalTransactionSystemReferenceNumber;
	}

	public void setOrginalTransactionSystemReferenceNumber(
			String orginalTransactionSystemReferenceNumber) {
		this.orginalTransactionSystemReferenceNumber = orginalTransactionSystemReferenceNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getShortCardNumber() {
		return shortCardNumber;
	}

	public void setShortCardNumber(String shortCardNumber) {
		this.shortCardNumber = shortCardNumber;
	}

	public String getPublishCardInstitute() {
		return publishCardInstitute;
	}

	public void setPublishCardInstitute(String publishCardInstitute) {
		this.publishCardInstitute = publishCardInstitute;
	}

	public String getCardOrganization() {
		return cardOrganization;
	}

	public void setCardOrganization(String cardOrganization) {
		this.cardOrganization = cardOrganization;
	}

	public String getTransactionCash() {
		return transactionCash;
	}

	public void setTransactionCash(String transactionCash) {
		this.transactionCash = transactionCash;
	}

	public String getOriginalTransactionCash() {
		return originalTransactionCash;
	}

	public void setOriginalTransactionCash(String originalTransactionCash) {
		this.originalTransactionCash = originalTransactionCash;
	}

	public Date getTransactionDatetime() {
		return transactionDatetime;
	}

	public void setTransactionDatetime(Date transactionDatetime) {
		this.transactionDatetime = transactionDatetime;
	}

	public String getTransactionFlag() {
		return transactionFlag;
	}

	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getExternalTraceNumber() {
		return externalTraceNumber;
	}

	public void setExternalTraceNumber(String externalTraceNumber) {
		this.externalTraceNumber = externalTraceNumber;
	}

	public String getExternalCustomerNumber() {
		return externalCustomerNumber;
	}

	public void setExternalCustomerNumber(String externalCustomerNumber) {
		this.externalCustomerNumber = externalCustomerNumber;
	}

	public String getServiceChannelTraceNumber() {
		return serviceChannelTraceNumber;
	}

	public void setServiceChannelTraceNumber(String serviceChannelTraceNumber) {
		this.serviceChannelTraceNumber = serviceChannelTraceNumber;
	}

	public String getSettleCash() {
		return settleCash;
	}

	public void setSettleCash(String settleCash) {
		this.settleCash = settleCash;
	}

	public String getSettleDatetime() {
		return settleDatetime;
	}

	public void setSettleDatetime(String settleDatetime) {
		this.settleDatetime = settleDatetime;
	}

	public String getTerminalNumber() {
		return terminalNumber;
	}

	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	public String getOriginalTerminalNumber() {
		return originalTerminalNumber;
	}

	public void setOriginalTerminalNumber(String originalTerminalNumber) {
		this.originalTerminalNumber = originalTerminalNumber;
	}

	public String getOperatorNumber() {
		return operatorNumber;
	}

	public void setOperatorNumber(String operatorNumber) {
		this.operatorNumber = operatorNumber;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getOrderCashType() {
		return orderCashType;
	}

	public void setOrderCashType(String orderCashType) {
		this.orderCashType = orderCashType;
	}

	public String getOrderOfferCash() {
		return orderOfferCash;
	}

	public void setOrderOfferCash(String orderOfferCash) {
		this.orderOfferCash = orderOfferCash;
	}

	public String getOrderPayCashType() {
		return orderPayCashType;
	}

	public void setOrderPayCashType(String orderPayCashType) {
		this.orderPayCashType = orderPayCashType;
	}

	public String getOrderPayCash() {
		return orderPayCash;
	}

	public void setOrderPayCash(String orderPayCash) {
		this.orderPayCash = orderPayCash;
	}

	public String getRateProvideFlag() {
		return rateProvideFlag;
	}

	public void setRateProvideFlag(String rateProvideFlag) {
		this.rateProvideFlag = rateProvideFlag;
	}

	public String getRateOrientation() {
		return rateOrientation;
	}

	public void setRateOrientation(String rateOrientation) {
		this.rateOrientation = rateOrientation;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
