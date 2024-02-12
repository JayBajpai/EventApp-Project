package com.stackroute.Authentication.service;

import com.stackroute.Authentication.entity.UserInfo;

public interface UserService {

	UserInfo fetchUserDetails(UserInfo user);

	boolean loginUser(String username, String password);

}
