package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScreeningServiceImp implements ScreeningService {
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    private final UserService userService;

    @Override
    public List<ScreeningDto> getScreenings() {
        return screeningRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, String screeningBegins) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            screeningRepository.delete(screeningRepository.findByMovieTitleAndRoomNameAndScreeningBegins(
                    movieTitle, roomName, screeningBegins));
        }
    }

    @Override
    public void createScreening(ScreeningDto screeningDto) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            Screening screening = new Screening(screeningDto.getMovieTitle(),
                    screeningDto.getRoomName(), screeningDto.getScreeningBegins());
            screeningRepository.save(screening);
        }
    }

    @Override
    public boolean isOverlapping(String screeningBegins, String movieTitle, String roomName) throws ParseException {
        int lengthInMinutes = movieRepository.findByTitle(movieTitle).getLengthInMinutes();
        Date ending = getEndingTime(screeningBegins,lengthInMinutes);
        Date beginning = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningBegins);
        for (ScreeningDto screeningDto : getScreenings()) {
            if (!roomName.equals(screeningDto.getRoomName())) {
                continue;
            }
            int movieLength = movieRepository.findByTitle(screeningDto.getMovieTitle()).getLengthInMinutes();
            try {
                Date begins = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningDto.getScreeningBegins());
                Date ends = getEndingTime(screeningDto.getScreeningBegins(),movieLength);
                if ((begins.getTime() <= beginning.getTime() && beginning.getTime() <= ends.getTime())
                        || (begins.getTime() <= ending.getTime() && ending.getTime() <= ends.getTime())) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean isBreakTimeAfter(String movieTitle, String roomName, String screeningBegins) throws ParseException {
        Date beginning = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningBegins);
        for (ScreeningDto screeningDto : getScreenings()) {
            if (!screeningDto.getRoomName().equals(roomName)) {
                continue;
            }
            int movieLength = movieRepository.findByTitle(screeningDto.getMovieTitle()).getLengthInMinutes();
            Date breakStarting = getEndingTime(screeningDto.getScreeningBegins(),movieLength);
            Date breakEnding = new Date(breakStarting.getTime() + (10 * 60000L));
            if (breakStarting.getTime() < beginning.getTime() && beginning.getTime() <= breakEnding.getTime()) {
                return true;
            }
        }
        return false;
    }

    public Date getEndingTime(String screeningBegins, int lengthInMinutes) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningBegins);
        return new Date(date.getTime() + (lengthInMinutes * 60000L));
    }

    private ScreeningDto convertEntityToDto(Screening screening) {
        return new ScreeningDto(screening.getMovieTitle(),screening.getRoomName(),
                screening.getScreeningBegins(),movieRepository);
    }

    private Optional<ScreeningDto> convertEntityToDto(Optional<Screening> screening) {
        return screening.isEmpty() ? Optional.empty() : Optional.of(convertEntityToDto(screening.get()));
    }
}
