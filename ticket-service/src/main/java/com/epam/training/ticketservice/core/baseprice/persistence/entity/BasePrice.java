package com.epam.training.ticketservice.core.baseprice.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class BasePrice {
    @Id
    int basePrice;

    public BasePrice(int basePrice) {
        this.basePrice = basePrice;
    }
}
