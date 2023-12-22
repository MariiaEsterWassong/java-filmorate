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
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Repository
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Film> getAll() {
        String sqlQuery = "SELECT f.*,\n" +
                "m.name AS name_mpa,\n" +
                "FROM film AS f\n" +
                "JOIN mpa AS m ON f.id_mpa = m.id";
        List<Film> films = jdbcTemplate.query(sqlQuery, getFilmMapper());
        GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
        for (Film film : films) {
            List<Genre> genres = genreDbStorage.getGenresByFilmId(film.getId());
            film.setGenres(genres);
        }
        return films;
    }

    @Override
    public Film update(Film film) {
        getById(film.getId());
        String sqlQuery = "update film set " +
                "name = ?, description = ?, releaseDate = ?, duration = ?, id_mpa = ? " +
                "where id = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getMpa().getId()
                , film.getId());

        if (film.getGenres() != null) {
            GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
            genreDbStorage.insertFilmGenres(film);
        }

        return getById(film.getId());
    }

    @Override
    public Film save(Film film) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("id");
        int id = insert.executeAndReturnKey(filmToMap(film)).intValue();
        film.setId(id);

        if (film.getGenres() != null) {

            GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
            genreDbStorage.insertFilmGenres(film);
        }
        return film;
    }

    @Override
    public Film getById(int id) {
        try {
            String sqlQuery = "SELECT f.*,\n" +
                    "m.name AS name_mpa,\n" +
                    "FROM film AS f\n" +
                    "JOIN mpa AS m ON f.id_mpa = m.id\n" +
                    "WHERE f.id = ?";

            Film film = jdbcTemplate.queryForObject(sqlQuery, getFilmMapper(), id);
            GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
            List<Genre> genres = genreDbStorage.getGenresByFilmId(id);
            film.setGenres(genres);

            return film;
        } catch (
                EmptyResultDataAccessException e) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
    }

    @Override
    public List<Film> getMostLikedFilms(int count) {

        String sqlQuery = "select ftj.*, " +
                "m.name AS name_mpa " +
                "from (select f.*, " +
                "c.count " +
                "from film as f left join " +
                "(select id_film, " +
                "count(id_film) as count " +
                "from film_likes " +
                "group by id_film) as c on f.id = c.id_film) as ftj JOIN mpa AS m ON ftj.id_mpa = m.id " +
                "order by ftj.count DESC " +
                "limit ?";

        List<Film> films = jdbcTemplate.query(sqlQuery, getFilmMapper(), count);
        GenreDbStorage genreDbStorage = new GenreDbStorage(jdbcTemplate);
        for (Film film : films) {
            List<Genre> genres = genreDbStorage.getGenresByFilmId(film.getId());
            film.setGenres(genres);
        }
        return films;
    }

    private static Map<String, Object> filmToMap(Film film) {
        return Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "releaseDate", film.getReleaseDate(),
                "duration", film.getDuration(),
                "id_mpa", film.getMpa().getId()
        );
    }

    private static RowMapper<Film> getFilmMapper() {
        return (rs, rowNum) -> new Film(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("releaseDate").toLocalDate(),
                rs.getInt("duration"),
                new Mpa(rs.getInt("id_mpa"), rs.getString("name_mpa"))
        );
    }
}
