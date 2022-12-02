package com.epam.training.ticketservice.core.config;

import com.epam.training.ticketservice.core.baseprice.persistence.entity.BasePrice;
import com.epam.training.ticketservice.core.baseprice.persistence.repository.BasePriceRepository;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserRepository userRepository;
    private final BasePriceRepository basePriceRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin","admin", User.Role.ADMIN);
        userRepository.save(admin);
        BasePrice basePrice = new BasePrice(1500);
        basePriceRepository.save(basePrice);
    }
}
