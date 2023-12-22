package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

/**
 * Interface for managing MPA-related data storage.
 */
public interface MpaStorage {

    /**
     * Retrieves a list of all MPA ratings.
     *
     * @return List of all MPA ratings.
     */
    List<Mpa> getAll();

    /**
     * Retrieves a specific MPA rating by its ID.
     *
     * @param id The ID of the MPA rating.
     * @return The MPA rating with the specified ID.
     */
    Mpa getById(int id);
}
