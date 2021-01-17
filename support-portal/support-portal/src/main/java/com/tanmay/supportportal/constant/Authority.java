package com.tanmay.supportportal.constant;

public class Authority {
	public static final String[] USER_AUTHORITIES = { "user:read" }; // user can only see
	public static final String[] HR_AUTHORITIES = { "user:read" ,"user:update"}; // hr can read,update
	public static final String[] MANAGER_AUTHORITIES = { "user:read" ,"user:update"}; // manager can read,update
	public static final String[] ADMIN_AUTHORITIES = { "user:read","user:create","user:update"}; // admin can read,create,update
	public static final String[] SUPER_USER_AUTHORITIES = { "user:read" ,"user:create","user:update","user:delete"}; // superuser can read,create,update,delete
	
}
