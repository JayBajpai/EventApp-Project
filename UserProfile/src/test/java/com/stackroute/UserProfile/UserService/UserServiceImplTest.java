package com.stackroute.UserProfile.UserService;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.stackroute.UserProfile.UserEntity.User;
import com.stackroute.UserProfile.UserException.UserAlreadyExistException;
import com.stackroute.UserProfile.UserException.UserNotFoundException;
import com.stackroute.UserProfile.UserRepository.UserRepository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class, Gson.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @Test
    void testGetUserById() {
        User user = new User();
        user.setCountry("GB");
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("abcd");
        user.setPhone("6625550144");
        user.setUserId(1L);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        User actualUserById = userServiceImpl.getUserById(1L);
        verify(userRepository).findById(Mockito.<Long>any());
        assertSame(user, actualUserById);
    }


    @Test
    void testGetUserById2() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(1L));  
        verify(userRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testGetUserById3() {
        when(userRepository.findById(Mockito.<Long>any())).thenThrow(new UserAlreadyExistException("An error occurred"));
        assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.getUserById(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testSaveUser() {
        User user = new User();
        user.setCountry("GB");
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("abcd");
        user.setPhone("6625550144");
        user.setUserId(1L);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setCountry("GB");
        user2.setEmailId("42");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("abcd");
        user2.setPhone("6625550144");
        user2.setUserId(1L);
        user2.setUsername("janedoe");
        assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.saveUser(user2));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }


    @Test
    void testSaveUser2() {
        when(userRepository.findByUsername(Mockito.<String>any()))
                .thenThrow(new UserNotFoundException("An error occurred"));

        User user = new User();
        user.setCountry("GB");
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("abcd");
        user.setPhone("6625550144");
        user.setUserId(1L);
        user.setUsername("janedoe");
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.saveUser(user));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }



    @Test
    void testUpdateUser() {
        User user = new User();
        user.setCountry("GB");
        user.setEmailId("42");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("abcd");
        user.setPhone("6625550144");
        user.setUserId(1L);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setCountry("GB");
        user2.setEmailId("42");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("abcd");
        user2.setPhone("6625550144");
        user2.setUserId(1L);
        user2.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setCountry("GB");
        user3.setEmailId("42");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setPassword("abvd");
        user3.setPhone("6625550144");
        user3.setUserId(1L);
        user3.setUsername("janedoe");
        User actualUpdateUserResult = userServiceImpl.updateUser(1L, user3);
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertSame(user2, actualUpdateUserResult);
    }



}
