package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;


@RestController
@RequestMapping("/films")
public class FilmController {
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info(String.format("POST '/films', parameters={%s}", film));

        try {
            return filmService.addFilm(film);
        } catch (FilmAlreadyExistException ex) {
            log.warn(String.format("POST '/films', request body={%s} exception: %s", film, ex.getMessage()));
            throw new FilmAlreadyExistException(ex.getMessage());
        }
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info(String.format("PUT '/films', parameters={%s}", film));

        try {
            return filmService.updateFilm(film);
        } catch (NotFoundException ex) {
            log.warn(String.format("PUT '/films', request body={%s} exception: %s", film, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        try {
            return filmService.getFilm(id);
        } catch (NotFoundException ex) {
            log.warn(String.format("GET '/films/%s, exception: %s", id, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("GET '/films'.");

        return filmService.getAllFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable(name = "id") long filmId, @PathVariable long userId) {
        log.info(String.format("PUT '/films/%s/like/%s'", filmId, userId));

        try {
            return filmService.addLike(filmId, userId);
        } catch (NotFoundException ex) {
            log.warn(String.format("PUT '/films/%s/like/%s', exception: %s", filmId, userId, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable(name = "id") long filmId, @PathVariable long userId) {
        log.info(String.format("DELETE '/films/%s/like/%s'", filmId, userId));

        try {
            return filmService.deleteLike(filmId, userId);
        } catch (NotFoundException ex) {
            log.warn(String.format("DELETE '/films/%s/like/%s', exception: %s", filmId, userId, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") @Min(1) int count) {
        log.info(String.format("GET '/films/popular?count=%s'", count));

        return filmService.getPopularFilms(count);
    }
}
