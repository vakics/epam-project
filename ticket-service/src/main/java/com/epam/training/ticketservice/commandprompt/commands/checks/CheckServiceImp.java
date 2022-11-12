package com.epam.training.ticketservice.commandprompt.commands.checks;

import com.epam.training.ticketservice.core.movie.MovieServiceImp;
import com.epam.training.ticketservice.core.room.RoomServiceImp;
import org.springframework.stereotype.Component;

@Component
public class CheckServiceImp implements CheckService {
    @Override
    public boolean checkMovie(String title) throws Exception {
        MovieServiceImp movieService = new MovieServiceImp();
        if (movieService.getMovieByTitle(title) == null) {
            throw new Exception("There is no movie with that title");
        }
        return true;
    }

    @Override
    public boolean checkRoom(String name) throws Exception {
        RoomServiceImp roomService = new RoomServiceImp();
        if (roomService.getRoomByName(name) == null) {
            throw new Exception("There is no room with that name");
        }
        return true;
    }
}
