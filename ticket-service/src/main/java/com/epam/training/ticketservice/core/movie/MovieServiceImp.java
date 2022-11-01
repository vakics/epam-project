package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieServiceImp implements MovieService {
    private List<Movie> movies = new LinkedList<Movie>(List.of(
            Movie.builder()
                    .withTitle("Encanto")
                    .withGenre("mese")
                    .withLengthInMinutes(123)
                    .build(),
            Movie.builder()
                    .withTitle("Black Adam")
                    .withGenre("akcio")
                    .withLengthInMinutes(234)
                    .build()
    ));

    @Override
    public List<Movie> getMovieList() {
        return movies;
    }

    @Override
    public Optional<Movie> getMovieByTitle(String title) {
        return movies.stream().filter(movie -> movie.getTitle().equals(title)).findFirst();
    }

    @Override
    public void createMovie(Movie movie) {
        movies.add(movie);
    }

    @Override
    public void updateMovie(String title,String genre,Integer lengthInMinutes) {
        deleteMovie(title);
        createMovie(Movie.builder().withTitle(title).withGenre(genre).withLengthInMinutes(lengthInMinutes).build());
    }

    @Override
    public List<Movie> deleteMovie(String title) {
        movies = movies.stream().filter(movie1 -> !movie1.getTitle().equals(title)).collect(Collectors.toList());
        return movies;
    }
}
