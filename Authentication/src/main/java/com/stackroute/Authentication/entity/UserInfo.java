package com.stackroute.Authentication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Table
@Entity
public class UserInfo {
	@Id
	@NotNull(message = "Username cannot be null")
	@Size(min = 1, message = "Username cannot be empty")
	String username;
	@NotNull(message = "Password cannot be null")
	@Size(min = 1, message = "Password cannot be empty")
	String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserInfo(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public UserInfo() {
	}
	@Override
	public String toString() {
		return "UserInfo{" + "username='" +username + '\''+",password='" + password + '\''+'}';
 	}
}
