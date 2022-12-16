package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.AfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.validator.FilmValidator.DESCRIPTION_VALIDATION_EXCEPTION;
import static ru.yandex.practicum.filmorate.validator.FilmValidator.DURATION_VALIDATION_EXCEPTION;
import static ru.yandex.practicum.filmorate.validator.FilmValidator.EARLIEST_RELEASE_DATE;
import static ru.yandex.practicum.filmorate.validator.FilmValidator.EMPTY_REQUEST_BODY_EXCEPTION;
import static ru.yandex.practicum.filmorate.validator.FilmValidator.MAX_DESCRIPTION_LENGTH;
import static ru.yandex.practicum.filmorate.validator.FilmValidator.NAME_VALIDATION_EXCEPTION;
import static ru.yandex.practicum.filmorate.validator.FilmValidator.RELEASE_DATE_VALIDATION_EXCEPTION;

@Data
@Builder
@NotNull(message = EMPTY_REQUEST_BODY_EXCEPTION)
public class Film {
    private Integer id;

    @NotBlank(message = NAME_VALIDATION_EXCEPTION)
    private String name;

    @NotNull(message = DESCRIPTION_VALIDATION_EXCEPTION)
    @Size(max = MAX_DESCRIPTION_LENGTH, message = DESCRIPTION_VALIDATION_EXCEPTION)
    private String description;

    @NotNull(message = RELEASE_DATE_VALIDATION_EXCEPTION)
    @AfterDate(value = EARLIEST_RELEASE_DATE, message = RELEASE_DATE_VALIDATION_EXCEPTION)
    private LocalDate releaseDate;

    @NotNull(message = DURATION_VALIDATION_EXCEPTION)
    @Positive(message = DURATION_VALIDATION_EXCEPTION)
    private Integer duration;
}
