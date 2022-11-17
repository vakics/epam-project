package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class ScreeningDto {
    String movieTitle;
    String roomName;
    String screeningBegins;

    MovieRepository movieRepository;

    public String toString() {
        Movie movie = movieRepository.findByTitle(this.movieTitle);
        return movie + ", screened in room " + roomName + ", at " + screeningBegins;
    }
}
