package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImp implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieByTitle(String title) {
        return convertEntityToDto(movieRepository.findByTitle(title));
    }

    @Override
    public Movie createMovie(MovieDto movieDto) {
        Movie movie = new Movie(movieDto.getTitle(), movieDto.getGenre(), movieDto.getLengthInMinutes());
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(String title,String genre,Integer lengthInMinutes) {
        deleteMovie(title);
        return createMovie(new MovieDto(title, genre, lengthInMinutes));
    }

    @Override
    public void deleteMovie(String title) {
        movieRepository.delete(movieRepository.findByTitle(title));
        getMovieList();
    }

    private MovieDto convertEntityToDto(Movie movie) {
        return movie == null ? null :
                MovieDto.builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withLengthInMinutes(movie.getLengthInMinutes())
                .build();
    }

}
