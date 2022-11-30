package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@ShellComponent
public class ScreeningCommand {
    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final UserService userService;

    @ShellMethod(key = "create screening",value = "Creates screening")
    public String createScreening(String movieTitle, String roomName, String screeningBegins) throws Exception {
        if (screeningService.isOverlapping(screeningBegins,movieTitle,roomName)) {
            return "There is an overlapping screening";
        }
        if (screeningService.isBreakTimeAfter(movieTitle, roomName, screeningBegins)) {
            return "This would start in the break period after another screening in this room";
        }
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            ScreeningDto screeningDto = new ScreeningDto(movieTitle, roomName, screeningBegins, movieService);
            screeningService.createScreening(screeningDto);
            return screeningDto.toString();
        }
        return null;
    }

    @ShellMethod(key = "delete screening",value = "Deletes screening")
    public String deleteScreening(String movieTitle,String roomName,String screeningBegins) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            ScreeningDto screeningDto = new ScreeningDto(movieTitle, roomName, screeningBegins, movieService);
            screeningService.deleteScreening(movieTitle, roomName, screeningBegins);
            return "Screening " + screeningDto + " deleted";
        }
        return null;
    }

    @ShellMethod(key = "list screenings",value = "Listing screenings")
    public String listScreening() {
        if (screeningService.getScreenings().isEmpty()) {
            return "There are no screenings";
        }
        return screeningService.getScreenings().stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }
}
