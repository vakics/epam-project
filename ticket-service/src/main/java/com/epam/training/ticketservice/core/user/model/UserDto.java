package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.Value;

@Value
public class UserDto {
    String username;
    User.Role role;
}
