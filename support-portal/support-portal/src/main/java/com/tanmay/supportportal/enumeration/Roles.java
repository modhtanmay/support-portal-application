package com.tanmay.supportportal.enumeration;

import static com.tanmay.supportportal.constant.Authority.*;

public enum Roles {
	ROLE_USER(USER_AUTHORITIES),
	ROLE_HR(HR_AUTHORITIES),
	ROLE_MANAGER(MANAGER_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES),
	ROLE_SUPER_ADMIN(SUPER_USER_AUTHORITIES);

	private String[] authorities;
	
	Roles(String... authorities){
		this.authorities = authorities;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}
	
	
}
