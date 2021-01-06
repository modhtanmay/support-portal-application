package com.tanmay.supportportal.utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

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
		return null;
	}
	
}
