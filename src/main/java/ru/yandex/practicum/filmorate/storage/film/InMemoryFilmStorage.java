package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.Exceptions;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static long currentIdx = 1;

    private static long getNextIdx() {
        return currentIdx++;
    }

    private final Map<Long, Film> films;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public Optional<Film> get(long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> get() {
        return List.copyOf(films.values());
    }

    @Override
    public Film create(Film film) {
        long id = getNextIdx();
        film.setId(id);
        films.put(id, film);

        return film;
    }

    @Override
    public Film update(Film film) {
        Long id = film.getId();
        if (id == null || !films.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, id));
        }
        films.put(id, film);

        return film;
    }

    @Override
    public Film delete(long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, id));
        }
        Film film = films.get(id);
        films.remove(id);

        return film;
    }
}
