package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

@RequiredArgsConstructor
@Repository
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void saveLike(Like like) {

        String sqlQuery = "insert into film_likes(id_film, id_user) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                like.getIdFilm(),
                like.getIdUser());
    }

    @Override
    public void deleteLike(Like like) {

        String sqlQuery = "delete from film_likes where id_film = ? AND id_user = ?";
        jdbcTemplate.update(sqlQuery,
                like.getIdFilm(),
                like.getIdUser());
    }
}
