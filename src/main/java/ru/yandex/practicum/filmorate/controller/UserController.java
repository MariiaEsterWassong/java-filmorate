package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Getter
    private final Map<Integer, User> users = new HashMap<>();
    private int generatorId = 0;
    @GetMapping("/users")
    public List<User> returnUsers() {
        log.info("GET /users");
        return new ArrayList<>(users.values());
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("PUT /users");
        if (user.getId() == null || !users.containsKey(user.getId())) {
            String msg = "Не возможно обновить данные пользователя. Идентификатор не указан идентификатор или отсутствует в базе";
            log.error(msg);
            throw new ValidationException(msg);
        }
        validate(user);
        users.put(user.getId(), user);
        return user;
    }
    @PostMapping("/users")
    public User postUser(@RequestBody User user) {
        log.info("POST /users");
        validate(user);
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }
    private static void validate(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if ("".equals(user.getEmail()) || !user.getEmail().contains("@")) {
            String msg = "Электронная почта не указана или не содержит @";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if ("".equals(user.getLogin()) || user.getLogin().contains(" ")) {
            String msg = "Логин не указан или содержит пробелы";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            String msg = "дата рождения не может быть в будущем";
            log.error(msg);
            throw new ValidationException(msg);
        }
    }
}


