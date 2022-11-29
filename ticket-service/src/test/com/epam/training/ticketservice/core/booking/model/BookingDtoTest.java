package com.epam.training.ticketservice.core.booking.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingDtoTest {
    private static final BookingDto bookingDto = new BookingDto("Encanto","Wermin",
            "2022-11-28 11:00","5,1 5,2","arthur23");

    @Test
    void testToString() {
        String[] seats = bookingDto.getBookedSeats().split(" ");
        StringBuilder formattedSeats = new StringBuilder();
        for (String seat:seats) {
            formattedSeats.append("(").append(seat).append(")");
            if (!seats[seats.length - 1].equals(seat)) {
                formattedSeats.append(", ");
            }
        }
        String expected = "Seats booked: " + formattedSeats + "; the price for this booking is " +
                seats.length * 1500 + " HUF";
        String actual = bookingDto.toString();
        assertEquals(expected,actual);
    }
}