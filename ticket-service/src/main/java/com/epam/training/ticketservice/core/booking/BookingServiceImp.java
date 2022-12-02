package com.epam.training.ticketservice.core.booking;

import com.epam.training.ticketservice.core.booking.model.BookingDto;
import com.epam.training.ticketservice.core.booking.persistence.entity.Booking;
import com.epam.training.ticketservice.core.booking.persistence.repository.BookingRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Override
    public Booking createBooking(BookingDto bookingDto) {
        Booking booking = new Booking(bookingDto.getMovieTitle(),bookingDto.getRoomName(),
                bookingDto.getScreeningBegins(),bookingDto.getBookedSeats(), bookingDto.getUserName(),
                bookingDto.getBasePrice());
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public String isSeatTaken(String seatsBooked) {
        String[] seats = seatsBooked.split(" ");
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking: bookings) {
            List<String> bookingSeats = Arrays.asList(booking.getSeatsBooked().split(" "));
            for (String seat: seats) {
                if (bookingSeats.contains(seat)) {
                    return seat;
                }
            }
        }
        return null;
    }

    @Override
    public String doesSeatNotExists(String seatsBooked, String roomName) {
        String[] seats = seatsBooked.split(" ");
        int seatRows = roomService.getRoomByName(roomName).getSeatRows();
        int seatCols = roomService.getRoomByName(roomName).getSeatColumns();
        for (String seat: seats) {
            int row = Integer.parseInt(seat.split(",")[0]);
            int col = Integer.parseInt(seat.split(",")[1]);
            if (row > seatRows || col > seatCols) {
                return seat;
            }
        }
        return null;
    }
}
