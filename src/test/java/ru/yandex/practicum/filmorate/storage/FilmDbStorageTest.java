package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private FilmDbStorage filmStorage;

    @BeforeEach
    public void setUp() {
        filmStorage = new FilmDbStorage(jdbcTemplate);
    }

    @Test
    public void testFindAllFilms() {
        List<Film> filmsToSave = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Film film_1 = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genres);
        Film film_2 = new Film(1, "updated name","updated description", LocalDate.of(1991, 2, 2), 61, new Mpa(1, "G"), genres);
        filmsToSave.add(film_1);
        filmsToSave.add(film_2);
        filmStorage.save(film_1);
        filmStorage.save(film_2);

        List<Film> savedFilms = filmStorage.getAll();

        Assertions.assertThat(savedFilms)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(filmsToSave);
    }

    @Test
    public void testFindFilmById() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Комедия"));
        genres.add(new Genre(2, "Драма"));
        Film newFilm = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genres);
        filmStorage.save(newFilm);

        Film savedFilm = filmStorage.getById(1);

        Assertions.assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testSaveFilm() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Комедия"));
        genres.add(new Genre(2, "Драма"));

        Film newFilm = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genres);

        filmStorage.save(newFilm);

        Assertions.assertThat(filmStorage.getById(1))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testUpdateFilm() {
        List<Genre> genres_1 = new ArrayList<>();
        genres_1.add(new Genre(1, "Комедия"));
        genres_1.add(new Genre(2, "Драма"));

        Film newFilm = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genres_1);
        filmStorage.save(newFilm);
        List<Genre> genres_2 = new ArrayList<>();
        genres_2.add(new Genre(1, "Комедия"));
        genres_2.add(new Genre(2, "Драма"));
        genres_2.add(new Genre(3, "Мультфильм"));
        Film filmToUpdate = new Film(1, "updated name","updated description", LocalDate.of(1991, 2, 2), 61, new Mpa(1, "G"), genres_2);

        filmStorage.update(filmToUpdate);

        Assertions.assertThat(filmStorage.getById(1))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(filmToUpdate);
    }
}
