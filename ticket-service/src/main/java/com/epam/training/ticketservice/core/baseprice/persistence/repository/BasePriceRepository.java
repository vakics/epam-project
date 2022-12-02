package com.epam.training.ticketservice.core.baseprice.persistence.repository;

import com.epam.training.ticketservice.core.baseprice.persistence.entity.BasePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePriceRepository extends JpaRepository<BasePrice,Integer> {
}
