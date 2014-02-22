package com.bill99.yn.webmgmt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "ss_log")
public class Log extends IdEntity {
	
	private String userName;
	private LogAction logAction;
	private Date dateTime;
	private String content;
	
	public Log() {
		
	}
	
	public Log(String userName, LogAction logAction, Date dateTime, String content) {
		this.userName = userName;
		this.logAction = logAction;
		this.dateTime = dateTime;
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Enumerated(EnumType.STRING)
//	@Type(type = "com.atlassian.hibernate.extras.type.GenericEnumUserType", parameters = {
//			@Parameter(name = "enumClass", value = "com.bill99.yn.webmgmt.entity.LogAction"),
//			@Parameter(name = "identifierMethod", value = "toString"),
//			@Parameter(name = "valueOfMethod", value = "fromString")
//			})
	public LogAction getLogAction() {
		return logAction;
	}

	public void setLogAction(LogAction logAction) {
		this.logAction = logAction;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}