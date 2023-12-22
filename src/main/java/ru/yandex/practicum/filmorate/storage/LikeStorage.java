package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Like;

/**
 * Interface for managing like-related data storage.
 */
public interface LikeStorage {
    /**
     * Saves a new like.
     *
     * @param like The like to be saved.
     */
    void saveLike(Like like);

    /**
     * Deletes an existing like.
     *
     * @param like The like to be deleted.
     */
    void deleteLike(Like like);
}
