package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.ValidationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for managing users.
 */
@Slf4j
@RestController
public class UserController {
    /**
     * Map to store users with their unique identifiers.
     */
    @Getter
    private final Map<Integer, User> users = new HashMap<>();

    /**
     * Variable to generate unique identifiers for users.
     */
    private int generatorId = 0;

    /**
     * Retrieves a list of users.
     *
     * @return List of users.
     */
    @GetMapping("/users")
    public List<User> returnUsers() {
        log.info("GET /users");
        return new ArrayList<>(users.values());
    }

    /**
     * Updates an existing user.
     *
     * @param user The user to be updated.
     * @return The updated user.
     * @throws ValidationException If the user ID is not specified or does not exist in the database.
     */
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("PUT /users");
        if (user.getId() == null || !users.containsKey(user.getId())) {
            String msg = "Идентификатор не указан идентификатор или отсутствует в базе";
            log.error(msg);
            throw new ValidationException(msg);
        }
        ValidationUtils.validateUser(user);
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    @PostMapping("/users")
    public User postUser(@RequestBody User user) {
        log.info("POST /users");
        ValidationUtils.validateUser(user);
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }
}


