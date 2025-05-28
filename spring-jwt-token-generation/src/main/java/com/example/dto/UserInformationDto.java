package com.example.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserInformationDto {
	
	@Id
	private String emailId;
	
	private String password;
	
	private  String mobileNumber;
	
	private String userName;


}
