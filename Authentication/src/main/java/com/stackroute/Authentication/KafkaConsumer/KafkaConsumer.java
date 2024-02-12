package com.stackroute.Authentication.KafkaConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.KafkaListener;
 
 
import com.stackroute.Authentication.entity.UserInfo;
import com.stackroute.Authentication.service.UserService;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

	@Autowired
	private Gson gson;

	@Autowired
	private UserService authUserService;

	//@KafkaListener(topics = "eventapp", groupId = "group_id")
	public void consume(String userMessage) {
		System.out.println("Received message: " + userMessage);

		UserInfo userdata = gson.fromJson(userMessage, UserInfo.class);
		UserInfo result = authUserService.fetchUserDetails(userdata);

		System.out.println("Stored data in User Copy: " + userdata.toString());
	}
}

