package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Repository
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM user_filmorate", getUserMapper());
    }

    @Override
    public List<User> getAllFriends(int id) {
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM user_filmorate " +
                        "WHERE id IN (SELECT id_friend " +
                        "FROM friendship " +
                        "WHERE id = ?" +
                        ")",

                getUserMapper(), id);
    }

    @Override
    public User update(User user) {
        getById(user.getId());
        String sqlQuery = "update user_filmorate set " +
                "email = ?, login = ?, name = ?, birthday = ? " +
                "where id = ?";
        jdbcTemplate.update(sqlQuery
                , user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday()
                , user.getId());

        return user;
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_filmorate")
                .usingGeneratedKeyColumns("id");
        int id = insert.executeAndReturnKey(userToMap(user)).intValue();
        user.setId(id);
        return user;
    }

    @Override
    public User getById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM user_filmorate WHERE id = ?", getUserMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
    }

    private static Map<String, Object> userToMap(User user) {
        return Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday()
        );
    }

    private static RowMapper<User> getUserMapper() {
        return (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate()
        );
    }
}
