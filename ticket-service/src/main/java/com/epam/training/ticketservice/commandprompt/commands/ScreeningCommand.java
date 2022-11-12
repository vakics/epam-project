package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.commandprompt.commands.checks.CheckService;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.Screening;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@AllArgsConstructor
@ShellComponent
public class ScreeningCommand {
    private final ScreeningService screeningService;
    private final CheckService checkService;

    @ShellMethod(key = "create screening",value = "Creates screening")
    public Screening createScreening(String movieTitle,String roomName,String screeningBegins) throws Exception {
        checkService.checkMovie(movieTitle);
        checkService.checkRoom(roomName);
        if (screeningService.isOverlapping(screeningBegins,movieTitle,roomName)) {
            throw new Exception("There is an overlapping screening");
        }
        if (screeningService.isBreakTimeAfter(movieTitle, roomName, screeningBegins)) {
            throw new Exception("This would start in the break period after another screening in this room");
        }
        Screening screening = new Screening(movieTitle, roomName, screeningBegins);
        screeningService.createScreening(screening);
        return screening;
    }

    @ShellMethod(key = "delete screening",value = "Deletes screening")
    public String deleteScreening(String movieTitle,String roomName,String screeningBegins) throws Exception {
        checkService.checkMovie(movieTitle);
        checkService.checkRoom(roomName);
        Screening screening = new Screening(movieTitle, roomName, screeningBegins);
        screeningService.deleteScreening(movieTitle, roomName, screeningBegins);
        return "Screening " + screening + " deleted";
    }

    @ShellMethod(key = "list screenings",value = "Listing screenings")
    public void listScreening() throws Exception {
        if (screeningService.getScreenings() == null) {
            throw new Exception("There are no screenings at the moment");
        }
        screeningService.getScreenings().forEach(System.out::println);
    }
}
