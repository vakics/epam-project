package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {
    List<ScreeningDto> getScreenings();

    ScreeningDto getScreening(String movieTitle,String roomName,String screeningBegins);

    void deleteScreening(String movieTitle,String roomName,String screeningBegins);

    Screening createScreening(ScreeningDto screeningDto);

    boolean isOverlapping(String screeningBegins, String movieTitle, String roomName) throws ParseException;

    boolean isBreakTimeAfter(String movieTitle, String roomName, String screeningBegins) throws ParseException;
}
