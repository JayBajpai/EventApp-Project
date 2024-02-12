package com.cts.eventList.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import com.cts.eventList.service.SeatGeekService;
import com.cts.eventList.entity.Event;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class SeatGeekControllerTest {

    @InjectMocks
    private SeatGeekController seatGeekController;

    @Mock
    private SeatGeekService seatGeekService;

    @Test
    public void test_getAllEvents_success() throws Exception {
        // Mock service response
        List<Event> expectedEvents = Arrays.asList(new Event(), new Event());
        when(seatGeekService.getAllEvents(any())).thenReturn(expectedEvents);

        // Call the controller method
        ResponseEntity<List<Event>> response = seatGeekController.getAllEvents("validToken");

        // Assert response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEvents, response.getBody());
    }

    @Test
    public void test_getEventById_success() {
        // Mock service response
        Event expectedEvent = new Event();
        when(seatGeekService.getEventById(any(), any())).thenReturn(expectedEvent);

        // Call the controller method
        ResponseEntity<Event> response = seatGeekController.getEventById("validToken", 1L);

        // Assert response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEvent, response.getBody());
    }

    @Test
    public void test_getEventById_notFound() {
        // Mock service response
        when(seatGeekService.getEventById(any(), any())).thenReturn(null);

        // Call the controller method
        ResponseEntity<Event> response = seatGeekController.getEventById("validToken", 1L);

        // Assert response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
