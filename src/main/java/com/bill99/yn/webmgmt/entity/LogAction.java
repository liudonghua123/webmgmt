package com.bill99.yn.webmgmt.entity;

public enum LogAction {
	
	UNKNOW("unknow"), LOGIN("login"), UPLOAD_DATA("upload_data"), PARSE_DATA("parse_data"), STORE_DATA("store_data");
	
	private String name;
	
	LogAction(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public LogAction fromString(String value) {
		switch(value) {
		case "login":
			return LOGIN;
		case "upload_data":
			return UPLOAD_DATA;
		case "parse_data":
			return PARSE_DATA;
		case "store_data":
			return STORE_DATA;
		default:
			return UNKNOW;
		}
	}
	
}
