package com.cts.eventList.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "venues")
public class Venue {

    private String name;
    private String address;
    private String city;
    private String capacity;
    private String country;
}
