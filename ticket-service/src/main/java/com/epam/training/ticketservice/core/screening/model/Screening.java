package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.MovieServiceImp;
import com.epam.training.ticketservice.core.movie.model.Movie;
import lombok.Value;

import java.util.Optional;

@Value
public class Screening {
    String movieTitle;
    String roomName;
    String screeningBegins;

    public String toString() {
        MovieServiceImp movieService = new MovieServiceImp();
        Movie movie = movieService.getMovieByTitle(movieTitle);
        return movie + ", screened in room " + roomName + ", at " + screeningBegins;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String movieTitle;
        private String roomName;
        private String screeningBegins;

        public Builder withMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
            return this;
        }

        public Builder withRoomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder withScreeningBegins(String screeningBegins) {
            this.screeningBegins = screeningBegins;
            return this;
        }

        public Screening build() {
            return new Screening(movieTitle,roomName,screeningBegins);
        }
    }
}
