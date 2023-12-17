package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the  FilmStorage interface.
 */
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    /**
     * Map to store films with their unique identifiers.
     */
    @Getter
    private final Map<Integer, Film> films = new HashMap<>();

    /**
     * Variable to generate unique identifiers for films.
     */
    private int generatorId = 0;

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film update(Film film) {
        if (film.getId() == null || !films.containsKey(film.getId())) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film save(Film film) {
        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(int id) {
        if (!films.containsKey(id)) {
            String msg = "Идентификатор отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        return films.get(id);
    }
}

