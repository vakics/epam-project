package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;
import com.epam.training.ticketservice.core.booking.persistence.repository.BookingRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class UserCommand {
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @ShellMethod(key = "sign in privileged",value = "Admin log in")
    public String adminLogin(String username,String password) {
        Optional<UserDto> user = userService.login(username,password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        if (user.get().getRole().equals(User.Role.ADMIN)) {
            return "Signed in with privileged account '" + username + "'";
        }
        List<Booking> bookingList = bookingRepository.findByUserName(username);
        return username + "is successfully logged in";
    }

    @ShellMethod(key = "sign out",value = "Log out")
    public String logout() {
        Optional<UserDto> user = userService.logout();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        return user.get().toString();
    }

    @ShellMethod(key = "describe account",value = "The type of the account")
    public String describeAccount() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        if (user.get().getRole().equals(User.Role.ADMIN)) {
            return "Signed in with privileged account '" + user.get().getUsername() + "'";
        }
        List<Booking> bookings = bookingRepository.findByUserName(user.get().getUsername());
        StringBuilder bookingsForUser;
        if (bookings.isEmpty()) {
            bookingsForUser = new StringBuilder("You have not booked any tickets yet");
        } else {
            bookingsForUser = new StringBuilder("Your previous bookings are\n");
            for (Booking booking: bookings) {
                String[] seats = booking.getSeatsBooked().split(" ");
                bookingsForUser.append("Seats ");
                for (String seat: seats) {
                    bookingsForUser.append("(").append(seat).append(")");
                    if (!seats[seats.length - 1].equals(seat)) {
                        bookingsForUser.append(", ");
                    }
                }
                bookingsForUser.append(" on ").append(booking.getMovieTitle()).append(" in room ")
                        .append(booking.getRoomName()).append(" starting at ").append(booking.getScreeningBegins())
                        .append(" for ").append(seats.length * 1500).append(" HUF");
            }
        }
        System.out.println("Signed in with account '" + user.get().getUsername() + "'");
        return bookingsForUser.toString();
    }

    @ShellMethod(key = "sign up",value = "Registrating user")
    public String registerUser(String userName,String password) {
        try {
            userService.registerUser(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }

    @ShellMethod(key = "sign in",value = "Login for user")
    public String signInUser(String userName,String password) {
        Optional<UserDto> user = userService.login(userName, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return "Signed in with account '" + userName + "'";
    }
}
