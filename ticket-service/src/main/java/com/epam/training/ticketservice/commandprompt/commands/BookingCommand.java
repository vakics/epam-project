package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.core.booking.BookingService;
import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.pricing.PricingService;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@AllArgsConstructor
@ShellComponent
public class BookingCommand {
    private final BookingService bookingService;
    private final UserService userService;
    private final PricingService pricingService;

    @ShellMethod(key = "book",value = "Book tickets")
    public String booking(String movieTitle,String roomName,String screeningBegins,String bookedSeats) {
        if (userService.describe().isPresent() && userService.describe().get().getRole() != User.Role.USER) {
            return "You have to be a user to create bookings";
        }
        if (bookingService.isSeatTaken(bookedSeats) != null) {
            return "Seat (" + bookingService.isSeatTaken(bookedSeats) + ") is already taken";
        }
        if (bookingService.doesSeatNotExists(bookedSeats,roomName) != null) {
            return "Seat " + bookingService.doesSeatNotExists(bookedSeats,roomName) + "does not exist in this room";
        }
        BookingDto bookingDto = new BookingDto(movieTitle, roomName, screeningBegins, bookedSeats,
                userService.describe().get().getUsername(),pricingService.getPrice());
        bookingService.createBooking(bookingDto);
        return bookingDto.toString();
    }
}
