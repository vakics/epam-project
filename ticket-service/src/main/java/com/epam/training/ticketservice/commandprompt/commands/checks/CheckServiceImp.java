package com.epam.training.ticketservice.commandprompt.commands.checks;

import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckServiceImp implements CheckService {
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public boolean isExistingMovie(String title) {
        return movieRepository.findByTitle(title) != null;
    }

    public boolean isExistingRoom(String name) {
        return roomRepository.findByName(name) != null;
    }
}
