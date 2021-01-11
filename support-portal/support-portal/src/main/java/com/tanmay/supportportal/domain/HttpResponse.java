package com.tanmay.supportportal.domain;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HttpResponse {  // this is response that we sent to user everytime
	
	private Date timeStamp;
	private int httpStatusCode;  // 200,201,400,500
	private HttpStatus httpStatus;
	private String reason;
	private String message;
	
	public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
		super();
		this.timeStamp = new Date();
		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.reason = reason;
		this.message = message;
	}

	
}
