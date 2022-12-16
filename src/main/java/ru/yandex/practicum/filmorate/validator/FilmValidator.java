package ru.yandex.practicum.filmorate.validator;

public class FilmValidator {
    public static final int MAX_DESCRIPTION_LENGTH = 200;
    public static final String EARLIEST_RELEASE_DATE = "1895-12-28";
    public static final String EMPTY_REQUEST_BODY_EXCEPTION = "Request body does not contain film data";
    public static final String NAME_VALIDATION_EXCEPTION = "Film name should not be empty.";
    public static final String DESCRIPTION_VALIDATION_EXCEPTION = "Maximum film description length is 200 characters.";
    public static final String RELEASE_DATE_VALIDATION_EXCEPTION = "Earliest film release date is 1895-12-28.";
    public static final String DURATION_VALIDATION_EXCEPTION = "Film duration must be a strictly positive number.";
}
