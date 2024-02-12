package com.WishlistService.controller;

import com.WishlistService.entity.Wishlist;
import com.WishlistService.service.WishlistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    @Test
    public void addToWishlist_Success() {
        Long eventId = 1L;
        String jwtToken = "valid_token";
        Wishlist expectedWishlist = new Wishlist(); // Create a sample wishlist object
        when(wishlistService.addToWishlist(eventId, jwtToken)).thenReturn(expectedWishlist);

        ResponseEntity<Wishlist> response = wishlistController.addToWishlist(eventId, jwtToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedWishlist, response.getBody());
    }
    @Test
    public void getAllEvents_Success() {
        String token = "valid_token";
        List<Wishlist> expectedWishlistItems = Arrays.asList(new Wishlist(), new Wishlist()); // Create a sample list
        when(wishlistService.getAllWishlistItems(token)).thenReturn(expectedWishlistItems);

        ResponseEntity<List<Wishlist>> response = wishlistController.getAllEvents(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedWishlistItems, response.getBody());
    }
    @Test
    public void removeFromWishlist_Success() {
        Long eventId = 1L;
        String jwtToken = "valid_token";

        ResponseEntity<Object> response = wishlistController.removeFromWishlist(eventId, jwtToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody()); // Assert empty body
    }

}
