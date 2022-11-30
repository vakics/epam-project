package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningDtoTest {
    private static final MovieService movieService = Mockito.mock(MovieService.class);
    private static final ScreeningDto screeningDto = new ScreeningDto("Encanto","Wermin",
            "2022-11-24 11:00",movieService);


    @Test
    void testToString() {
        MovieDto movie = new MovieDto("Encanto","animation",99);
        Mockito.when(movieService.getMovieByTitle(screeningDto.getMovieTitle())).thenReturn(movie);
        String expected = movie + ", screened in room " + screeningDto.getRoomName() + ", at "
                + screeningDto.getScreeningBegins();
        String actual = screeningDto.toString();
        assertEquals(expected,actual);
    }
}