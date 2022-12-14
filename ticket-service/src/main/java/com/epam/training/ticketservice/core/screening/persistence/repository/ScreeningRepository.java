package com.epam.training.ticketservice.core.screening.persistence.repository;

import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Integer> {
    Screening findByMovieTitleAndRoomNameAndScreeningBegins(String movieTitle,String roomName,String screeningBegins);
}
