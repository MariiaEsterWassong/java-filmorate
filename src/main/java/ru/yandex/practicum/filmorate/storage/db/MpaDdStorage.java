package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MpaDdStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {
        return jdbcTemplate.query("SELECT * FROM mpa", getMpaMapper());
    }

    @Override
    public Mpa getById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM mpa WHERE id = ?", getMpaMapper(), id);
        } catch (
                EmptyResultDataAccessException e) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
    }

    private static RowMapper<Mpa> getMpaMapper() {
        return (rs, rowNum) -> new Mpa(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
