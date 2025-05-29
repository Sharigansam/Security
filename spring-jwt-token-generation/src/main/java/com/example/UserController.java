package com.example;

import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ChangePasswordDto;
import com.example.dto.UserInformationDto;
import com.example.dto.UserLoginDto;
import com.example.security.JWTUtil;
import com.example.service.UserService;

import jakarta.annotation.PostConstruct;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/public/user/signup")
	public String signUpUser(@RequestBody UserInformationDto userInformation) {
		
		return userService.singnUpuser(userInformation);
	}
	
	@PostMapping("/public/user/signin")
	public ResponseEntity<String> signIn(@RequestBody UserLoginDto userLoginDto) {
		
		//Validation of Credentials : username & password combo
		String response = null;
		
		UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(userLoginDto.getEmailId(), userLoginDto.getPassword());
		
		try {
			Authentication authentication = authenticationManager.authenticate(credentials);
			
			System.out.println(authentication.getCredentials());
		}
		catch(BadCredentialsException ex) {
			System.err.println("UserController :Bad CredentialsException");
			 response = "Invalid Credentials";
			 
			 return new ResponseEntity(response,HttpStatusCode.valueOf(401));
		}
		
		String token = jwtUtil.createToken(userLoginDto.getEmailId());
		
		org.springframework.http.HttpHeaders responseHeaders =  new org.springframework.http.HttpHeaders();
		
		responseHeaders.add("Authorization",token);
		
		
		
		return new ResponseEntity<String>("Welcome to Home"+userLoginDto.getEmailId(),responseHeaders,HttpStatusCode.valueOf(200));
	}
	
	//api/user/change/password
	
	@PostMapping("/api/user/change/password/{userId}")
	public String changePassword(@RequestBody ChangePasswordDto changePasswordDto,@PathVariable String userId)  {
		
		return userService.changePassword(changePasswordDto,userId);
	}
	
	
	@DeleteMapping("/api/user/delete/{userId}")
	public String deleteUser(@PathVariable String userId)  {
		
		return userService.deleteUser(userId);
	}
	
	
	
	

}
