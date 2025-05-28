package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.JWTUtil;

@RestController
public class TestController {
	
	@Autowired
	JWTUtil jwtUtil;
	
	@GetMapping("/jwt/create/token/{emailId}")
	public  String getToken(@PathVariable String emailId) {
		
		return jwtUtil.createToken(emailId);
	}
	
	@GetMapping("/jwt/validate/token")
	public Boolean validateToken() {
		return jwtUtil.isValidateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYW1AZ21haWwuY29tIiwiaWF0IjoxNzQ4NDE5MjExLCJleHAiOjE3NDg0MTk1MTF9.ro-FL7fdUaqLYbal0NkG4HiGFtAg-wp2lfsXbdfXAYYeFl8QyWqpmSMYZf8YsNa-9I3-fFb2MExxT7yh_peH1Q", "sam@gmail.com");
	}
	

}
