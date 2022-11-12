package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.Screening;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {
    List<Screening> getScreenings();

    void deleteScreening(String movieTitle,String roomName,String screeningBegins);

    void createScreening(Screening screening);

    boolean isOverlapping(String screeningBegins, String movieTitle, String roomName) throws ParseException;

    boolean isBreakTimeAfter(String movieTitle, String roomName, String screeningBegins) throws ParseException;
}
