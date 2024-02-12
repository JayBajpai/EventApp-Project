package com.stackroute.Authentication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.Authentication.entity.UserInfo;

@Repository
public interface UserRepo extends JpaRepository<UserInfo,String> {
	
	Optional<UserInfo> findByUsernameAndPassword(String username,String pass);

}

