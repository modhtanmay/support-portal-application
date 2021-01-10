package com.tanmay.supportportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.supportportal.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findUserByUserName(String username);
	
	User findUserByEmail(String email);

}
