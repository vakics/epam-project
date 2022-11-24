package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoomServiceImpTest {
    private static final Room ENTITY = new Room("Wermin",10,10);
    private static final RoomDto DTO = RoomDto.builder().withName("Wermin").withSeatRows(10)
            .withSeatColumns(10).build();
    private final RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
    private final RoomServiceImp underTest = new RoomServiceImp(roomRepository);

    @Test
    void testGetRooms() {
        Mockito.when(roomRepository.findAll()).thenReturn(List.of(ENTITY));
        List<RoomDto> expected = List.of(DTO);
        List<RoomDto> actual = underTest.getRooms();
        assertEquals(expected,actual);
        Mockito.verify(roomRepository).findAll();
    }

    @Test
    void testGetRoomByNameShouldReturnWerminWhenInputIsWermin() {
        Mockito.when(roomRepository.findByName("Wermin")).thenReturn(ENTITY);
        RoomDto actual = underTest.getRoomByName("Wermin");
        assertEquals(DTO,actual);
        Mockito.verify(roomRepository).findByName("Wermin");
    }

    @Test
    void testGetRoomByNameShouldReturnNullWhenInputIsMoldova() {
        Mockito.when(roomRepository.findByName("Moldova")).thenReturn(null);
        RoomDto actual = underTest.getRoomByName("Moldova");
        assertNull(actual);
        Mockito.verify(roomRepository).findByName("Moldova");
    }

    @Test
    void testCreateRoom() {
        Mockito.when(roomRepository.save(ENTITY)).thenReturn(ENTITY);
        Room actual = underTest.createRoom(DTO);
        assertEquals(ENTITY,actual);
        Mockito.verify(roomRepository).save(ENTITY);
    }

    @Test
    void testUpdateRoom() {
        Mockito.when(roomRepository.save(ENTITY)).thenReturn(ENTITY);
        Room actual = underTest.updateRoom(DTO.getName(), DTO.getSeatRows(), DTO.getSeatColumns());
        assertEquals(ENTITY,actual);
        Mockito.verify(roomRepository).save(ENTITY);
    }

    @Test
    void testDeleteRoom() {
        Mockito.when(roomRepository.findByName(DTO.getName())).thenReturn(ENTITY);
        underTest.deleteRoom(DTO.getName());
        Mockito.verify(roomRepository).findByName(DTO.getName());
    }
}