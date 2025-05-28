package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ChangePasswordDto;
import com.example.dto.UserInformationDto;
import com.example.dto.UserLoginDto;
import com.example.entity.UserInformation;
import com.example.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public String singnUpuser(UserInformationDto userInformation) {

		if (userRepository.findById(userInformation.getEmailId()).isPresent()) {

			return "Email Already Existed";
		} else {
			UserInformation userDetails = new UserInformation(userInformation.getEmailId(), userInformation.getPassword(),
					userInformation.getMobileNumber(), userInformation.getUserName());
			userRepository.save(userDetails);

		}

		return "User Created Successfully";

	}

	public String singnIn(UserLoginDto userLoginDto) {

		UserInformation userDetals = userRepository.findByEmailIdAndPassword(userLoginDto.getEmailId(),
				userLoginDto.getPassword());

		if (userDetals != null) {
			return "User lOgin success.." + userDetals.getFullName();

		}

		return "Invalid Credentials";
	}

	public String changePassword(ChangePasswordDto changePasswordDto, String usderId) {

		Optional<UserInformation> info = userRepository.findById(usderId);

		if (info.isPresent()) {
			UserInformation userDetails = info.get();
			userDetails.setPassword(changePasswordDto.getNewPassword());
			userRepository.save(userDetails);

			return "password changed successfully";

		} else {

			return "User not found with EmailId : " + usderId;
		}

	}

	public String deleteUser(String usderId) {

		Optional<UserInformation> info = userRepository.findById(usderId);

		if (info.isPresent()) {
			userRepository.deleteById(usderId);

			return "User Deleted successfully";

		}
		else {
			
			return "user Not Found with email : " +usderId;
		}
	}

}
