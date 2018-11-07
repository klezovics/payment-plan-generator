package com.klezovich.payment_plan_generator.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {
	
	private Date timestamp;
    private String status;
    private String message;
    String  path;
    
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus( String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ApiError(Date timestamp, String status, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.path = path;
	}
    
    

    
}