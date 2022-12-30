package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film get(long id) throws NotFoundException;

    Collection<Film> getAll();

    Film add(Film film) throws FilmAlreadyExistException;

    Film update(Film film) throws NotFoundException;

    Film delete(long id) throws NotFoundException;
}
