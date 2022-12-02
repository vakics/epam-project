package com.epam.training.ticketservice.core.pricing.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Pricing {
    @Id
    private String name;
    private int howMuch;
    @Enumerated(EnumType.STRING)
    private AttachTo attachTo;
    private String attachedToThis;
    private int basePrice;

    public enum AttachTo {
        NothingYet,
        Room,
        Movie,
        Screening
    }

    public Pricing(String name, int howMuch, AttachTo attachTo, String attachedToThis) {
        this.name = name;
        this.howMuch = howMuch;
        this.attachTo = attachTo;
        this.attachedToThis = attachedToThis;
    }
}
