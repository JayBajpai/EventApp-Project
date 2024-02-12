package com.stackroute.Authentication.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stackroute.Authentication.entity.UserInfo;
import com.stackroute.Authentication.repo.UserRepo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;


    /**
     * Method under test: {@link UserServiceImpl#loginUser(String, String)}
     */
    @Test
    void testLoginUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword("password");
        userInfo.setUsername("janedoe");
        Optional<UserInfo> ofResult = Optional.of(userInfo);
        when(userRepo.findByUsernameAndPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        boolean actualLoginUserResult = userServiceImpl.loginUser("janedoe", "password");
        verify(userRepo).findByUsernameAndPassword(Mockito.<String>any(), Mockito.<String>any());
        assertTrue(actualLoginUserResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#loginUser(String, String)}
     */
    @Test
    void testLoginUser2() {
        Optional<UserInfo> emptyResult = Optional.empty();
        when(userRepo.findByUsernameAndPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);
        boolean actualLoginUserResult = userServiceImpl.loginUser("janedoe", "password");
        verify(userRepo).findByUsernameAndPassword(Mockito.<String>any(), Mockito.<String>any());
        assertFalse(actualLoginUserResult);
    }
}