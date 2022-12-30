package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public Film getFilm(long id) {
        return filmStorage.get(id);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public Film addLike(long filmId, long userId) {
        Film film = filmStorage.get(filmId);
        userStorage.get(userId);

        Set<Long> likes = Optional.ofNullable(film.getLikes()).orElse(new HashSet<>());
        likes.add(userId);
        film.setLikes(likes);
        filmStorage.update(film);

        return film;
    }

    public Film deleteLike(long filmId, long userId) {
        Film film = filmStorage.get(filmId);
        userStorage.get(userId);

        Set<Long> likes = Optional.ofNullable(film.getLikes()).orElse(new HashSet<>());
        likes.remove(userId);
        film.setLikes(likes);
        filmStorage.update(film);

        return film;
    }

    public Collection<Film> getPopularFilms(int count) {
        Collection<Film> films = filmStorage.getAll();

        if (films == null) return Collections.emptyList();

        return films.stream()
                .sorted(Collections.reverseOrder(
                        Comparator.comparingInt(film -> Optional.ofNullable(film.getLikes()).orElse(new HashSet<>()).size())
                ))
                .limit(count)
                .collect(Collectors.toList());
    }
}
