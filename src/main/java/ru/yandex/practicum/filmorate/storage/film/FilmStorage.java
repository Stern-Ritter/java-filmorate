package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Optional<Film> get(long id);

    List<Film> get();

    Film create(Film film);

    Film update(Film film);

    Film delete(long id);
}
