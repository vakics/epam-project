package com.epam.training.ticketservice.core.room.model;

import lombok.Value;

@Value
public class Room {
    String name;
    Integer seatRows;
    Integer seatColumns;

    public String toString() {
        return "Room " + name + " with " + seatColumns * seatRows + "seats, "
                + seatRows + "rows, " + seatColumns + " columns";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Integer seatRows;
        private Integer seatColumns;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSeatRows(Integer seatRows) {
            this.seatRows = seatRows;
            return this;
        }

        public Builder withSeatColumns(Integer seatColumns) {
            this.seatColumns = seatColumns;
            return this;
        }

        public Room build() {
            return new Room(name,seatRows,seatColumns);
        }
    }
}
