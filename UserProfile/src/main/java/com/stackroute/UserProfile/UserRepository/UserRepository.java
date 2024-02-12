package com.stackroute.UserProfile.UserRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.relational.core.mapping.Table;
import com.stackroute.UserProfile.UserEntity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
}
 