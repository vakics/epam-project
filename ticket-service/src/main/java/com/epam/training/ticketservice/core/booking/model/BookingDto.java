package com.epam.training.ticketservice.core.booking.model;

import lombok.Value;

@Value
public class BookingDto {
    String movieTitle;
    String roomName;
    String screeningBegins;
    String bookedSeats;
    String userName;
    int basePrice;

    public String toString() {
        String[] seats = bookedSeats.split(" ");
        StringBuilder formattedSeats = new StringBuilder();
        for (String seat:seats) {
            formattedSeats.append("(").append(seat).append(")");
            if (!seats[seats.length - 1].equals(seat)) {
                formattedSeats.append(", ");
            }
        }
        return "Seats booked: " + formattedSeats + "; the price for this booking is " + seats.length * basePrice
                + " HUF";
    }
}
