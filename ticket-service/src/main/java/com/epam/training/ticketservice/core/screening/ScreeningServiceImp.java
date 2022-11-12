package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.MovieServiceImp;
import com.epam.training.ticketservice.core.screening.model.Screening;
import org.springframework.stereotype.Component;
import org.tinylog.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScreeningServiceImp implements ScreeningService {
    private List<Screening> screenings = new LinkedList<>(List.of(
            Screening.builder().withMovieTitle("Encanto")
            .withRoomName("Jó név")
            .withScreeningBegins("2022-11-05 10:00")
            .build()
    ));

    @Override
    public List<Screening> getScreenings() {
        return screenings;
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, String screeningBegins) {
        screenings = screenings.stream().filter(screening -> screening.equals(
                new Screening(movieTitle, roomName, screeningBegins))).collect(Collectors.toList());
    }

    @Override
    public void createScreening(Screening screening) {
        screenings.add(screening);
    }

    @Override
    public boolean isOverlapping(String screeningBegins, String movieTitle, String roomName) throws ParseException {
        MovieServiceImp movieService = new MovieServiceImp();
        int lengthInMinutes = movieService.getMovieByTitle(movieTitle).getLengthInMinutes();
        Date ending = getEndingTime(screeningBegins,lengthInMinutes);
        Logger.debug("ending is {}",ending);
        Date beginning = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningBegins);
        for (Screening screening:screenings) {
            if (!roomName.equals(screening.getRoomName())) {
                continue;
            }
            int movieLength = movieService.getMovieByTitle(screening.getMovieTitle()).getLengthInMinutes();
            try {
                Date begins = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screening.getScreeningBegins());
                Date ends = getEndingTime(screening.getScreeningBegins(),movieLength);
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
        MovieServiceImp movieService = new MovieServiceImp();
        Date beginning = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(screeningBegins);
        for (Screening screening:screenings) {
            if (!screening.getRoomName().equals(roomName)) {
                continue;
            }
            int movieLength = movieService.getMovieByTitle(screening.getMovieTitle()).getLengthInMinutes();
            Date breakStarting = getEndingTime(screening.getScreeningBegins(),movieLength);
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
}
