package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getMovieList();

    Optional<Movie> getMovieByTitle(String title);

    void createMovie(Movie movie);

    void updateMovie(String title,String genre,Integer lengthInMinutes);

    void deleteMovie(String title);
}
