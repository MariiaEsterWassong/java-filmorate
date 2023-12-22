package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling requests related to MPAs (Motion Picture Association rating).
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class MpaController {

    private final MpaService mpaService;

    /**
     * Retrieves a list of all MPAs.
     *
     * @return List of all MPAs.
     */
    @GetMapping("/mpa")
    public List<Mpa> returnListMpa() {
        log.info("GET /mpa");
        return new ArrayList<>(mpaService.getAll());
    }

    /**
     * Retrieves a specific MPA by its ID.
     *
     * @param id The ID of the MPA.
     * @return The MPA with the specified ID.
     */
    @GetMapping("/mpa/{id}")
    public Mpa returnMpa(@PathVariable("id") Integer id) {
        log.info("GET /mpa/{id}");
        return mpaService.getById(id);
    }
}
