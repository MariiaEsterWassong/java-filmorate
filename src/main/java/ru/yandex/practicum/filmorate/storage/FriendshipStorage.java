package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Friendship;

/**
 * Interface for managing friendship data storage.
 */
public interface FriendshipStorage {
    /**
     * Saves a new friendship.
     *
     * @param friendship The friendship to be saved.
     */
    void saveFriendship(Friendship friendship);

    /**
     * Deletes an existing friendship.
     *
     * @param friendship The friendship to be deleted.
     */
    void deleteFriendship(Friendship friendship);
}
