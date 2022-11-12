package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRooms();

    Room getRoomByName(String name);

    void createRoom(Room room);

    void updateRoom(String name,Integer seatRows,Integer seatColumns) throws Exception;

    void deleteRoom(String name);
}
