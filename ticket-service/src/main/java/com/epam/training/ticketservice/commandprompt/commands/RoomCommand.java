package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.Room;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@AllArgsConstructor
@ShellComponent
public class RoomCommand {
    private final RoomService roomService;

    @ShellMethod(key = "create room",value = "Creating a room")
    public Room createRoom(String name,Integer seatRows,Integer seatColumns){
        Room room=new Room(name, seatRows, seatColumns);
        roomService.createRoom(room);
        return room;
    }

    @ShellMethod(key = "update room",value = "Updating room")
    public String updateRoom(String name,Integer seatRows,Integer seatColumns) throws Exception {
        if (roomService.getRoomByName(name).isEmpty()) throw new Exception("There is no room with that name");
        roomService.updateRoom(name, seatRows, seatColumns);
        return "Room "+name+" updated";
    }

    @ShellMethod(key = "delete room",value = "Deleting room")
    public String deleteRoom(String name) throws Exception {
        if (roomService.getRoomByName(name).isEmpty()) throw new Exception("There is no room with that name");
        roomService.deleteRoom(name);
        return "Room "+name+" deleted";
    }

    @ShellMethod(key = "list rooms",value = "List of rooms")
    public List<Room> getRooms() throws Exception {
        if (roomService.getRooms()==null) throw new Exception("There are no rooms at the moment");
        return roomService.getRooms();
    }
}
