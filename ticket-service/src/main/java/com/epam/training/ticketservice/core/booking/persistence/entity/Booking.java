package com.epam.training.ticketservice.core.booking.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private Long id;
    private String movieTitle;
    private String roomName;
    private String screeningBegins;
    private String seatsBooked;
    private String userName;
    private int basePrice;

    public Booking(String movieTitle, String roomName, String screeningBegins, String seatsBooked, String userName,
                   int basePrice) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.screeningBegins = screeningBegins;
        this.seatsBooked = seatsBooked;
        this.userName = userName;
        this.basePrice = basePrice;
    }
}
