package com.epam.training.ticketservice.core.pricing.model;

import com.epam.training.ticketservice.core.pricing.persistence.entity.Pricing;
import lombok.Value;

@Value
public class PricingDto {
    String name;
    int howMuch;
    Pricing.AttachTo attachTo;
    String attachedToThis;

}
