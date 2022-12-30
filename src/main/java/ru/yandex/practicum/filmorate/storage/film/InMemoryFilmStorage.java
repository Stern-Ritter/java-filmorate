package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.Exceptions;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
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
    public Film get(long id) throws NotFoundException {
        return Optional.ofNullable(films.get(id))
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, id)));
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Film add(Film film) throws FilmAlreadyExistException {
        Long id = film.getId();
        if (id != null && films.containsKey(id)) {
            throw new FilmAlreadyExistException(String.format(Exceptions.FILM_ALREADY_EXISTS_TEMPLATE, id));
        }

        id = getNextIdx();
        film.setId(id);
        films.put(id, film);

        return film;
    }

    @Override
    public Film update(Film film) throws NotFoundException {
        Long id = film.getId();
        if (id == null) {
            id = getNextIdx();
            film.setId(id);
        } else if (!films.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, id));
        }

        films.put(id, film);

        return film;
    }

    @Override
    public Film delete(long id) throws NotFoundException {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, id));
        }

        Film film = films.get(id);
        films.remove(id);

        return film;
    }
}
