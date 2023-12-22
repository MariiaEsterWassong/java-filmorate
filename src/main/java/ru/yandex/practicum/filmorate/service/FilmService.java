package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

/**
 * Service class for handling film-related operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;

    /**
     * Adds a like to a film.
     *
     * @param filmId The ID of the film.
     * @param userId The ID of the user giving the like.
     * @return The film with the updated like information.
     * @throws NotFoundException If the film or user with the specified IDs is not found.
     */
    public void addLike(int filmId, int userId) {
        if (filmStorage.getById(filmId) == null) {
            String msg = String.format("Фильма с id =%d нет", filmId);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (userStorage.getById(userId) == null) {
            String msg = String.format("Пользователя с id =%d нет", userId);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        likeStorage.saveLike(new Like(filmId, userId));
    }

    /**
     * Deletes a like from a film.
     *
     * @param filmId The ID of the film.
     * @param userId The ID of the user whose like should be deleted.
     * @return The film with the updated like information.
     * @throws NotFoundException If the film or user with the specified IDs is not found.
     */
    public void deleteLike(int filmId, int userId) {
        if (filmStorage.getById(filmId) == null) {
            String msg = String.format("Фильма с id =%d нет", filmId);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        if (userStorage.getById(userId) == null) {
            String msg = String.format("Пользователя с id =%d нет", userId);
            log.warn(msg);
            throw new NotFoundException(msg);
        }
        likeStorage.deleteLike(new Like(filmId, userId));
    }

    /**
     * Retrieves a list of the most liked films.
     *
     * @param count The number of films to retrieve.
     * @return List of the most liked films.
     */
    public List<Film> returnMostLikedFilms(int count) {

        return filmStorage.getMostLikedFilms(count);
    }

    /**
     * Retrieves all films.
     *
     * @return List of all films.
     */
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    /**
     * Updates a film.
     *
     * @param film The film to be updated.
     * @return The updated film.
     */
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    /**
     * Saves a new film.
     *
     * @param film The film to be saved.
     * @return The saved film.
     */
    public Film save(Film film) {
        return filmStorage.save(film);
    }

    /**
     * Retrieves a film by ID.
     *
     * @param id The ID of the film.
     * @return The film with the specified ID.
     */
    public Film getById(int id) {
        return filmStorage.getById(id);

    }
}
