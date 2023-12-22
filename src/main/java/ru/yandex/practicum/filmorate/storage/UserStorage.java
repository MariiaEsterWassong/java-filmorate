package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Interface for managing user storage.
 */
public interface UserStorage {
    /**
     * Retrieves all users from the storage.
     *
     * @return List of all users.
     */
    List<User> getAll();

    /**
     * Updates a user in the storage.
     *
     * @param user The user to be updated.
     * @return The updated user.
     */
    User update(User user);

    /**
     * Saves a new user to the storage.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    User save(User user);

    /**
     * Retrieves a user by its ID from the storage.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID.
     */
    User getById(int id);

    /**
     * Retrieves a list of friends for a user with the specified ID.
     *
     * @param id The ID of the user.
     * @return List of friends for the user with the specified ID.
     */
    List<User> getAllFriends(int id);
}
