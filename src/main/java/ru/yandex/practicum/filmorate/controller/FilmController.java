package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.ValidationUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for managing films.
 */
@Slf4j
@RestController
public class FilmController {
    /**
     * Map to store films with their unique identifiers.
     */
    @Getter
    private final Map<Integer, Film> films = new HashMap<>();

    /**
     * Variable to generate unique identifiers for films.
     */
    private int generatorId = 0;

    /**
     * Retrieves a list of films.
     *
     * @return List of films.
     */
    @GetMapping("/films")
    public List<Film> returnFilms() {
        log.info("GET /films");
        return new ArrayList<>(films.values());
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
        if (film.getId() == null || !films.containsKey(film.getId())) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new ValidationException(msg);
        }
        ValidationUtils.validateFilm(film);
        films.put(film.getId(), film);
        return film;
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
        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }
}

