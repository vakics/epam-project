package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImp implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomByName(String name) {
        return convertEntityToDto(roomRepository.findByName(name));
    }

    @Override
    public Room createRoom(RoomDto roomDto) {
        Room room = new Room(roomDto.getName(), roomDto.getSeatRows(), roomDto.getSeatColumns());
        roomRepository.save(room);
        return room;
    }

    @Override
    public Room updateRoom(String name, Integer seatRows, Integer seatColumns) {
        deleteRoom(name);
        return createRoom(new RoomDto(name, seatRows, seatColumns));
    }

    @Override
    public void deleteRoom(String name) {
        roomRepository.delete(roomRepository.findByName(name));
        getRooms();
    }

    private RoomDto convertEntityToDto(Room room) {
        return room == null ? null : RoomDto.builder()
                .withName(room.getName())
                .withSeatRows(room.getSeatRows())
                .withSeatColumns(room.getSeatColumns())
                .build();
    }

}
