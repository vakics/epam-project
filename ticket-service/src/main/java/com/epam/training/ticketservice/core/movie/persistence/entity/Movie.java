package com.epam.training.ticketservice.core.movie.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Movie {
    @Id
    private String title;
    private String genre;
    private Integer lengthInMinutes;

    public Movie(String title, String genre, Integer lengthInMinutes) {
        this.title = title;
        this.genre = genre;
        this.lengthInMinutes = lengthInMinutes;
    }
}
