package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.commandprompt.commands.checks.CheckService;
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
    private final CheckService checkService;

    @ShellMethod(key = "create movie",value = "Create a new movie")
    public Movie createMovie(String title, String genre, Integer lengthInMinutes) throws Exception {
        if (movieService.getMovieByTitle(title) != null) {
            throw new Exception("There already is a movie with that title");
        }
        Movie movie = new Movie(title,genre,lengthInMinutes);
        movieService.createMovie(movie);
        return movie;
    }

    @ShellMethod(key = "update movie",value = "Update a movie")
    public Movie updateMovie(String title,String genre, Integer lengthInMinutes) throws Exception {
        checkService.checkMovie(title);
        movieService.updateMovie(title, genre, lengthInMinutes);
        return new Movie(title, genre, lengthInMinutes);
    }

    @ShellMethod(key = "delete movie",value = "Delete a movie")
    public void deleteMovie(String title) throws Exception {
        checkService.checkMovie(title);
        movieService.deleteMovie(title);
    }

    @ShellMethod(key = "list movies",value = "List of movies")
    public void listMovies() throws Exception {
        if (movieService.getMovieList() == null) {
            throw new Exception("There are no movies at the moment");
        }
        movieService.getMovieList().forEach(System.out::println);
    }
}
