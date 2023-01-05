package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Validated
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info(String.format("POST '/films', parameters={%s}", film));
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info(String.format("PUT '/films', parameters={%s}", film));
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable long id) {
        log.info(String.format("GET '/films/%s'.", id));
        return filmService.get(id);
    }

    @GetMapping
    public Collection<Film> get() {
        log.info("GET '/films'.");
        return filmService.get();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable(name = "id") long filmId, @PathVariable long userId) {
        log.info(String.format("PUT '/films/%s/like/%s'", filmId, userId));
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable(name = "id") long filmId, @PathVariable long userId) {
        log.info(String.format("DELETE '/films/%s/like/%s'", filmId, userId));
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10") @Min(1) int count) {
        log.info(String.format("GET '/films/popular?count=%s'", count));
        return filmService.getPopular(count);
    }
}
