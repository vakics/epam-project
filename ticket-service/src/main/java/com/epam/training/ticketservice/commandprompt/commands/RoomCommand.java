package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.commandprompt.commands.checks.CheckService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@ShellComponent
public class RoomCommand {
    private final RoomService roomService;
    private final CheckService checkService;
    private final UserService userService;

    @ShellMethod(key = "create room",value = "Creating a room")
    public String createRoom(String name, Integer seatRows, Integer seatColumns) throws Exception {
        if (!roomService.getRooms().isEmpty() && checkService.isExistingRoom(name)) {
            throw new Exception("There already is a room with that name");
        }
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            RoomDto roomDto = new RoomDto(name, seatRows, seatColumns);
            roomService.createRoom(roomDto);
            return roomDto.toString();
        }
        return null;
    }

    @ShellMethod(key = "update room",value = "Updating room")
    public String updateRoom(String name,Integer seatRows,Integer seatColumns) throws Exception {
        if (!checkService.isExistingRoom(name)) {
            return "There is no room with that name";
        }
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            roomService.updateRoom(name, seatRows, seatColumns);
            return new RoomDto(name, seatRows, seatColumns) + " updated";
        }
        return null;
    }

    @ShellMethod(key = "delete room",value = "Deleting room")
    public String deleteRoom(String name) {
        if (!checkService.isExistingRoom(name)) {
            return "There is no room with that name";
        }
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            roomService.deleteRoom(name);
            return "Room " + name + " successfully deleted";
        }
        return null;
    }

    @ShellMethod(key = "list rooms",value = "List of rooms")
    public String getRooms() {
        if (roomService.getRooms().isEmpty()) {
            return "There are no rooms at the moment";
        }
        return roomService.getRooms().stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }
}
