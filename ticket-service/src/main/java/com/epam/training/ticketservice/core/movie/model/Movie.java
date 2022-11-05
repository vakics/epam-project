package com.epam.training.ticketservice.core.movie.model;

import lombok.Value;

@Value
public class Movie {
    String title;
    String genre;
    Integer lengthInMinutes;

    public String toString(){
        return title+" ("+genre+", "+lengthInMinutes+")\n";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String genre;
        private Integer lengthInMinutes;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withLengthInMinutes(Integer lengthInMinutes) {
            this.lengthInMinutes = lengthInMinutes;
            return this;
        }

        public Movie build() {
            return new Movie(title,genre,lengthInMinutes);
        }
    }
}
