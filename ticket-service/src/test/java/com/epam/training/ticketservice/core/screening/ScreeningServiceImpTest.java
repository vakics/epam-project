package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningServiceImpTest {
    private final MovieService movieService = Mockito.mock(MovieService.class);
    private static final Screening SCREENINGENTITY = new Screening("Encanto",
            "Wermin","2022-11-20 11:00");
    private final ScreeningDto SCREENINGDTO = new ScreeningDto("Encanto",
            "Wermin","2022-11-20 11:00", movieService);
    private final MovieDto MOVIEDTO = new MovieDto("Encanto","animation",99);
    private final ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
    private final ScreeningServiceImp underTest= new ScreeningServiceImp(movieService,screeningRepository);

    @Test
    void testGetScreenings() {
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(SCREENINGENTITY));
        List<ScreeningDto> expected = List.of(SCREENINGDTO);
        List<ScreeningDto> actual = underTest.getScreenings();
        assertEquals(expected,actual);
        Mockito.verify(screeningRepository).findAll();
    }

    @Test
    void testGetScreening() {
        Mockito.when(screeningRepository.findByMovieTitleAndRoomNameAndScreeningBegins(SCREENINGDTO.getMovieTitle(),
                SCREENINGDTO.getRoomName(), SCREENINGDTO.getScreeningBegins())).thenReturn(SCREENINGENTITY);
        ScreeningDto actual = underTest.getScreening(SCREENINGDTO.getMovieTitle(), SCREENINGDTO.getRoomName(),
                SCREENINGDTO.getScreeningBegins());
        assertEquals(SCREENINGDTO,actual);
        Mockito.verify(screeningRepository).findByMovieTitleAndRoomNameAndScreeningBegins(SCREENINGDTO.getMovieTitle(),
                SCREENINGDTO.getRoomName(), SCREENINGDTO.getScreeningBegins());
    }

    @Test
    void testCreateScreening() {
        Mockito.when(screeningRepository.save(SCREENINGENTITY)).thenReturn(SCREENINGENTITY);
        Screening actual = underTest.createScreening(SCREENINGDTO);
        assertEquals(SCREENINGENTITY,actual);
        Mockito.verify(screeningRepository).save(SCREENINGENTITY);
    }

    @Test
    void testDeleteScreening() {
        Mockito.when(screeningRepository.findByMovieTitleAndRoomNameAndScreeningBegins(
                SCREENINGDTO.getMovieTitle(), SCREENINGDTO.getRoomName(), SCREENINGDTO.getScreeningBegins())).thenReturn(SCREENINGENTITY);
        underTest.deleteScreening(SCREENINGDTO.getMovieTitle(), SCREENINGDTO.getRoomName(), SCREENINGDTO.getScreeningBegins());
        Mockito.verify(screeningRepository).findByMovieTitleAndRoomNameAndScreeningBegins(
                SCREENINGDTO.getMovieTitle(), SCREENINGDTO.getRoomName(), SCREENINGDTO.getScreeningBegins());
    }

    @Test
    void testIsOverLappingShouldReturnFalseWithOneScreening() throws ParseException {
        Mockito.when(movieService.getMovieByTitle(SCREENINGDTO.getMovieTitle())).thenReturn(MOVIEDTO);
        boolean actual = underTest.isOverlapping(SCREENINGDTO.getScreeningBegins(), SCREENINGDTO.getMovieTitle(),
                SCREENINGDTO.getRoomName());
        assertFalse(actual);
        Mockito.verify(movieService).getMovieByTitle(MOVIEDTO.getTitle());
    }

    @Test
    void testIsOverLappingShouldReturnFalseWithTwoScreeningsInDifferentRooms() throws ParseException {
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(SCREENINGENTITY));
        Mockito.when(movieService.getMovieByTitle("Encanto")).thenReturn(MOVIEDTO);
        boolean actual = underTest.isOverlapping("2022-11-24 11:00","Encanto",
                "Moldova");
        assertFalse(actual);
        Mockito.verify(screeningRepository).findAll();
        Mockito.verify(movieService).getMovieByTitle("Encanto");
    }

    @Test
    void testisOverLappingShouldReturnTrueIfTwoMoviesOverlap() throws ParseException {
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(SCREENINGENTITY));
        Mockito.when(movieService.getMovieByTitle("Encanto")).thenReturn(MOVIEDTO);
        boolean actual = underTest.isOverlapping("2022-11-20 11:15","Encanto","Wermin");
        assertTrue(actual);
        Mockito.verify(screeningRepository).findAll();
    }

    @Test
    void testisOverLappingShouldReturnTrueIfTwoMoviesOverlapOtherwise() throws ParseException {
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(SCREENINGENTITY));
        Mockito.when(movieService.getMovieByTitle("Encanto")).thenReturn(MOVIEDTO);
        boolean actual = underTest.isOverlapping("2022-11-20 10:15","Encanto","Wermin");
        assertTrue(actual);
        Mockito.verify(screeningRepository).findAll();
    }

    @Test
    void testIsBreakAfterReturnsTrue() throws ParseException {
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(SCREENINGENTITY));
        Mockito.when(movieService.getMovieByTitle("Encanto")).thenReturn(MOVIEDTO);
        boolean actual = underTest.isBreakTimeAfter("Encanto","Wermin",
                "2022-11-20 12:40");
        assertTrue(actual);
        Mockito.verify(screeningRepository).findAll();
    }

    @Test
    void testIsBreakAfterReturnsFalseWhenDifferentRooms() throws ParseException {
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(SCREENINGENTITY));
        Mockito.when(movieService.getMovieByTitle("Encanto")).thenReturn(MOVIEDTO);
        boolean actual = underTest.isBreakTimeAfter("Encanto","Moldova",
                "2022-11-24 11:00");
        assertFalse(actual);
        Mockito.verify(screeningRepository).findAll();
    }
}