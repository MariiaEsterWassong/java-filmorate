package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.util.ValidationUtils;

import java.util.*;

/**
 * Controller class for managing users.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    /**
     * Service responsible for user-related operations.
     */
    private final UserService userService;

    /**
     * Retrieves a list of users.
     *
     * @return List of users.
     */
    @GetMapping("/users")
    public List<User> returnUsers() {
        log.info("GET /users");
        return new ArrayList<>(userService.getAll());
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
        ValidationUtils.validateUser(user);
        return userService.update(user);
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
        return userService.save(user);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID.
     */
    @GetMapping("/users/{id}")
    public User returnUser(@PathVariable("id") Integer id) {
        log.info("GET /users/{id}");
        return userService.getById(id);
    }

    /**
     * Adds a friend to a user.
     *
     * @param id       The ID of the user.
     * @param friendId The ID of the friend to be added.
     * @return The user with the updated friends list.
     */
    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        log.info("PUT /users/{id}/friends/{friendId}");
        return userService.addFriend(id, friendId);
    }

    /**
     * Deletes a friend from a user.
     *
     * @param id       The ID of the user.
     * @param friendId The ID of the friend to be deleted.
     * @return The user with the updated friends list.
     */
    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        log.info("DELETE /users/{id}/friends/{friendId}");
        return userService.deleteFriend(id, friendId);
    }

    /**
     * Retrieves a list of friends for a user.
     *
     * @param id The ID of the user.
     * @return List of friends for the specified user.
     */
    @GetMapping("/users/{id}/friends")
    public List<User> returnFriends(@PathVariable("id") Integer id) {
        log.info("GET /users/{id}/friends");
        return userService.returnFriends(id);
    }

    /**
     * Retrieves a list of common friends between two users.
     *
     * @param id      The ID of the first user.
     * @param otherId The ID of the second user.
     * @return List of common friends.
     */
    @GetMapping("/users/{id}/friends/common/{otherId}")
    List<User> returnCommonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        log.info("GET /users/{id}/friends/common/{otherId}");
        return userService.returnCommonFriends(id, otherId);
    }
}


