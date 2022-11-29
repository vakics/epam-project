package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;
import com.epam.training.ticketservice.core.booking.persistence.repository.BookingRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceImpTest {
    private static final Booking BOOKINGENTITY = new Booking("Encanto","Wermin",
            "2022-11-28 11:00","5,1 5,2","arthur23");
    private static final BookingDto BOOKINGDTO = new BookingDto("Encanto","Wermin",
            "2022-11-28 11:00","5,1 5,2","arthur23");
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
}