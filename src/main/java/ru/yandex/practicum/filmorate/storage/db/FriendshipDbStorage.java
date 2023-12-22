package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class FriendshipDbStorage implements FriendshipStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveFriendship(Friendship friendship) {

        String sqlQuery = "insert into friendship(id, id_friend) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                friendship.getId(),
                friendship.getId_friend());
    }

    @Override
    public void deleteFriendship(Friendship friendship) {

        String sqlQuery = "delete from friendship where id = ? AND id_friend = ?";
        jdbcTemplate.update(sqlQuery,
                friendship.getId(),
                friendship.getId_friend());
    }

    private static RowMapper<Friendship> getFriendshipMapper() {
        return (rs, rowNum) -> new Friendship(
                rs.getInt("id"),
                rs.getInt("id_friend")
        );
    }

    private static Map<String, Object> friendshipToMap(Friendship friendship) {
        return Map.of(
                "id", friendship.getId(),
                "id_friend", friendship.getId_friend()
        );
    }
}
