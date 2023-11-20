package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {
    @Getter
    private final Map<Integer, Film> films = new HashMap<>();
    private int generatorId = 0;

    @GetMapping("/films")
    public List<Film> returnFilms() {
        log.info("GET /films");
        return new ArrayList<>(films.values());
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("PUT /films");
        if (film.getId() == null || !films.containsKey(film.getId())) {
            String msg = "Не возможно обновить данные фильма. Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new ValidationException(msg);
        }
        validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @PostMapping("/films")
    public Film postFilm(@RequestBody Film film) {
        log.info("POST /films");
        validate(film);
        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }

    private static void validate(Film film) {
        if ("".equals(film.getName())) {
            String msg = "Название фильма не может быть пустым";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if (film.getDescription().length() > 200) {
            String msg = "Длина описания превышает 200 символов";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String msg = "Дата релиза раньше 28 декабря 1895 года";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if (film.getDuration() < 0) {
            String msg = "Продолжительность фильма не положительная";
            log.error(msg);
            throw new ValidationException(msg);
        }
    }
}

