package ru.yandex.practicum.filmorate.exception;

public class FilmAlreadyExistException extends RuntimeException {
    public FilmAlreadyExistException() {
    }

    public FilmAlreadyExistException(String message) {
        super(message);
    }

    public FilmAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
