package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the UserStorage interface.
 */
@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    /**
     * Map to store users with their unique identifiers.
     */
    @Getter
    private final Map<Integer, User> users = new HashMap<>();

    /**
     * Variable to generate unique identifiers for users.
     */
    private int generatorId = 0;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User update(User user) {
        if ((user.getId() == null) || !users.containsKey(user.getId())) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User save(User user) {
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            String msg = "Идентификатор отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        return users.get(id);
    }
}
