package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.entity.UserInformation;
import com.example.repo.UserRepository;

@Component
public class SecurityUserAuthenticationService  implements UserDetailsService{

	@Autowired
	UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		
		
		//load data from db for a  specific user...
		
		System.out.println("SecurityUserAuthenticationService : load data from db for a  specific user"+emailId);
		
		Optional<UserInformation> id = repository.findById(emailId);
		
		if(id.isPresent()) {
			System.err.println("Email id  found "+ emailId);
			return id.get();
		}
		else {
			System.err.println("Email id not found "+ emailId);
			throw new UsernameNotFoundException(emailId);
		}
		
		
	}
	

}
