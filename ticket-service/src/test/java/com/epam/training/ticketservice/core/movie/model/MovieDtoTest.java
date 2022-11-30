package com.epam.training.ticketservice.core.movie.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieDtoTest {
    private static final MovieDto DTO = new MovieDto("Encanto","animation",99);

    @Test
    void testToString() {
        String expected = DTO.getTitle() + " (" + DTO.getGenre() + ", " + DTO.getLengthInMinutes() + " minutes)";
        String actual = DTO.toString();
        assertEquals(expected,actual);
    }
}