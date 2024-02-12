package com.WishlistService.service;

import com.WishlistService.entity.Wishlist;
import com.WishlistService.repo.WishlistRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class WishlistServiceTest {

//    @Mock
//    private RestTemplate restTemplate;
//
//    @Mock
//    private WishlistRepo wishlistRepo;
//
//    @InjectMocks
//    private WishlistService wishlistService;
//
//    @Test
//    public void testAddToWishlist() {
//        // Mock data
//        Long eventId = 1L;
//        String jwtToken = "mockJwtToken";
//        Wishlist mockWishlist = new Wishlist();
//
//        // Mocking restTemplate behavior
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Wishlist.class)))
//                .thenReturn(new ResponseEntity<>(mockWishlist, HttpStatus.OK));
//
//        // Mocking repository behavior
//        when(wishlistRepo.save(any(Wishlist.class))).thenReturn(mockWishlist);
//
//        // Perform the test
//        Wishlist result = wishlistService.addToWishlist(eventId, jwtToken);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals(mockWishlist, result);
//        verify(wishlistRepo, times(1)).save(any(Wishlist.class));
//    }
//
//    @Test
//    public void testGetAllWishlistItems() {
//        // Mock data
//        String token = "mockToken";
//        Wishlist mockWishlist = new Wishlist();
//
//        // Mocking restTemplate behavior
//        doNothing().when(wishlistService).validateToken(token);
//
//        // Mocking repository behavior
//        when(wishlistRepo.findAll()).thenReturn(Collections.singletonList(mockWishlist));
//
//        // Perform the test
//        List<Wishlist> result = wishlistService.getAllWishlistItems(token);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(mockWishlist, result.get(0));
//    }
//
//    @Test
//    public void testRemoveFromWishlist() {
//        // Mock data
//        Long eventId = 1L;
//        String jwtToken = "mockJwtToken";
//        Wishlist mockWishlist = new Wishlist();
//
//        // Mocking repository behavior
//        when(wishlistRepo.findById(eventId)).thenReturn(Optional.of(mockWishlist));
//        doNothing().when(wishlistService).validateToken(jwtToken);
//        doNothing().when(wishlistRepo).delete(mockWishlist);
//
//        // Perform the test
//        wishlistService.removeFromWishlist(eventId, jwtToken);
//
//        // Verify repository method calls
//        verify(wishlistRepo, times(1)).findById(eventId);
//        verify(wishlistRepo, times(1)).delete(mockWishlist);
//    }
//
//    @Test
//    public void testRemoveFromWishlistNotFound() {
//        // Mock data
//        Long eventId = 1L;
//        String jwtToken = "mockJwtToken";
//
//        // Mocking repository behavior
//        when(wishlistRepo.findById(eventId)).thenReturn(Optional.empty());
//
//        // Perform the test and expect NotFoundException
//        assertThrows(NotFoundException.class, () -> wishlistService.removeFromWishlist(eventId, jwtToken));
//    }
//
//    // Reflection approach to test private method (for reference, not recommended)
//    @Test
//    public void testPrivateMethodUsingReflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        // Mock data
//        String token = "mockToken";
//
//        // Use reflection to access the private method
//        Method validateTokenMethod = WishlistService.class.getDeclaredMethod("validateToken", String.class);
//        validateTokenMethod.setAccessible(true);  // Allow access to private method
//
//        // Mocking restTemplate behavior
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
//                .thenReturn(new ResponseEntity<>("mockResponse", HttpStatus.OK));
//
//        // Perform the test
//        validateTokenMethod.invoke(wishlistService, token);
//
//        // Assertions or verifications if needed
//    }
}