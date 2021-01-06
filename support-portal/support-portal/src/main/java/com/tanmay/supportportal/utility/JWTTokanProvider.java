package com.tanmay.supportportal.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import com.auth0.jwt.JWT;
import com.tanmay.supportportal.constant.SecurityConstant;
import com.tanmay.supportportal.domain.UserPrincipal;


import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
public class JWTTokanProvider {

	@Value("${jwt.secret}")
	private String secret;	// will be in some secured server
	
	
	public String generateJwtToken(UserPrincipal userPrincipal) {
		String[] claims = getClaimsFromUser(userPrincipal);
		return JWT.create().withIssuer(SecurityConstant.GET_ARRAYS_LLC).withAudience(SecurityConstant.GET_ARRAYS_ADMINISTRATION)
						   .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername()).withArrayClaim(SecurityConstant.AUTHORITIES, claims)
						   .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
						   .sign(HMAC512(secret.getBytes()));
	}


	private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
		// TODO Auto-generated method stub
		List<String> authorities = new ArrayList<String>();
		for(GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}
	
}
