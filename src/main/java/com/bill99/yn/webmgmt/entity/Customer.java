package com.bill99.yn.webmgmt.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "ss_customer")
public class Customer extends IdEntity {
	private String name;
	private String phoneNumber;
	private User user;
	
	public Customer() {
		
	}
	
	public Customer(Long id) {
		this.id = id;
	}
	
	public Customer(String name, String phoneNumber, User user) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.user = user;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}