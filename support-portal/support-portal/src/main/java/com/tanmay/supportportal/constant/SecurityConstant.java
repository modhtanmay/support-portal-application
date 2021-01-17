package com.tanmay.supportportal.constant;

public class SecurityConstant {
	
	public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
	public static final String TOKEN_PREFIX = "Bearer ";	// BEARER means no further checks required of user 
	public static final String JWT_TOKEN_HEADER = "Jwt-Token"; //custom header
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be Verified";
	public static final String GET_ARRAYS_LLC = "Get Arrays, LLC";		//which audience provided by LLC or google, etc
	public static final String GET_ARRAYS_ADMINISTRATION = "User Management Portal";	//who will be using it , check of right audience
	public static final String AUTHORITIES = "authorities";  // this will hold all authorities
	public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";
	public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	//public static final String[] PUBLIC_URLS = { "/user/login","/user/register","/user/resetPassword/**","/user/image/**" };  // no security for this urls
	public static final String[] PUBLIC_URLS = { "**" };	// only for debugging purpose
}
