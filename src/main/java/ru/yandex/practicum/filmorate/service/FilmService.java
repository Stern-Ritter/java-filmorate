package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.Exceptions;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film get(long id) {
        return filmStorage.get(id)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, id)));
    }

    public Collection<Film> get() {
        return filmStorage.get();
    }

    public Film addLike(long filmId, long userId) {
        Film film = filmStorage.get(filmId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, filmId)));
        userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, userId)));

        Set<Long> likes = film.getLikes();
        likes.add(userId);

        return film;
    }

    public Film deleteLike(long filmId, long userId) {
        Film film = filmStorage.get(filmId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, filmId)));
        userStorage.get(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, userId)));

        Set<Long> likes = film.getLikes();
        likes.remove(userId);

        return film;
    }

    public Collection<Film> getPopular(int count) {
        Collection<Film> films = filmStorage.get();

        return films.stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(film -> film.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }
}
