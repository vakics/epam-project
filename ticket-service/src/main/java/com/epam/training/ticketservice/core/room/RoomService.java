package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;

import java.util.List;

public interface RoomService {
    List<RoomDto> getRooms();

    RoomDto getRoomByName(String name);

    Room createRoom(RoomDto roomDto);

    Room updateRoom(String name,Integer seatRows,Integer seatColumns) throws Exception;

    void deleteRoom(String name);
}
