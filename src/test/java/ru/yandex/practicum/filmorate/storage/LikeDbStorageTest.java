package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static org.hamcrest.Matchers.is;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LikeDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private LikeDbStorage likeDbStorage;
    private FilmDbStorage filmDbStorage;
    private UserDbStorage userDbStorage;

    @DirtiesContext
    @BeforeEach
    public void setUp() {

        likeDbStorage = new LikeDbStorage(jdbcTemplate);
        filmDbStorage = new FilmDbStorage(jdbcTemplate);
        userDbStorage = new UserDbStorage(jdbcTemplate);
    }

    @DirtiesContext
    @Test
    public void testSaveLike() {
        List<Genre> genres = new ArrayList<>();
        Film film = new Film(1, "name", "description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genres);
        filmDbStorage.save(film);
        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userDbStorage.save(user);
        Like like = new Like(1, 1);

        likeDbStorage.saveLike(like);

        Assertions.assertThat(filmDbStorage.getMostLikedFilms(1).get(0))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film);
    }

    @DirtiesContext
    @Test
    public void testDeleteLike() {
        Film film = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, null));
        filmDbStorage.save(film);
        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userDbStorage.save(user);
        Like like = new Like(1,1);
        likeDbStorage.saveLike(like);

        likeDbStorage.deleteLike(like);

        Assertions.assertThat(filmDbStorage.getMostLikedFilms(1));
                is(empty());
    }

    @DirtiesContext
    @Test
    public void testGetMostLikedFilms() {
        List<Film> mostLikedFilms = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Film film = new Film(1, "name","description", LocalDate.of(1990, 1, 1), 60, new Mpa(1, "G"), genres);
        Film filmNumberTwo = new Film(2, "updated name","updated description", LocalDate.of(1991, 2, 2), 61, new Mpa(1, "G"), genres);
        mostLikedFilms.add(film);
        mostLikedFilms.add(filmNumberTwo);

        filmDbStorage.save(film);
        filmDbStorage.save(filmNumberTwo);

        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userDbStorage.save(user);
        User userNumberTwo = new User(2, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userDbStorage.save(user);

        Like like = new Like(1,1);
        Like likeNumberTwo = new Like(1,2);
        Like likeNumberThree = new Like(2,2);
        likeDbStorage.saveLike(like);
        likeDbStorage.saveLike(likeNumberTwo);
        likeDbStorage.saveLike(likeNumberThree);
        List<Film> mostLikedFilmsDb = filmDbStorage.getMostLikedFilms(10);
        System.out.println(mostLikedFilmsDb.toString());
        System.out.println(mostLikedFilms.toString());

        Assertions.assertThat(mostLikedFilmsDb)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(mostLikedFilms);
    }
}
