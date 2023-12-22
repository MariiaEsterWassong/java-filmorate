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
        Film filmNumberOne = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genres);
        Film filmNumberTwo = new Film(1, "updated name","updated description", LocalDate.of(1991, 2, 2), 61, new Mpa(1, "G"), genres);
        filmsToSave.add(filmNumberOne);
        filmsToSave.add(filmNumberTwo);
        filmStorage.save(filmNumberOne);
        filmStorage.save(filmNumberTwo);

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
        List<Genre> genresNumberOne = new ArrayList<>();
        genresNumberOne.add(new Genre(1, "Комедия"));
        genresNumberOne.add(new Genre(2, "Драма"));

        Film newFilm = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genresNumberOne);
        filmStorage.save(newFilm);
        List<Genre> genresNumberTwo = new ArrayList<>();
        genresNumberTwo.add(new Genre(1, "Комедия"));
        genresNumberTwo.add(new Genre(2, "Драма"));
        genresNumberTwo.add(new Genre(3, "Мультфильм"));
        Film filmToUpdate = new Film(1, "updated name","updated description", LocalDate.of(1991, 2, 2), 61, new Mpa(1, "G"), genresNumberTwo);

        filmStorage.update(filmToUpdate);

        Assertions.assertThat(filmStorage.getById(1))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(filmToUpdate);
    }
}
