package com.cts.eventList.service;

import com.cts.eventList.entity.Event;
import com.cts.eventList.entity.EventList;
import com.cts.eventList.exception.UnauthorizedException;
import com.cts.eventList.service.SeatGeekService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SeatGeekServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private SeatGeekService seatGeekService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seatGeekService = new SeatGeekService(restTemplate);
    }


}