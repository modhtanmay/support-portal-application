package com.tanmay.supportportal.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tanmay.supportportal.constant.SecurityConstant;
import com.tanmay.supportportal.domain.UserPrincipal;

import static java.util.Arrays.stream;
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

	public List<GrantedAuthority> getAuthorities(String token){
		String[] claims = getClaimsFromToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	public Authentication getAuthentication(String username,List<GrantedAuthority> authorities,HttpServletRequest request) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
		
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return usernamePasswordAuthenticationToken;
	}
	
	
	
	private String[] getClaimsFromToken(String token) {
		// TODO Auto-generated method stub
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {
		// TODO Auto-generated method stub
		JWTVerifier verifier;
		try {
			Algorithm algorithm = HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(SecurityConstant.GET_ARRAYS_LLC).build();
		} catch(JWTVerificationException exception) {
			throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
		}
		return verifier;
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
