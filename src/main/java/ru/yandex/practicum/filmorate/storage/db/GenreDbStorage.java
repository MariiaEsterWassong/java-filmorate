package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("SELECT * FROM genre", getGenreMapper());
    }

    @Override
    public Genre getById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM genre WHERE id = ?", getGenreMapper(), id);
        } catch (
                EmptyResultDataAccessException e) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
    }

    @Override
    public List<Genre> getGenresByFilmId(Integer filmId) {
        String sql = "SELECT fg.id_genre AS id, g.name " +
                "FROM film_genre fg " +
                "JOIN genre g ON g.id = fg.id_genre " +
                "WHERE fg.id_film = ? ";
        List<Genre> filmGenres = jdbcTemplate.query(sql, getGenreMapper(), filmId);
        return filmGenres;
    }

    @Override
    public void insertFilmGenres(Film film) {
        if (!film.getGenres().isEmpty()) {
            List<Genre> genres = new ArrayList<>();
            for (Genre genre : (film.getGenres())) genres.add(genre);
            String sql = "insert into film_genre (id_film, id_genre)  VALUES (?, ?);";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, genres.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return genres.size();
                }
            });


        } else {
            deleteFilmGenresByFilmId(film.getId());
        }
    }

    @Override
    public void deleteFilmGenresByFilmId(Integer filmId) {
        String sqlQuery = "delete from film_genre where id_film = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    private static RowMapper<Genre> getGenreMapper() {
        return (rs, rowNum) -> new Genre(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
