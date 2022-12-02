package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class UserServiceImpTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserServiceImp underTest = new UserServiceImp(userRepository);

    @Test
    void testLoginShouldSetLoggedInAdminWhenUsernameAndPasswordIsCorrect() {
        User user = new User("admin","admin", User.Role.ADMIN);
        Optional<User> expected = Optional.of(user);
        Mockito.when(userRepository.findByUsernameAndPassword("admin","admin"))
                .thenReturn(Optional.of(user));
        Optional<UserDto> actual = underTest.login("admin","admin");
        Assertions.assertEquals(expected.get().getUsername(),actual.get().getUsername());
        Assertions.assertEquals(expected.get().getRole(),actual.get().getRole());
        Mockito.verify(userRepository).findByUsernameAndPassword("admin","admin");
    }

    @Test
    void testLoginShouldReturnOptionalEmptyWhenUsernameOrPasswordAreNotCorrect() {
        Optional<UserDto> expected = Optional.empty();
        Mockito.when(userRepository.findByUsernameAndPassword("dummy", "dummy"))
                .thenReturn(Optional.empty());
        Optional<UserDto> actual = underTest.login("dummy", "dummy");
        Assertions.assertEquals(expected, actual);
        Mockito.verify(userRepository).findByUsernameAndPassword("dummy", "dummy");
    }

    @Test
    void testLogoutShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        Optional<UserDto> expected = Optional.empty();
        Optional<UserDto> actual = underTest.logout();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnTheLoggedInUserWhenThereIsALoggedInUser() {
        User user = new User("admin", "admin", User.Role.ADMIN);
        Mockito.when(userRepository.findByUsernameAndPassword("admin", "admin")).thenReturn(Optional.of(user));
        Optional<UserDto> expected = underTest.login("admin", "admin");
        Optional<UserDto> actual = underTest.describe();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        Optional<UserDto> expected = Optional.empty();
        Optional<UserDto> actual = underTest.describe();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testRegisterUser() {
        User user = new User("dummy","dummy", User.Role.USER);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        underTest.registerUser("dummy","dummy");
        Mockito.when(userRepository.findByUsernameAndPassword("dummy", "dummy")).thenReturn(Optional.of(user));
        Optional<UserDto> expected = underTest.login("dummy", "dummy");
        Optional<UserDto> actual = underTest.describe();
        Assertions.assertEquals(expected, actual);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(userRepository).findByUsernameAndPassword("dummy","dummy");
    }
}