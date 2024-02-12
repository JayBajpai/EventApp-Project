package com.cts.eventList.controller;


import com.cts.eventList.entity.Event;
import com.cts.eventList.service.SeatGeekService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/events")
public class SeatGeekController {

    private final SeatGeekService seatGeekService;

    @Autowired
    public SeatGeekController(SeatGeekService seatGeekService) {
        this.seatGeekService = seatGeekService;
    }

    @GetMapping("/allEvents")

    public ResponseEntity<List<Event>> getAllEvents(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        List<Event> events = seatGeekService.getAllEvents(token);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Event> getEventById(@RequestHeader("Authorization") String token, @PathVariable Long eventId) {
        Event event = seatGeekService.getEventById(token, eventId);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}