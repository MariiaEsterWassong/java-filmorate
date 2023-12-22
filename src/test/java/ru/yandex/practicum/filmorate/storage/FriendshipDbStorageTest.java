package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.db.FriendshipDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;

import static java.util.Optional.empty;
import static org.hamcrest.Matchers.is;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FriendshipDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private FriendshipDbStorage friendshipDbStorage;
    private UserDbStorage userDbStorage;

    @BeforeEach
    public void setUp() {

        friendshipDbStorage = new FriendshipDbStorage(jdbcTemplate);
        userDbStorage = new UserDbStorage(jdbcTemplate);
    }

    @Test
    public void testSaveFriendship() {
        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userDbStorage.save(user);
        User friend = new User(2, "friend@email.ru", "friend", "Name Friend", LocalDate.of(1991, 2, 2));
        userDbStorage.save(friend);
        Friendship friendship = new Friendship(1,2);

        friendshipDbStorage.saveFriendship(friendship);


        Assertions.assertThat(userDbStorage.getAllFriends(1).get(0))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(friend);
    }

    @Test
    public void testDeleteFriendship() {

        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userDbStorage.save(user);
        User friend = new User(2, "friend@email.ru", "friend", "Name Friend", LocalDate.of(1991, 2, 2));
        userDbStorage.save(friend);
        Friendship friendship = new Friendship(1,2);
        friendshipDbStorage.saveFriendship(friendship);

        friendshipDbStorage.deleteFriendship(friendship);

        Assertions.assertThat(userDbStorage.getAllFriends(1));
                is(empty());
    }
}
