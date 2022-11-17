package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> getRooms();

    RoomDto getRoomByName(String name);

    void createRoom(RoomDto roomDto);

    void updateRoom(String name,Integer seatRows,Integer seatColumns) throws Exception;

    void deleteRoom(String name);
}
