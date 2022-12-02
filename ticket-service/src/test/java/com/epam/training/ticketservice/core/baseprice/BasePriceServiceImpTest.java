package com.epam.training.ticketservice.core.baseprice;

import com.epam.training.ticketservice.core.baseprice.persistence.entity.BasePrice;
import com.epam.training.ticketservice.core.baseprice.persistence.repository.BasePriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasePriceServiceImpTest {
    private static final BasePrice baseprice = new BasePrice(1500);
    private final BasePriceRepository basePriceRepository = Mockito.mock(BasePriceRepository.class);
    private final BasePriceServiceImp underTest = new BasePriceServiceImp(basePriceRepository);


    @Test
    void getBasePrice() {
        Mockito.when(basePriceRepository.findAll()).thenReturn(List.of(baseprice));
        int expected = 1500;
        int actual = underTest.getBasePrice();
        assertEquals(expected,actual);
        Mockito.verify(basePriceRepository).findAll();
    }

    @Test
    void setBasePrice() {
        Mockito.when(basePriceRepository.findAll()).thenReturn(List.of(baseprice));
        underTest.setBasePrice(1000);
        Mockito.verify(basePriceRepository).findAll();
    }
}