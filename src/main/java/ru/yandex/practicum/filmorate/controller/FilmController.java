package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Exceptions;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/films")
public class FilmController {
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private static int currentIdx = 1;

    private static int getNextIdx() {
        return currentIdx++;
    }

    private final Map<Integer, Film> films;

    public FilmController() {
        films = new HashMap<>();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        log.info(String.format("POST '/films', parameters={%s}", film));

        try {
            FilmValidator.validate(film);

            Integer id = film.getId();
            if (id != null && films.containsKey(id)) {
                throw new FilmAlreadyExistException(String.format(Exceptions.FILM_ALREADY_EXISTS_TEMPLATE, id));
            }

            id = getNextIdx();
            film.setId(id);
            films.put(id, film);

            return film;
        } catch (ValidationException ex) {
            log.warn(String.format("POST '/films', request body={%s} validation exception: %s.", film, ex.getMessage()));
            throw new ValidationException(ex.getMessage());
        } catch (FilmAlreadyExistException ex) {
            log.warn(String.format("POST '/films' exception: %s.", ex.getMessage()));
            throw new FilmAlreadyExistException(ex.getMessage());
        }
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info(String.format("PUT '/films', parameters={%s}", film));

        try {
            FilmValidator.validate(film);

            Integer id = film.getId();
            if (id == null) {
                id = getNextIdx();
                film.setId(id);
            } else if (!films.containsKey(id)) {
                throw new NotFoundException(String.format(Exceptions.FILM_NOT_EXISTS_TEMPLATE, id));
            }
            films.put(id, film);

            return film;
        } catch (ValidationException ex) {
            log.warn(String.format("PUT '/films', request body={%s} validation exception: %s.", film, ex.getMessage()));
            throw new ValidationException(ex.getMessage());
        }
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }
}
