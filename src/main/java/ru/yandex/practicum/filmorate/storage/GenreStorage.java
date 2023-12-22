package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

/**
 * Interface for managing genre-related data storage.
 */
public interface GenreStorage {

    /**
     * Retrieves a list of all genres.
     *
     * @return List of all genres.
     */
    List<Genre> getAll();

    /**
     * Retrieves a specific genre by its ID.
     *
     * @param id The ID of the genre.
     * @return The genre with the specified ID.
     */
    Genre getById(int id);

    /**
     * Retrieves genres associated with a specific film.
     *
     * @param filmId The ID of the film.
     * @return List of genres associated with the specified film.
     */
    List<Genre> getGenresByFilmId(Integer filmId);

    /**
     * Inserts genre associations for a film.
     *
     * @param film The film for which genre associations should be inserted.
     */
    void insertFilmGenres(Film film);

    /**
     * Deletes genre associations for a film by its ID.
     *
     * @param filmId The ID of the film for which genre associations should be deleted.
     */
    void deleteFilmGenresByFilmId(Integer filmId);
}
