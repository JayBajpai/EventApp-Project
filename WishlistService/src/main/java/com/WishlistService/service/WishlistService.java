package com.WishlistService.service;

import com.WishlistService.entity.Wishlist;
import com.WishlistService.exception.UnauthorizedException;
import com.WishlistService.repo.WishlistRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@Service
//public class WishlistService {
//
//    private final WishlistRepo wishlistRepo;
//    private final RestTemplate restTemplate;
//
//    @Value("${wishlist.api.base-url}")
//    private String baseUrl;
//
//
//    @Autowired
//    public WishlistService(WishlistRepo wishlistRepo,
//                           RestTemplate restTemplate) {
//        this.wishlistRepo = wishlistRepo;
//        this.restTemplate = restTemplate;
//    }
//    public List<Wishlist> addToWishlist(String eventId) throws JsonProcessingException {
//        String url = baseUrl + "events/events/" + eventId;
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> data = mapper.readValue(response.getBody(), Map.class);
//        List<Map<String, Object>> events = (List<Map<String, Object>>) data.get("events");
//        List<Wishlist> savedEvents = new ArrayList<>();
//        for (Map<String, Object> eventMap : events) {
//            Wishlist event = createEventFromMap(eventMap);
//            wishlistRepo.save(event);
//            savedEvents.add(event);
//        }
//
//        return savedEvents;
//    }
//    private Wishlist createEventFromMap(Map<String, Object> eventMap) {
//        Wishlist event = new Wishlist();
//
//        event.setExternalEventId(((Integer) eventMap.get("id")).longValue());
//        event.setType((String) eventMap.get("type"));
//        event.setTitle((String) eventMap.get("title"));
//        event.setDatetimeUtc((String) eventMap.get("datetime_utc"));
//        return event;
//    }
//    }

@Service
public class WishlistService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WishlistRepo wishlistRepo;
    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Autowired
    public WishlistService(RestTemplate restTemplate, WishlistRepo wishlistRepo, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.wishlistRepo = wishlistRepo;
        this.objectMapper = objectMapper;
    }


    public Wishlist addToWishlist(Long eventId, String jwtToken) {

        String apiUrl = "http://localhost:8082/api/v1.0/events/events/" + eventId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Wishlist> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Wishlist.class);
        Wishlist wishlist = response.getBody();

        return wishlistRepo.save(wishlist);

    }

    private void validateToken(String token) {
        ResponseEntity<String> validationResponse = restTemplate.exchange(
                authServiceUrl + "/verifyToken", HttpMethod.GET, createHttpEntityWithToken(token), String.class);

        if (validationResponse == null) {
            throw new RuntimeException("Error communicating with auth service");
        }
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    private HttpEntity<String> createHttpEntityWithToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new HttpEntity<>(headers);
    }

    public List<Wishlist> getAllWishlistItems(String token) {
        try {
            validateToken(token);
            return wishlistRepo.findAll();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw e;
            }
        }
    }
    public void removeFromWishlist(Long eventId, String jwtToken) {
        // Check if the event exists in the wishlist
        Optional<Wishlist> wishlistItemOptional = wishlistRepo.findById(eventId);
        if (wishlistItemOptional.isPresent()) {
            Wishlist wishlistItem = wishlistItemOptional.get();
            validateToken(jwtToken);
            wishlistRepo.delete(wishlistItem);
        } else {
            throw new NotFoundException("Event with ID " + eventId + " not found in the wishlist");
        }
    }
}