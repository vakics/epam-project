package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;
import com.epam.training.ticketservice.core.booking.persistence.repository.BookingRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceImpTest {
    private static final Booking BOOKINGENTITY = new Booking("Encanto","Wermin",
            "2022-11-28 11:00","5,1 5,2","arthur23",1500);
    private static final BookingDto BOOKINGDTO = new BookingDto("Encanto","Wermin",
            "2022-11-28 11:00","5,1 5,2","arthur23",1500);
    private static final RoomDto ROOMDTO = new RoomDto("Wermin",10,10);
    private final BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private final RoomService roomService = Mockito.mock(RoomService.class);
    private final BookingServiceImp underTest = new BookingServiceImp(bookingRepository,roomService);

    @Test
    void testCreateBooking() {
        Mockito.when(bookingRepository.save(BOOKINGENTITY)).thenReturn(BOOKINGENTITY);
        Booking actual = underTest.createBooking(BOOKINGDTO);
        assertEquals(BOOKINGENTITY,actual);
        Mockito.verify(bookingRepository).save(BOOKINGENTITY);
    }

    @Test
    void testIsSeatTakenShouldReturnNull() {
        Mockito.when(bookingRepository.findAll()).thenReturn(List.of(BOOKINGENTITY));
        String actual = underTest.isSeatTaken("6,3 6,4");
        assertNull(actual);
        Mockito.verify(bookingRepository).findAll();
    }

    @Test
    void testIsSeatTakenShouldReturnASeat() {
        Mockito.when(bookingRepository.findAll()).thenReturn(List.of(BOOKINGENTITY));
        String expected = "5,2";
        String actual = underTest.isSeatTaken("5,2 5,3");
        assertEquals(expected,actual);
        Mockito.verify(bookingRepository).findAll();
    }

    @Test
    void testDoesSeatNotExistsShouldReturnNull() {
        Mockito.when(roomService.getRoomByName("Wermin")).thenReturn(ROOMDTO);
        String actual = underTest.doesSeatNotExists("2,3","Wermin");
        assertNull(actual);
    }

    @Test
    void testDoesSeatNotExistsShouldReturnASeatIfRowDoesNotExists() {
        Mockito.when(roomService.getRoomByName("Wermin")).thenReturn(ROOMDTO);
        String expected = "12,2";
        String actual = underTest.doesSeatNotExists("5,7 12,2","Wermin");
        assertEquals(expected,actual);
    }

    @Test
    void testDoesSeatNotExistsShouldReturnASeatIfColumnDoesNotExists() {
        Mockito.when(roomService.getRoomByName("Wermin")).thenReturn(ROOMDTO);
        String expected = "2,12";
        String actual = underTest.doesSeatNotExists("5,7 2,12","Wermin");
        assertEquals(expected,actual);
    }
}