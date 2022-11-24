package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;

import java.util.List;

public interface MovieService {
    List<MovieDto> getMovieList();

    MovieDto getMovieByTitle(String title);

    Movie createMovie(MovieDto movieDto);

    Movie updateMovie(String title,String genre,Integer lengthInMinutes);

    void deleteMovie(String title);
}
