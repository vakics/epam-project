package com.epam.training.ticketservice.core.baseprice;

import com.epam.training.ticketservice.core.baseprice.persistence.entity.BasePrice;
import com.epam.training.ticketservice.core.baseprice.persistence.repository.BasePriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BasePriceServiceImp implements BasePriceService {
    private final BasePriceRepository basePriceRepository;

    @Override
    public int getBasePrice() {
        return basePriceRepository.findAll().get(0).getBasePrice();
    }

    @Override
    public void setBasePrice(int price) {
        basePriceRepository.delete(basePriceRepository.findAll().get(0));
        basePriceRepository.save(new BasePrice(price));
    }
}
