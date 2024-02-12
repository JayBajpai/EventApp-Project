package com.stackroute.Authentication.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.stackroute.Authentication.entity.UserInfo;
import com.stackroute.Authentication.exception.UserNotFoundException;
import com.stackroute.Authentication.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


   

    @Test
    void testLoginUser() throws UserNotFoundException {

        UserController userController = new UserController();

        UserInfo user = new UserInfo();
        user.setPassword(null);
        user.setUsername(null);
        assertThrows(UserNotFoundException.class, () -> userController.loginUser(user));
    }


    @Test
    void testLoginUser2() throws UserNotFoundException {


        UserController userController = new UserController();

        UserInfo user = new UserInfo();
        user.setPassword(null);
        user.setUsername("foo");
        assertThrows(UserNotFoundException.class, () -> userController.loginUser(user));
    }


    @Test
    void testLoginUser3() throws UserNotFoundException {


        UserController userController = new UserController();
        UserInfo user = mock(UserInfo.class);
        when(user.getPassword()).thenThrow(new UserNotFoundException("An error occurred"));
        when(user.getUsername()).thenReturn("any");
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
        user.setPassword("abc");
        user.setUsername("janedoe");
        assertThrows(UserNotFoundException.class, () -> userController.loginUser(user));
        verify(user).getPassword();
        verify(user).getUsername();
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setUsername(Mockito.<String>any());
    }
    @Test
    void testLoginUser_() throws UserNotFoundException {
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword("any");
        userInfo.setUsername("any");
       Mockito.when(userService.loginUser(userInfo.getUsername(), userInfo.getPassword())).thenReturn(true);

      userController.loginUser(userInfo);
    }
    @Test
    void testLoginUser_for_false() throws UserNotFoundException {
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword("any");
        userInfo.setUsername("any");
        Mockito.when(userService.loginUser(userInfo.getUsername(), userInfo.getPassword())).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class,()->userController.loginUser(userInfo));
    }
}