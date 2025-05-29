package com.example.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppSecurityConfirguration {
	
	Logger logger = LoggerFactory.getLogger(AppSecurityConfirguration.class);

	//Bean Obj
	@Autowired
	JwtTokenFilter filter;
	
	//password Encoder
	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder() {
		
		System.out.println("BCryptPasswordEncoder:Instance Creation");
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		System.out.println("Creating an Instance of AuthenticationManager");
		return authenticationConfiguration.getAuthenticationManager();
	}

	


	// Security Filter Chain : public, protected api
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception {

        logger.info("Configuring the security rules");

        httpSecurity
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable()) // OR better: configure properly
            .authorizeHttpRequests(reqs -> reqs
                .requestMatchers("/public/**", "/help/**").permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

	}

}
