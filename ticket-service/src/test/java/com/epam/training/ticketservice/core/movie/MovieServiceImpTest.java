package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class MovieServiceImpTest {

    private static final Movie ENTITY = new Movie("Encanto","animation",99);
    private static final MovieDto DTO = new MovieDto.Builder()
            .withTitle("Encanto").withGenre("animation").withLengthInMinutes(99).build();

    private final MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
    private final MovieServiceImp underTest = new MovieServiceImp(movieRepository);

    @Test
    void testGetMovieList() {
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(ENTITY));
        List<MovieDto> expected = List.of(DTO);
        List<MovieDto> actual = underTest.getMovieList();
        Assertions.assertEquals(expected,actual);
        Mockito.verify(movieRepository).findAll();
    }

    @Test
    void testGetMovieByTitleShouldReturnEncantoWhenInputTitleIsEncanto() {
        Mockito.when(movieRepository.findByTitle("Encanto")).thenReturn(ENTITY);
        MovieDto actual = underTest.getMovieByTitle("Encanto");
        Assertions.assertEquals(DTO,actual);
        Mockito.verify(movieRepository).findByTitle("Encanto");
    }
    
    @Test
    void testGetMovieByTitleShouldReturnNullWhenInputTitleShrek() {
        Mockito.when(movieRepository.findByTitle("Shrek")).thenReturn(null);
        MovieDto actual = underTest.getMovieByTitle("Shrek");
        Assertions.assertNull(actual);
        Mockito.verify(movieRepository).findByTitle("Shrek");
    }

    @Test
    void testCreateMovie() {
        Mockito.when(movieRepository.save(ENTITY)).thenReturn(ENTITY);
        Movie actual = underTest.createMovie(DTO);
        Assertions.assertEquals(ENTITY,actual);
        Mockito.verify(movieRepository).save(ENTITY);
    }

    @Test
    void testUpdateMovie() {
        Mockito.when(movieRepository.save(ENTITY)).thenReturn(ENTITY);
        Movie actual = underTest.updateMovie(DTO.getTitle(), DTO.getGenre(), DTO.getLengthInMinutes());
        Assertions.assertEquals(ENTITY,actual);
        Mockito.verify(movieRepository).save(ENTITY);
    }

    @Test
    void testDeleteMovie() {
        Mockito.when(movieRepository.findByTitle(DTO.getTitle())).thenReturn(ENTITY);
        underTest.deleteMovie(DTO.getTitle());
        Mockito.verify(movieRepository).findByTitle(DTO.getTitle());
    }

}