package com.epam.training.ticketservice.core.pricing;

import com.epam.training.ticketservice.core.baseprice.BasePriceService;
import com.epam.training.ticketservice.core.pricing.model.PricingDto;
import com.epam.training.ticketservice.core.pricing.persistence.entity.Pricing;
import com.epam.training.ticketservice.core.pricing.persistence.repository.PricingRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PricingServiceImp implements PricingService {
    private final PricingRepository pricingRepository;
    private final BasePriceService basePriceService;

    @Override
    public Pricing createPrice(PricingDto pricingDto) {
        Pricing pricing = new Pricing(pricingDto.getName(),pricingDto.getHowMuch(),pricingDto.getAttachTo(),
                pricingDto.getAttachedToThis());
        pricingRepository.save(pricing);
        return pricing;
    }

    @Override
    public int getPriceComponentPrice(String name) {
        return pricingRepository.findByName(name).getHowMuch();
    }

    @Override
    public PricingDto attachTo(PricingDto pricingDto) {
        pricingRepository.delete(pricingRepository.findByName(pricingDto.getName()));
        createPrice(pricingDto);
        return pricingDto;
    }

    @Override
    public int getPrice() {
        return basePriceService.getBasePrice();
    }

    @Override
    public int updatePrice(int price) {
        basePriceService.setBasePrice(price);
        return basePriceService.getBasePrice();
    }

    @Override
    public int showPrice(ScreeningDto screeningDto, String seats) {
        int forOnePerson = getPrice();
        List<Pricing> movieComponent = pricingRepository.findByAttachTo(Pricing.AttachTo.Movie);
        List<Pricing> roomComponent = pricingRepository.findByAttachTo(Pricing.AttachTo.Room);
        List<Pricing> screeningComponent = pricingRepository.findByAttachTo(Pricing.AttachTo.Screening);
        for (Pricing movie: movieComponent) {
            if (movie.getAttachedToThis().equals(screeningDto.getMovieTitle())) {
                forOnePerson += movie.getHowMuch();
            }
        }
        for (Pricing room: roomComponent) {
            if (room.getAttachedToThis().equals(screeningDto.getRoomName())) {
                forOnePerson += room.getHowMuch();
            }
        }
        for (Pricing screening: screeningComponent) {
            if (screening.getAttachedToThis().equals(screeningDto.toString())) {
                forOnePerson += screening.getHowMuch();
            }
        }
        String[] seatList = seats.split(" ");
        return forOnePerson * seatList.length;
    }
}
