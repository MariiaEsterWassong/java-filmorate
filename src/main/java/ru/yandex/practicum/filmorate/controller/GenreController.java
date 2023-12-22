package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling requests related to genres.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreService genreService;

    /**
     * Retrieves all genres.
     *
     * @return List of all genres.
     */
    @GetMapping("/genres")
    public List<Genre> returnGenres() {
        log.info("GET /genres");
        return new ArrayList<>(genreService.getAll());
    }

    /**
     * Retrieves a specific genre by its ID.
     *
     * @param id The ID of the genre.
     * @return The genre with the specified ID.
     */
    @GetMapping("/genres/{id}")
    public Genre returnGenre(@PathVariable("id") Integer id) {
        log.info("GET /genres/{id}");
        return genreService.getById(id);
    }
}
