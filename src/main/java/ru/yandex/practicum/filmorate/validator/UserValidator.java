package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {
    private static final String REQUIRED_EMAIL_CHARACTER = "@";
    private static final String WHITESPACE_CHARACTER = " ";

    public static final String EMPTY_REQUEST_BODY_EXCEPTION = "Request body does not contain user data";
    public static final String EMAIL_VALIDATION_EXCEPTION = "Email should not be empty and should contain '@' character.";
    public static final String LOGIN_VALIDATION_EXCEPTION = "Login should not be empty and contain whitespaces.";
    public static final String BIRTHDAY_VALIDATION_EXCEPTION = "Birthdate should not be after current date.";

    public static void validate(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException(EMPTY_REQUEST_BODY_EXCEPTION);
        }

        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthdate = user.getBirthday();

        if (!isEmailValid(email)) {
            throw new ValidationException(EMAIL_VALIDATION_EXCEPTION);
        }

        if (!isLoginValid(login)) {
            throw new ValidationException(LOGIN_VALIDATION_EXCEPTION);
        }

        if (!isBirthdateValid(birthdate)) {
            throw new ValidationException(BIRTHDAY_VALIDATION_EXCEPTION);
        }
    }

    public static boolean isNotNull(Object field) {
        return field != null;
    }

    private static boolean isEmailValid(String email) {
        return isNotNull(email) && !email.isBlank() && email.contains(REQUIRED_EMAIL_CHARACTER);
    }

    private static boolean isLoginValid(String login) {
        return isNotNull(login) && !login.isBlank() && !login.contains(WHITESPACE_CHARACTER);
    }

    private static boolean isBirthdateValid(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        return isNotNull(birthdate) && !currentDate.isBefore(birthdate);
    }
}
