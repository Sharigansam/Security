package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.entity.UserInformation;

@Repository
public interface UserRepository extends JpaRepository<UserInformation, String> {

	UserInformation findByEmailIdAndPassword(String emailId, String password);

}
