package com.epam.training.ticketservice.core.screening.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Screening {
    @Id
    @GeneratedValue
    private long id;
    private String movieTitle;
    private String roomName;
    private String screeningBegins;

    public Screening(String movieTitle, String roomName, String screeningBegins) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.screeningBegins = screeningBegins;
    }
}
