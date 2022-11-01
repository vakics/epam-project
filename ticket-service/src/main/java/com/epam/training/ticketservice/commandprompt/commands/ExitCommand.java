package com.epam.training.ticketservice.commandprompt.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ExitCommand {
    @ShellMethod(key = "exit",value = "Exit from application")
    public void exitApp() {
        System.exit(0);
    }
}
