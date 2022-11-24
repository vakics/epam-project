package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.commandprompt.commands.checks.CheckService;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {
    private final MovieService movieService;
    private final CheckService checkService;
    private final UserService userService;

    @ShellMethod(key = "create movie",value = "Create a new movie")
    public String createMovie(String title, String genre, Integer lengthInMinutes) {
        if (!movieService.getMovieList().isEmpty() && checkService.isExistingMovie(title)) {
            return "There already is a movie with that title";
        }
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            MovieDto movieDto = new MovieDto(title, genre, lengthInMinutes);
            movieService.createMovie(movieDto);
            return movieDto.toString();
        }
        return null;
    }

    @ShellMethod(key = "update movie",value = "Update a movie")
    public String updateMovie(String title, String genre, Integer lengthInMinutes) {
        if (!checkService.isExistingMovie(title)) {
            return "There is no movie with that title";
        }
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            movieService.updateMovie(title, genre, lengthInMinutes);
            return new MovieDto(title, genre, lengthInMinutes).toString();
        }
        return null;
    }

    @ShellMethod(key = "delete movie",value = "Delete a movie")
    public String deleteMovie(String title) {
        if (!checkService.isExistingMovie(title)) {
            return "There is no movie with that title";
        }
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            movieService.deleteMovie(title);
            return title + " successfully deleted";
        }
        return null;
    }

    @ShellMethod(key = "list movies",value = "List of movies")
    public String listMovies() {
        if (movieService.getMovieList().isEmpty()) {
            return "There are no movies at the moment";
        }
        return movieService.getMovieList().stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }
}
