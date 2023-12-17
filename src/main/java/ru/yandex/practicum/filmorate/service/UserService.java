package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for handling user-related operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;

    /**
     * Adds a friend to a user.
     *
     * @param id       The ID of the user.
     * @param friendId The ID of the friend to be added.
     * @return The user with the updated friends list.
     * @throws NotFoundException If the user or friend with the specified IDs is not found.
     */
    public User addFriend(int id, int friendId) {
        if (userStorage.getById(id) == null) {
            String msg = String.format("Пользователя с id =%d нет", id);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (userStorage.getById(friendId) == null) {
            String msg = String.format("Пользователя с id =%d нет", friendId);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
        return user;
    }

    /**
     * Deletes a friend from a user.
     *
     * @param id       The ID of the user.
     * @param friendId The ID of the friend to be deleted.
     * @return The user with the updated friends list.
     * @throws NotFoundException If the user or friend with the specified IDs is not found.
     */
    public User deleteFriend(int id, int friendId) {
        if (userStorage.getById(id) == null) {
            String msg = String.format("Пользователя с id =%d нет", id);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (userStorage.getById(friendId) == null) {
            String msg = String.format("Пользователя с id =%d нет", friendId);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        User user = userStorage.getById(id);
        user.getFriends().remove(friendId);
        User friend = userStorage.getById(friendId);
        friend.getFriends().remove(id);
        return user;
    }

    /**
     * Retrieves a list of friends for a user.
     *
     * @param id The ID of the user.
     * @return List of friends for the specified user.
     * @throws NotFoundException If the user with the specified ID is not found.
     */
    public List<User> returnFriends(int id) {
        List<User> friends = new ArrayList<>();
        if (userStorage.getById(id) == null) {
            String msg = String.format("Пользователя с id =%d нет", id);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        User user = userStorage.getById(id);
        friends = user.getFriends().stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());

        return friends;
    }

    /**
     * Retrieves a list of common friends between two users.
     *
     * @param id      The ID of the first user.
     * @param otherId The ID of the second user.
     * @return List of common friends.
     * @throws NotFoundException If either of the users with the specified IDs is not found.
     */
    public List<User> returnCommonFriends(int id, int otherId) {
        if (userStorage.getById(id) == null) {
            String msg = String.format("Пользователя с id =%d нет", id);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (userStorage.getById(otherId) == null) {
            String msg = String.format("Пользователя с id =%d нет", otherId);
            log.warn(msg);
            throw new NotFoundException(msg);
        }

        List<User> commonFriends = new ArrayList<>();
        Set<Integer> userFriends = userStorage.getById(id).getFriends();
        Set<Integer> otherUserFriends = userStorage.getById(otherId).getFriends();
        userFriends.stream()
                .filter(otherUserFriends::contains)
                .map(userStorage::getById)
                .forEach(commonFriends::add);

        return commonFriends;
    }

    /**
     * Retrieves all users.
     *
     * @return List of all users.
     */
    public List<User> getAll() {
        return userStorage.getAll();
    }

    /**
     * Updates a user.
     *
     * @param user The user to be updated.
     * @return The updated user.
     */
    public User update(User user) {
        return userStorage.update(user);
    }

    /**
     * Saves a new user.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public User save(User user) {
        return userStorage.save(user);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID.
     */
    public User getById(int id) {
        return userStorage.getById(id);
    }
}
