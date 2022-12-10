package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate EARLIEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private static final int MIN_DURATION = 1;


    public static final String EMPTY_REQUEST_BODY_EXCEPTION = "Request body does not contain film data";
    public static final String NAME_VALIDATION_EXCEPTION = "Film name should not be empty.";
    public static final String DESCRIPTION_VALIDATION_EXCEPTION = String.format(
            "Maximum film description length is %d characters.",
            MAX_DESCRIPTION_LENGTH);
    public static final String RELEASE_DATE_VALIDATION_EXCEPTION = String.format(
            "Earliest film release date is %s.",
            EARLIEST_RELEASE_DATE
    );
    public static final String DURATION_VALIDATION_EXCEPTION = String.format(
            "Minimum film duration is %d minute.",
            MIN_DURATION
    );

    public static void validate(Film film) throws ValidationException {
        if (film == null) {
            throw new ValidationException(EMPTY_REQUEST_BODY_EXCEPTION);
        }

        String name = film.getName();
        String description = film.getDescription();
        LocalDate releaseDate = film.getReleaseDate();
        int duration = film.getDuration();


        if (!isNameValid(name)) {
            throw new ValidationException(NAME_VALIDATION_EXCEPTION);
        }

        if (!isDescriptionValid(description)) {
            throw new ValidationException(DESCRIPTION_VALIDATION_EXCEPTION);
        }

        if (!isReleaseDateValid(releaseDate)) {
            throw new ValidationException(RELEASE_DATE_VALIDATION_EXCEPTION);
        }

        if (!isDurationValid(duration)) {
            throw new ValidationException(DURATION_VALIDATION_EXCEPTION);
        }
    }

    private static boolean isNotNull(Object field) {
        return field != null;
    }

    private static boolean isNameValid(String name) {
        return isNotNull(name) && !name.isBlank();
    }

    private static boolean isDescriptionValid(String description) {
        return isNotNull(description) && description.length() <= MAX_DESCRIPTION_LENGTH;
    }

    private static boolean isReleaseDateValid(LocalDate releaseDate) {
        return isNotNull(releaseDate) && !EARLIEST_RELEASE_DATE.isAfter(releaseDate);
    }

    private static boolean isDurationValid(int duration) {
        return duration >= MIN_DURATION;
    }
}
