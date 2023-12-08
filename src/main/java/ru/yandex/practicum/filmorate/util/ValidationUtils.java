package ru.yandex.practicum.filmorate.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

/**
 * Utility class for validating Film and User objects.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {
    /**
     * Validates the properties of a Film object.
     *
     * @param film The Film object to be validated.
     * @throws ValidationException If validation fails.
     */
    public static void validateFilm(Film film) {
        if ("".equals(film.getName())) {
            String msg = "Название фильма не может быть пустым";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if (film.getDescription().length() > 200) {
            String msg = "Длина описания превышает 200 символов";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String msg = "Дата релиза раньше 28 декабря 1895 года";
            log.error(msg);
            throw new ValidationException(msg);
        }
        if (film.getDuration() < 0) {
            String msg = "Продолжительность фильма не положительная";
            log.error(msg);
            throw new ValidationException(msg);
        }
    }

    /**
     * Validates the properties of a User object.
     *
     * @param user The User object to be validated.
     * @throws ValidationException If validation fails.
     */
    public static void validateUser(User user) {
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
