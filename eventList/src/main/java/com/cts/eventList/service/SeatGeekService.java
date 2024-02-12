package com.cts.eventList.service;


import com.cts.eventList.entity.Event;
import com.cts.eventList.entity.EventList;
import com.cts.eventList.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;



import java.util.Collections;
import java.util.List;

@Service
public class SeatGeekService {
   private final RestTemplate restTemplate;
   @Value("${seatgeek.api.base-url}")
   private String baseUrl;
   @Value("${seatgeek.api.client-id}")
    private String clientId;
    @Value("${seatgeek.api.client-secret}")
    private String clientSecret;
    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Autowired
    public SeatGeekService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   public List<Event> getAllEvents(String token) {
        try {
           validateToken(token);
            String url = baseUrl + "events?client_id={clientId}&client_secret={clientSecret}";
            ResponseEntity<EventList> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, createHttpEntityWithToken(token), EventList.class, clientId, clientSecret);
          EventList eventList = responseEntity.getBody();
           return eventList.getEvents();
        } catch (HttpClientErrorException e) {
          if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
              return Collections.emptyList();
            } else {
               throw e;
           }
       }
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
   public Event getEventById(String token, Long eventId) {
        try {
            validateToken(token);
           String url = baseUrl + "events/{eventId}?client_id={clientId}&client_secret={clientSecret}";
            ResponseEntity<Event> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, createHttpEntityWithToken(token), Event.class, eventId, clientId, clientSecret);
           return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
               throw e;
            }
        }
    }
}

