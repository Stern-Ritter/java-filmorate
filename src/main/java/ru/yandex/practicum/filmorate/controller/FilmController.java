package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;

import static ru.yandex.practicum.filmorate.logger.Template.DELETE_FILM_LIKE_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.GET_FILMS_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.GET_FILM_BY_ID_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.GET_POPULAR_FILMS_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.POST_FILM_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.PUT_FILM_LIKE_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.PUT_FILM_TEMPLATE;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Validated
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info(String.format(POST_FILM_TEMPLATE, film));
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info(String.format(PUT_FILM_TEMPLATE, film));
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable long id) {
        log.info(String.format(GET_FILM_BY_ID_TEMPLATE, id));
        return filmService.get(id);
    }

    @GetMapping
    public List<Film> get() {
        log.info(GET_FILMS_TEMPLATE);
        return filmService.get();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable(name = "id") long filmId, @PathVariable long userId) {
        log.info(String.format(PUT_FILM_LIKE_TEMPLATE, filmId, userId));
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable(name = "id") long filmId, @PathVariable long userId) {
        log.info(String.format(DELETE_FILM_LIKE_TEMPLATE, filmId, userId));
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") @Min(1) int count) {
        log.info(String.format(GET_POPULAR_FILMS_TEMPLATE, count));
        return filmService.getPopular(count);
    }
}
