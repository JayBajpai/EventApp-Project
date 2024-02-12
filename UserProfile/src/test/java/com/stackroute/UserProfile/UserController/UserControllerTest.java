package com.stackroute.UserProfile.UserController;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.UserProfile.UserEntity.User;
import com.stackroute.UserProfile.UserService.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)      // Integrates the Spring TestContext Framework 
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        
     // Create a new User object and set its properties 
        user.setCountry("GB");
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("abc");
        user.setPhone("6625550144");
        user.setUserId(1L);
        user.setUsername("janedoe");
        
        // Configure the userService.saveUser mock to return the created user when called with any User object
        when(userService.saveUser(Mockito.<User>any())).thenReturn(user);
        
        // Call the createUser method on userController with the created user
        userController.createUser(user);

    }


    @Test
    void testGetUserById() throws Exception {
        User user = new User();
        user.setCountry("GB");
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("any");
        user.setPhone("6625550144");
        user.setUserId(1L);
        user.setUsername("janedoe");
        
        // Configure the userService.getUserById mock to return the created user when called with anyLong
         when(userService.getUserById(anyLong())).thenReturn(user);
         
         // Call the getUserById method on userController with a specific user ID (1L)
          userController.getUserById(1L);
    }


    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        user.setCountry("GB");
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("any");
        user.setPhone("6625550144");
        user.setUserId(1L);
        user.setUsername("janedoe");
     // Configure the userService.updateUser mock to return the created user when called with anyLong and any User object
        when(userService.updateUser(anyLong(), Mockito.<User>any())).thenReturn(user);
        
     // Call the updateUser method on userController with a specific user ID (1L) and the created user
        userController.updateUser(1L,user);
    }
}