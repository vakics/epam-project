package com.epam.training.ticketservice.core.pricing.persistence.repository;

import com.epam.training.ticketservice.core.pricing.persistence.entity.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricingRepository extends JpaRepository<Pricing,Integer> {
    Pricing findByName(String name);

    List<Pricing> findByAttachTo(Pricing.AttachTo attachTo);
}
