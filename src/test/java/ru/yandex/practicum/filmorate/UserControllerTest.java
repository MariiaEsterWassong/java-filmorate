package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserControllerTest {
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
    }

    @Test
    public void returnUsers_shouldReturnListOfUsers() {
        User user = new User();
        user.setLogin("login");
        user.setEmail("user@mail.com");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2000, 2, 2));
        userController.postUser(user);
        List<User> postedUsers = new ArrayList<>();
        postedUsers.add(user);
        Assertions.assertEquals(postedUsers, userController.returnUsers());
    }

    @Test
    public void updateUser_shouldValidateAndUpdateUser() {
        User user = new User();
        user.setLogin("login");
        user.setEmail("user@mail.com");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2000, 2, 2));
        userController.postUser(user);
        int userId = user.getId();
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setLogin("login");
        updatedUser.setEmail("user@mail.com");
        updatedUser.setName("Name");
        updatedUser.setBirthday(LocalDate.of(2000, 2, 2));
        userController.updateUser(updatedUser);
        Assertions.assertEquals(updatedUser, userController.getUsers().get(userId));
    }

    @Test
    public void postUser_shouldValidateUserAndAddToMap() {
        User user = new User();
        user.setLogin("login");
        user.setEmail("user@mail.com");
        user.setName("");
        user.setBirthday(LocalDate.of(2000, 2, 2));
        userController.postUser(user);
        Assertions.assertEquals(user.getName(), userController.returnUsers().get(0).getName());
        user.setName("Name");
        user.setEmail("");
        Assertions.assertThrows(ValidationException.class, () -> userController.postUser(user));
        user.setEmail("usermail.com");
        Assertions.assertThrows(ValidationException.class, () -> userController.postUser(user));
        user.setEmail("user@mail.com");
        user.setLogin("");
        Assertions.assertThrows(ValidationException.class, () -> userController.postUser(user));
        user.setLogin("lo gin");
        Assertions.assertThrows(ValidationException.class, () -> userController.postUser(user));
        user.setLogin("login");
        user.setBirthday(LocalDate.now().plusDays(1));
        Assertions.assertThrows(ValidationException.class, () -> userController.postUser(user));
        user.setBirthday(LocalDate.of(2000, 2, 2));
        userController.postUser(user);
        Assertions.assertEquals(user, userController.returnUsers().get(0));
    }
}
