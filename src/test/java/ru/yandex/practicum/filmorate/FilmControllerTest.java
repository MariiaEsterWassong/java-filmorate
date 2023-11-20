package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    public void setUp() {
        filmController = new FilmController();
    }

    @Test
    public void returnUsers_shouldReturnListOfUsers() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Road movie");
        film.setReleaseDate(LocalDate.of(2000, 02, 02));
        film.setDuration(60);
        filmController.postFilm(film);
        List<Film> postedFilms = new ArrayList<>();
        postedFilms.add(film);
        Assertions.assertEquals(postedFilms, filmController.returnFilms());
    }

    @Test
    public void updateFilm_shouldValidateAndUpdateFilm() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Road movie");
        film.setReleaseDate(LocalDate.of(2000, 02, 02));
        film.setDuration(60);
        filmController.postFilm(film);
        int filmId = film.getId();
        Film updatedFilm = new Film();
        updatedFilm.setId(filmId);
        updatedFilm.setName("New Name");
        updatedFilm.setDescription("Road movie");
        updatedFilm.setReleaseDate(LocalDate.of(2000, 02, 02));
        updatedFilm.setDuration(60);
        filmController.updateFilm(updatedFilm);
        Assertions.assertEquals(updatedFilm, filmController.getFilms().get(filmId));
    }

    @Test
    public void postFilm_shouldValidateFilmAndAddToMap() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Road movie");
        film.setReleaseDate(LocalDate.of(2000, 02, 02));
        film.setDuration(60);
        Assertions.assertThrows(ValidationException.class, () -> filmController.postFilm(film));
        film.setName("Name");
        String description = "";
        while (description.length() < 201) {
            description += "a";
        }
        film.setDescription(description);
        Assertions.assertThrows(ValidationException.class, () -> filmController.postFilm(film));
        film.setDescription("Road movie");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        Assertions.assertThrows(ValidationException.class, () -> filmController.postFilm(film));
        film.setReleaseDate(LocalDate.of(2000, 02, 02));
        film.setDuration(-1);
        Assertions.assertThrows(ValidationException.class, () -> filmController.postFilm(film));
        film.setDuration(60);
        filmController.postFilm(film);
        Assertions.assertEquals(film, filmController.returnFilms().get(0));
    }
}

