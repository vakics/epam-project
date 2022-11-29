package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;

public interface BookingService {
    Booking createBooking(BookingDto bookingDto);

    String isSeatTaken(String seatsBooked);

    String doesSeatNotExists(String seatsBooked, String roomName);
}
