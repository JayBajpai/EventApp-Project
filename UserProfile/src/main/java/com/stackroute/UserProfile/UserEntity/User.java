package com.stackroute.UserProfile.UserEntity;

//import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long userId;

	@NotBlank(message = "First name cannot be blank")
	@Size(max = 255, message = "First name cannot exceed 255 characters")
	@Column(name = "FIRSTNAME")
	private String firstName;

	@NotBlank(message = "Last name cannot be blank")
	@Size(max = 255, message = "Last name cannot exceed 255 characters")
	@Column(name = "LASTNAME")
	private String lastName;

	@NotBlank(message = "Username cannot be blank")
	@Size(max = 255, message = "Username cannot exceed 255 characters")
	@Column(unique = true)
	private String username;

	@NotBlank(message = "Country cannot be blank")
	@Size(max = 255, message = "Country cannot exceed 255 characters")
	@Column(name = "COUNTRY")
	private String country;

	@NotBlank(message = "Password cannot be blank")
	@Size(min = 6, message = "Password must be at least 6 characters")
	@Column(name = "PASSWORD")
	private String password;

	@NotBlank(message = "Email ID cannot be blank")
	@Email(message = "Invalid email format")
	@Size(max = 255, message = "Email ID cannot exceed 255 characters")
	@Column(name = "EMAILID")
	private String emailId;

	@NotBlank(message = "Phone cannot be blank")
	@Size(max = 20, message = "Phone cannot exceed 20 characters")
	@Column(name = "PHONE")
	private String phone;

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password; 
	}
	public String getEmailId() { 
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public User(long userId, String firstName, String lastName, String username, String country, String password,
			String emailId, String phone) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.country = country;
		this.password = password;
		this.emailId = emailId;
		this.phone = phone;
	}
	public User() {
		super();

	}
  

	

	
	
}
 
 
