package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.Value;

@Value
public class ScreeningDto {
    String movieTitle;
    String roomName;
    String screeningBegins;

    MovieService movieService;

    public String toString() {
        MovieDto movie = movieService.getMovieByTitle(this.movieTitle);
        return movie + ", screened in room " + roomName + ", at " + screeningBegins;
    }
}
