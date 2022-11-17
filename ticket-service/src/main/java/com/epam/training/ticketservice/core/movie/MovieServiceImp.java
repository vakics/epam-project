package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImp implements MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieByTitle(String title) {
        return convertEntityToDto(movieRepository.findByTitle(title));
    }

    @Override
    public void createMovie(MovieDto movieDto) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            Movie movie = new Movie(movieDto.getTitle(), movieDto.getGenre(), movieDto.getLengthInMinutes());
            movieRepository.save(movie);
        }
    }

    @Override
    public void updateMovie(String title,String genre,Integer lengthInMinutes) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            deleteMovie(title);
            createMovie(new MovieDto(title, genre, lengthInMinutes));
        }
    }

    @Override
    public void deleteMovie(String title) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            movieRepository.delete(movieRepository.findByTitle(title));
            getMovieList();
        }
    }

    private MovieDto convertEntityToDto(Movie movie) {
        return MovieDto.builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withLengthInMinutes(movie.getLengthInMinutes())
                .build();
    }

    private Optional<MovieDto> convertEntityToDto(Optional<Movie> movie) {
        return movie.isEmpty() ? Optional.empty() : Optional.of(convertEntityToDto(movie.get()));
    }
}
