package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FilmGenre {
    private Integer idFilm;
    private Integer idGenre;
}
