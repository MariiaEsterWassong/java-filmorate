package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.util.ValidationUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing films.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class FilmController {

    /**
     * Service responsible for film-related operations.
     */
    private final FilmService filmService;

    /**
     * Retrieves a list of films.
     *
     * @return List of films.
     */
    @GetMapping("/films")
    public List<Film> returnFilms() {
        log.info("GET /films");
        return new ArrayList<>(filmService.getAll());
    }

    /**
     * Updates an existing film.
     *
     * @param film The film to be updated.
     * @return The updated film.
     * @throws ValidationException If the film ID is not specified or does not exist in the database.
     */
    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("PUT /films");
        ValidationUtils.validateFilm(film);
        return filmService.update(film);
    }

    /**
     * Creates a new film.
     *
     * @param film The film to be created.
     * @return The created film.
     */
    @PostMapping("/films")
    public Film postFilm(@RequestBody Film film) {
        log.info("POST /films");
        ValidationUtils.validateFilm(film);
        return filmService.save(film);
    }

    /**
     * Retrieves a film by ID.
     *
     * @param id The ID of the film.
     * @return The film with the specified ID.
     */
    @GetMapping("/films/{id}")
    public Film returnFilm(@PathVariable("id") Integer id) {
        log.info("GET /films/{id}");
        return filmService.getById(id);
    }

    /**
     * Adds a like to a film.
     *
     * @param id     The ID of the film.
     * @param userId The ID of the user giving the like.
     * @return The film with the updated like information.
     */
    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        log.info("PUT /films/{id}/like/{userId}");
        filmService.addLike(id, userId);
    }

    /**
     * Deletes a like from a film.
     *
     * @param id     The ID of the film.
     * @param userId The ID of the user whose like should be deleted.
     * @return The film with the updated like information.
     */
    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        log.info("DELETE /films/{id}/like/{userId}");
        filmService.deleteLike(id, userId);
    }

    /**
     * Retrieves a list of the most liked films.
     *
     * @param count The number of films to retrieve (default is 10).
     * @return List of the most liked films.
     * @throws IncorrectParameterException If the count is not a positive integer.
     */
    @GetMapping("/films/popular")
    public List<Film> returnMostLikedFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        if (count <= 0) {
            throw new IncorrectParameterException("count");
        }
        log.info("GET /films/popular?count={count}");
        return filmService.returnMostLikedFilms(count);
    }
}

