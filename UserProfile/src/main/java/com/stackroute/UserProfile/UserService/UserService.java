package com.stackroute.UserProfile.UserService;

import com.stackroute.UserProfile.UserEntity.User;

public interface UserService {

	//create

    User getUserById(long userId);

    User saveUser(User user);

    User updateUser(long userId, User user);


	
}
