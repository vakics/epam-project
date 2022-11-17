package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImp implements RoomService {
    private final RoomRepository roomRepository;
    private final UserService userService;

    @Override
    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomByName(String name) {
        return convertEntityToDto(roomRepository.findByName(name));
    }

    @Override
    public void createRoom(RoomDto roomDto) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            Room room = new Room(roomDto.getName(), roomDto.getSeatRows(), roomDto.getSeatColumns());
            roomRepository.save(room);
        }
    }

    @Override
    public void updateRoom(String name, Integer seatRows, Integer seatColumns) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            deleteRoom(name);
            createRoom(new RoomDto(name, seatRows, seatColumns));
        }
    }

    @Override
    public void deleteRoom(String name) {
        if (userService.describe().isPresent() && userService.describe().get().getRole().equals(User.Role.ADMIN)) {
            roomRepository.delete(roomRepository.findByName(name));
            getRooms();
        }
    }

    private RoomDto convertEntityToDto(Room room) {
        return RoomDto.builder()
                .withName(room.getName())
                .withSeatRows(room.getSeatRows())
                .withSeatColumns(room.getSeatColumns())
                .build();
    }

    private Optional<RoomDto> convertEntityToDto(Optional<Room> room) {
        return room.isEmpty() ? Optional.empty() : Optional.of(convertEntityToDto(room.get()));
    }
}
