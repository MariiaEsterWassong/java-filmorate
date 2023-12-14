package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

/**
 * Interface for managing film storage.
 */
public interface FilmStorage {
    /**
     * Retrieves all films from the storage.
     *
     * @return List of all films.
     */
    List<Film> getAll();

    /**
     * Updates a film in the storage.
     *
     * @param film The film to be updated.
     * @return The updated film.
     */
    Film update(Film film);

    /**
     * Saves a new film to the storage.
     *
     * @param film The film to be saved.
     * @return The saved film.
     */
    Film save(Film film);

    /**
     * Retrieves a film by its ID from the storage.
     *
     * @param id The ID of the film.
     * @return The film with the specified ID.
     */
    Film getById(int id);
}
