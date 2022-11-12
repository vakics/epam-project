package com.epam.training.ticketservice.commandprompt.commands.checks;

public interface CheckService {
    boolean checkMovie(String title) throws Exception;

    boolean checkRoom(String name) throws Exception;
}
