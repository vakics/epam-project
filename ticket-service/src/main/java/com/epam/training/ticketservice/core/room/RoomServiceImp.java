package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.Room;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomServiceImp implements RoomService {
    private List<Room> rooms = new LinkedList<>(List.of(
            Room.builder()
                    .withName("Jó név")
                    .withSeatRows(12)
                    .withSeatColumns(20)
                    .build()
    ));

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public Room getRoomByName(String name) {
        return rooms.stream().filter(room -> room.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void createRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public void updateRoom(String name, Integer seatRows, Integer seatColumns) {
        deleteRoom(name);
        createRoom(Room.builder().withName(name).withSeatRows(seatRows).withSeatColumns(seatColumns).build());
    }

    @Override
    public void deleteRoom(String name) {
        rooms = rooms.stream().filter(room -> !room.getName().equals(name)).collect(Collectors.toList());
    }
}
