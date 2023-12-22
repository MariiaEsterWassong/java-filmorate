package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;

import  org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    UserDbStorage userStorage;

    @BeforeEach
    public void setUp() {
        userStorage = new UserDbStorage(jdbcTemplate);
    }

    @DirtiesContext
    @Test
    public void testFindUserById() {
        User newUser = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.save(newUser);

        User savedUser = userStorage.getById(1);

        Assertions.assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUser);
    }

    @DirtiesContext
    @Test
    public void testUpdateUser() {

        User newUser = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.save(newUser);
        User userToUpdate = new User(1, "friend@email.ru", "friend", "Name Friend", LocalDate.of(1991, 2, 2));

        userStorage.update(userToUpdate);

        Assertions.assertThat(userStorage.getById(1))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(userToUpdate);
    }
}
