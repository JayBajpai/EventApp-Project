package com.stackroute.UserProfile.UserService;

import java.util.Optional;

import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.stackroute.UserProfile.UserEntity.User;
import com.stackroute.UserProfile.UserException.UserAlreadyExistException;
import com.stackroute.UserProfile.UserException.UserNotFoundException;
import com.stackroute.UserProfile.UserRepository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private Gson gson;
    
    @Autowired 
    private KafkaTemplate<String, String> kafkaTemplate;
     
    private static final String TOPIC = "eventapp";
	
	@Override
	public User getUserById(long userId) {
		// TODO Auto-generated method stub
		 return userRepository.findById(userId)
	                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new UserAlreadyExistException("username already exists: " + user.getUsername());
		}
		userRepository.save(user);
		   kafkaTemplate.send(TOPIC, gson.toJson(user));
		return user;
	}

	@Override
	 public User updateUser(long userId, User user) {

	        // Check if the user with the given userId exists
	        Optional<User> optionalUser = userRepository.findById(userId);
	        if (optionalUser.isPresent()) {
	            User existingUser = optionalUser.get();
	            // Update the fields with the new values
	            existingUser.setUsername(user.getUsername());
	            existingUser.setFirstName(user.getFirstName());
	            existingUser.setLastName(user.getLastName());
	            existingUser.setPhone(user.getPhone());
	            existingUser.setPassword(user.getPassword());
	            existingUser.setEmailId(user.getEmailId());
	            existingUser.setCountry(user.getCountry());

	            // Save the updated user
	            return userRepository.save(existingUser);
	        } else {
	            throw new UserNotFoundException("User not found with id: " + userId);
	        }
	}
}
	