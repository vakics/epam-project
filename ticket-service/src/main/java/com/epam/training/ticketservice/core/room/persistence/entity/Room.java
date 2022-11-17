package com.epam.training.ticketservice.core.room.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Room {
    @Id
    private String name;
    private Integer seatRows;
    private Integer seatColumns;

    public Room(String name, Integer seatRows, Integer seatColumns) {
        this.name = name;
        this.seatRows = seatRows;
        this.seatColumns = seatColumns;
    }
}
