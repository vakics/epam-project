package com.epam.training.ticketservice.commandprompt.commands;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.pricing.PricingService;
import com.epam.training.ticketservice.core.pricing.model.PricingDto;
import com.epam.training.ticketservice.core.pricing.persistence.entity.Pricing;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@AllArgsConstructor
@ShellComponent
public class PricingCommand {
    private final PricingService pricingService;
    private final UserService userService;
    private final MovieService movieService;
    private final ScreeningService screeningService;

    @ShellMethod(key = "create price component",value = "Creates new price component")
    public String createPriceComponent(String name,String howMuch) {
        if (userService.describe().isPresent() && userService.describe().get().getRole() == User.Role.ADMIN) {
            PricingDto pricingDto = new PricingDto(name,Integer.parseInt(howMuch), Pricing.AttachTo.NothingYet,
                    null);
            pricingService.createPrice(pricingDto);
            return "Price component " + name + " created";
        }
        return "You are not an admin";
    }

    @ShellMethod(key = "update base price",value = "Updating booking's base price")
    public String updatePrice(String newPrice) {
        if (userService.describe().isPresent() || userService.describe().get().getRole() == User.Role.ADMIN) {
            pricingService.updatePrice(Integer.parseInt(newPrice));
            return "Price updated to " + newPrice;
        }
        return "You are not an admin";
    }

    @ShellMethod(key = "attach price component to room", value = "Attaching price component to a room")
    public String attachComponentToRoom(String componentName, String roomName) {
        if (userService.describe().isPresent() && userService.describe().get().getRole() == User.Role.ADMIN) {
            PricingDto pricingDto = new PricingDto(componentName, pricingService.getPriceComponentPrice(componentName),
                    Pricing.AttachTo.Room,roomName);
            pricingService.attachTo(pricingDto);
            return componentName + " attached to " + roomName;
        }
        return "You are not an admin";
    }

    @ShellMethod(key = "attach price component to movie",value = "Attaching price component to movie")
    public String attachComponentToMovie(String componentName,String movieTitle) {
        if (userService.describe().isPresent() && userService.describe().get().getRole() == User.Role.ADMIN) {
            PricingDto pricingDto = new PricingDto(componentName, pricingService.getPriceComponentPrice(componentName),
                    Pricing.AttachTo.Movie,movieTitle);
            pricingService.attachTo(pricingDto);
            return componentName + " attached to " + movieTitle;
        }
        return "You are not an admin";
    }

    @ShellMethod(key = "attach price component to screening",value = "Attaching price component to screening")
    public String attachComponentToScreening(String componentName,String movieTitle,String roomName,
                                             String screeningBegins) {
        if (userService.describe().isPresent() && userService.describe().get().getRole() == User.Role.ADMIN) {
            ScreeningDto screeningDto = new ScreeningDto(movieTitle,roomName,screeningBegins,movieService);
            PricingDto pricingDto = new PricingDto(componentName, pricingService.getPriceComponentPrice(componentName),
                    Pricing.AttachTo.Screening,screeningDto.toString());
            pricingService.attachTo(pricingDto);
            return componentName + " attached to screening";
        }
        return "You are not an admin";
    }

    @ShellMethod(key = "show price for",value = "Show price for a possible booking")
    public String showPrice(String movieTitle,String roomName,String screeningBegins,String seats) {
        ScreeningDto screeningDto = screeningService.getScreening(movieTitle, roomName, screeningBegins);
        if (screeningDto == null) {
            System.out.println("There is no screening like that");
        }
        return "The price for this booking would be " + pricingService.showPrice(screeningDto,seats) + " HUF";
    }
}
