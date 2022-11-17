package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.commandprompt.commands.checks.CheckService;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
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

    @ShellMethod(key = "create room",value = "Creating a room")
    public String createRoom(String name, Integer seatRows, Integer seatColumns) throws Exception {
        if (!roomService.getRooms().isEmpty() && checkService.isExistingRoom(name)) {
            throw new Exception("There already is a room with that name");
        }
        RoomDto roomDto = new RoomDto(name, seatRows, seatColumns);
        roomService.createRoom(roomDto);
        return roomDto.toString();
    }

    @ShellMethod(key = "update room",value = "Updating room")
    public String updateRoom(String name,Integer seatRows,Integer seatColumns) throws Exception {
        if (!checkService.isExistingRoom(name)) {
            return "There is no room with that name";
        }
        roomService.updateRoom(name, seatRows, seatColumns);
        return new RoomDto(name, seatRows, seatColumns) + " updated";
    }

    @ShellMethod(key = "delete room",value = "Deleting room")
    public String deleteRoom(String name) {
        if (!checkService.isExistingRoom(name)) {
            return "There is no room with that name";
        }
        roomService.deleteRoom(name);
        return "Room " + name + " successfully deleted";
    }

    @ShellMethod(key = "list rooms",value = "List of rooms")
    public String getRooms() {
        if (roomService.getRooms().isEmpty()) {
            return "There are no rooms at the moment";
        }
        return roomService.getRooms().stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }
}
