package com.WishlistService.controller;

import com.WishlistService.entity.Wishlist;
import com.WishlistService.service.WishlistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.WatchService;
import java.util.List;

//@RestController
//@RequestMapping("/wishlist")
//public class WishlistController {
//
//    private final WishlistService wishlistService;
//
//    @Autowired
//    public WishlistController(WishlistService wishlistService) {
//        this.wishlistService = wishlistService;
//    }
//
//    @PostMapping("/add/{eventId}")
//    public ResponseEntity<List<Wishlist>> addToWishlist() throws JsonProcessingException {
//        return new  wishlistService.addToWishlist();
//    }
//}
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {

        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam Long eventId,
                                                  @RequestHeader("Authorization") String jwtToken) {
            Wishlist wishlistItems = wishlistService.addToWishlist(eventId, jwtToken);
            return ResponseEntity.ok((Wishlist) wishlistItems);


    }
    @GetMapping("/all")
    public ResponseEntity<List<Wishlist>> getAllEvents(@RequestHeader("Authorization") String token) {
        List<Wishlist> wishlistItems = wishlistService.getAllWishlistItems(token);
        return ResponseEntity.ok(wishlistItems);
    }
    @DeleteMapping("/remove/{eventId}")
    public ResponseEntity<Object> removeFromWishlist(@PathVariable Long eventId,
                                                     @RequestHeader("Authorization") String jwtToken) {
        wishlistService.removeFromWishlist(eventId, jwtToken);
        return ResponseEntity.ok().build(); // Respond with an empty body
    }
    }