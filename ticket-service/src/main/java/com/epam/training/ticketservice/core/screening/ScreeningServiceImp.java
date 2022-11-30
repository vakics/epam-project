package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScreeningServiceImp implements ScreeningService {
    private final MovieService movieService;
    private final ScreeningRepository screeningRepository;

    @Override
    public List<ScreeningDto> getScreenings() {
        return screeningRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, String screeningBegins) {
        screeningRepository.delete(screeningRepository.findByMovieTitleAndRoomNameAndScreeningBegins(
                movieTitle, roomName, screeningBegins));
    }

    @Override
    public Screening createScreening(ScreeningDto screeningDto) {
        Screening screening = new Screening(screeningDto.getMovieTitle(),
                screeningDto.getRoomName(), screeningDto.getScreeningBegins());
        screeningRepository.save(screening);
        return screening;
    }

    @Override
    public boolean isOverlapping(String screeningBegins, String movieTitle, String roomName) throws ParseException {
        int lengthInMinutes = movieService.getMovieByTitle(movieTitle).getLengthInMinutes();
        Date ending = getEndingTime(screeningBegins,lengthInMinutes);
        Date beginning = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningBegins);
        for (ScreeningDto screeningDto : getScreenings()) {
            if (!roomName.equals(screeningDto.getRoomName())) {
                continue;
            }
            int movieLength = movieService.getMovieByTitle(screeningDto.getMovieTitle()).getLengthInMinutes();
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
            int movieLength = movieService.getMovieByTitle(screeningDto.getMovieTitle()).getLengthInMinutes();
            Date breakStarting = getEndingTime(screeningDto.getScreeningBegins(),movieLength);
            Date breakEnding = new Date(breakStarting.getTime() + (10 * 60000L));
            if (breakStarting.getTime() < beginning.getTime() && beginning.getTime() <= breakEnding.getTime()) {
                return true;
            }
        }
        return false;
    }

    private Date getEndingTime(String screeningBegins, int lengthInMinutes) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningBegins);
        return new Date(date.getTime() + (lengthInMinutes * 60000L));
    }

    private ScreeningDto convertEntityToDto(Screening screening) {
        return new ScreeningDto(screening.getMovieTitle(),screening.getRoomName(),
                screening.getScreeningBegins(),movieService);
    }

}
