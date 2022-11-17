package com.epam.training.ticketservice.commandprompt.commands.checks;

public interface CheckService {
    boolean isExistingMovie(String title);

    boolean isExistingRoom(String name);
}
