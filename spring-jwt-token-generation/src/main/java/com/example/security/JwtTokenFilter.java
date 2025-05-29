package com.example.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	JWTUtil jwtUtil;

	@Autowired
	SecurityUserAuthenticationService authenticationService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1. Token is available or not
		String JwtToken = request.getHeader("Authorization");
		String userId = null;

		if (JwtToken != null && !JwtToken.isBlank() && !JwtToken.isEmpty()) {

			System.out.println("OncePerRequest Token Present : Incoming Token :" + JwtToken);
			// validate the token : user name && Token expire

			// Getting userName from Token
			userId = jwtUtil.getUserIdFromToken(JwtToken);

			System.out.println("Request came from user: " + userId);

		} else {
			System.out.println("OncePerRequest Token is missing");
		}

		// Token userid id matching with requested userId or not

		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			System.out.println("OncePerRequest : get user information from db by passing userid :Got from token");
			// get user information from db by passing userid : got from token
			UserDetails userDetails = authenticationService.loadUserByUsername(userId);

			// passing user id (came from database)to token validator
			System.out.println("OncePerRequest :Now validating token is expire or not & matching user name");

			Boolean isValidToken = jwtUtil.isValidateToken(JwtToken, userDetails.getUsername());

			System.out.println("OncePerRequest Token validation result :" + isValidToken);

			if (isValidToken) {
				System.out.println("OncePerRequest :Setting security context  for that user id");

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, isValidToken);

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			} else {

				System.out.println("OncePerRequest:Token is Invalid ,Please Try with Valid Token");
			}

			

		}
		// forward req to dispatcher servlet or other filters
					filterChain.doFilter(request, response);

	}

}
