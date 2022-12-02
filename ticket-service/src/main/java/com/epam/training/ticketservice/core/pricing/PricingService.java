package com.epam.training.ticketservice.core.pricing;

import com.epam.training.ticketservice.core.pricing.model.PricingDto;
import com.epam.training.ticketservice.core.pricing.persistence.entity.Pricing;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

public interface PricingService {
    Pricing createPrice(PricingDto pricingDto);

    int getPriceComponentPrice(String name);

    PricingDto attachTo(PricingDto pricingDto);

    int getPrice();

    int updatePrice(int price);

    int showPrice(ScreeningDto screeningDto, String seats);
}
