package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class UserCommand {
    private final UserService userService;

    @ShellMethod(key = "sign in privileged",value = "Admin log in")
    public String adminLogin(String username,String password) {
        Optional<UserDto> user = userService.login(username,password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        if (user.get().getRole().equals(User.Role.ADMIN)) {
            return "Signed in with privileged account '" + username + "'";
        }
        return username + "is successfully logged in";
    }

    @ShellMethod(key = "sign out",value = "Log out")
    public String logout() {
        Optional<UserDto> user = userService.logout();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        return user.get().toString();
    }

    @ShellMethod(key = "describe account",value = "The type of the account")
    public String describeAccount() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        if (user.get().getRole().equals(User.Role.ADMIN)) {
            return "Signed in with privileged account " + user.get().getUsername();
        }
        return user.get().toString();
    }
}
