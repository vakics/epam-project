package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;

public interface MovieService {
    List<MovieDto> getMovieList();

    MovieDto getMovieByTitle(String title);

    void createMovie(MovieDto movieDto);

    void updateMovie(String title,String genre,Integer lengthInMinutes);

    void deleteMovie(String title);
}
