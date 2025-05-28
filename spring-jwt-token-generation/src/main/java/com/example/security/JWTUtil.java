package com.example.security;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	private final String SECRET_KEY= "hhasd7auyd479vdjhsdwj797sbj73vgvdvb8q8y2bv727y787yiwe79hj292bbo20iu2hfwahgr7899039872900953475nnhgrkjehgfnjj";
	private final long  TOKEN_EXPIRY_DURATION = 5 * 60000;
	
	private SecretKey getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String createToken(String emailId) {
		String token = null;
		
		token =	Jwts
				.builder() //creating
				.subject(emailId)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY_DURATION))
				.signWith(getSecretKey())
				.compact();
		
		return token;
	}
	
	public boolean isValidateToken(String token,String userId) {
		String userIDFromToken = getUserIdFromToken(token);
		System.out.println("User Id retrived from token : "+ userIDFromToken);
		
		
		return userIDFromToken.equalsIgnoreCase(userId) && isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		
		Date expiryTime = Jwts.parser()//reading
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration();
		
		System.out.println("Token Expiry Time : "+expiryTime);
		
		return expiryTime.after(new Date());
	}

	// user id from token
	public String getUserIdFromToken(String token) {
		
		return Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
		
	}
}
