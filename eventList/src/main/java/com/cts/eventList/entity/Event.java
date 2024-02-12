package com.cts.eventList.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    private String type;
    private long id;
    private String title;
    private String datetime_utc;
    private Venue venue;


}
