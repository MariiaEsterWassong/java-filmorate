package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes = new HashSet<>();

    public int getNumberOfLikes() {
        return likes.size();
    }

}

