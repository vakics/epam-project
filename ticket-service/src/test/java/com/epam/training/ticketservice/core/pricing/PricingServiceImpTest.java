package com.epam.training.ticketservice.core.pricing;

import com.epam.training.ticketservice.core.baseprice.BasePriceService;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.pricing.model.PricingDto;
import com.epam.training.ticketservice.core.pricing.persistence.entity.Pricing;
import com.epam.training.ticketservice.core.pricing.persistence.repository.PricingRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceImpTest {
    private static MovieService movieService = Mockito.mock(MovieService.class);
    private static final Pricing PRICING_ENTITY = new Pricing("componentNew",100,
            Pricing.AttachTo.NothingYet,null);
    private static final PricingDto PRICING_DTO = new PricingDto("componentNew",100,
            Pricing.AttachTo.NothingYet,null);
    private static final PricingDto PRICING_DTO_TO_MOVIE = new PricingDto("componentNew",100,
            Pricing.AttachTo.Movie,"Encanto");
    private static final Pricing PRICING_ENTITY_TO_MOVIE = new Pricing("componentNew",100,
            Pricing.AttachTo.Movie,"Encanto");
    private final PricingRepository pricingRepository = Mockito.mock(PricingRepository.class);
    private final BasePriceService basePriceService = Mockito.mock(BasePriceService.class);
    private final PricingServiceImp underTest = new PricingServiceImp(pricingRepository,basePriceService);
    private static final ScreeningDto SCREENING_DTO = new ScreeningDto("Encanto","Wermin",
            "2022-12-02 12:00",movieService);

    @Test
    void testCreatePricing() {
        Mockito.when(pricingRepository.save(PRICING_ENTITY)).thenReturn(PRICING_ENTITY);
        Pricing actual = underTest.createPrice(PRICING_DTO);
        assertEquals(PRICING_ENTITY,actual);
        Mockito.verify(pricingRepository).save(PRICING_ENTITY);
    }

    @Test
    void testGetPriceComponent() {
        Mockito.when(pricingRepository.findByName("componentNew")).thenReturn(PRICING_ENTITY);
        int actual = underTest.getPriceComponentPrice("componentNew");
        assertEquals(100,actual);
        Mockito.verify(pricingRepository).findByName("componentNew");
    }

    @Test
    void testGetPrice() {
        Mockito.when(basePriceService.getBasePrice()).thenReturn(1500);
        int actual = underTest.getPrice();
        assertEquals(1500,actual);
        Mockito.verify(basePriceService).getBasePrice();
    }

    @Test
    void testAttachTo() {
        Mockito.when(pricingRepository.findByName("componentNew")).thenReturn(PRICING_ENTITY);
        PricingDto actual = underTest.attachTo(PRICING_DTO_TO_MOVIE);
        assertEquals(PRICING_DTO_TO_MOVIE,actual);
        Mockito.verify(pricingRepository).findByName("componentNew");
    }

    @Test
    void testShowPriceWithMovieComponent() {
        Mockito.when(underTest.getPrice()).thenReturn(1500);
        Mockito.when(pricingRepository.findByAttachTo(Pricing.AttachTo.Movie))
                .thenReturn(List.of(PRICING_ENTITY_TO_MOVIE));
        int actual = underTest.showPrice(SCREENING_DTO,"2,3 2,4");
        assertEquals(3200,actual);
        Mockito.verify(pricingRepository).findByAttachTo(Pricing.AttachTo.Movie);
    }

}