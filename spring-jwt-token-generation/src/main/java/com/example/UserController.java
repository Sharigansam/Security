package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ChangePasswordDto;
import com.example.dto.UserInformationDto;
import com.example.dto.UserLoginDto;
import com.example.service.UserService;

import jakarta.annotation.PostConstruct;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/public/user/signup")
	public String signUpUser(@RequestBody UserInformationDto userInformation) {
		
		return userService.singnUpuser(userInformation);
	}
	
	@PostMapping("/public/user/signin")
	public String signIn(@RequestBody UserLoginDto userLoginDto) {
		
		return userService.singnIn(userLoginDto);
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
