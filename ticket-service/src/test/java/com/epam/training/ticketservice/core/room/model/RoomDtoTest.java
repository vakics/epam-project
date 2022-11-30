package com.epam.training.ticketservice.core.room.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomDtoTest {
    private static final RoomDto DTO = new RoomDto("Wermin",11,11);

    @Test
    void testToString() {
        String expected = "Room " + DTO.getName() + " with " + DTO.getSeatColumns()* DTO.getSeatRows()
                + " seats, " + DTO.getSeatRows() + " rows and " + DTO.getSeatColumns() + " columns";
        String actual = DTO.toString();
        assertEquals(expected,actual);
    }
}