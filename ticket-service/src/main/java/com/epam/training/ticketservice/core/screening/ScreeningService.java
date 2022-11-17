package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {
    List<ScreeningDto> getScreenings();

    void deleteScreening(String movieTitle,String roomName,String screeningBegins);

    void createScreening(ScreeningDto screeningDto);

    boolean isOverlapping(String screeningBegins, String movieTitle, String roomName) throws ParseException;

    boolean isBreakTimeAfter(String movieTitle, String roomName, String screeningBegins) throws ParseException;
}
