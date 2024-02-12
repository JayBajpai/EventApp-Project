package com.stackroute.Authentication.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stackroute.Authentication.config.JwtGenerator;
import com.stackroute.Authentication.exception.UserNotFoundException;
import com.stackroute.Authentication.entity.UserInfo;
import com.stackroute.Authentication.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/authentication")
public class UserController {

	@Autowired
	UserService authUserService;

	@PostMapping("/register")
	public ResponseEntity fetchUserDetails(@RequestBody UserInfo user) {
		UserInfo userdata = authUserService.fetchUserDetails(user);
		if (userdata != null) {
			return new ResponseEntity<UserInfo>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("duplicate id", HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserInfo user) throws UserNotFoundException {

		// checking user credentials will not be null

		if (user.getUsername() == null || user.getPassword() == null) {
			throw new UserNotFoundException("UserId and Password are null");
		}

		//calling autheService from loginUser to authenticate the user

		boolean result = authUserService.loginUser(user.getUsername(), user.getPassword());

		if (result == false) {
			throw new UserNotFoundException("UserName and password mismatch");
		}

		if (result) {
			//generating jwtToken from JWTgenerator
			Map<String, String> token = new JwtGenerator().generateToken(user);
			return new ResponseEntity<Map>(token, HttpStatus.OK);
		} else {
			return new ResponseEntity("Invalid user", HttpStatus.UNAUTHORIZED);
		}
	}
		@GetMapping("/verifyToken")
		public ResponseEntity<String> verifyToken(@RequestHeader(name = "Authorization") String authorizationHeader) {
		// Extract the token from the Authorization header
		String token = authorizationHeader.replace("Bearer ", "");

		JwtGenerator jwtGenerator = new JwtGenerator();
		boolean isTokenValid = jwtGenerator.verifyToken(token);

		if (isTokenValid) {
		return new ResponseEntity<>("Token is valid", HttpStatus.OK);
		} else {
		return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
		}
}
}