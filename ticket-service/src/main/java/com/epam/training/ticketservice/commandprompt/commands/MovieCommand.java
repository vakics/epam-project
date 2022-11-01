package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.Movie;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {
    private final MovieService movieService;

    @ShellMethod(key = "create movie",value = "Create a new movie")
    public Movie createMovie(String title, String genre, Integer lengthInMinutes) {
        Movie movie = new Movie(title,genre,lengthInMinutes);
        movieService.createMovie(movie);
        return movie;
    }

    @ShellMethod(key = "update movie",value = "Update a movie")
    public Movie updateMovie(String title,String genre, Integer lengthInMinutes) {
        movieService.updateMovie(title, genre, lengthInMinutes);
        return new Movie(title, genre, lengthInMinutes);
    }

    @ShellMethod(key = "delete movie",value = "Delete a movie")
    public void deleteMovie(String title) {
        movieService.deleteMovie(title);
    }

    @ShellMethod(key = "list movies",value = "List of movies")
    public List<Movie> listMovies() {
        return movieService.getMovieList();
    }
}
