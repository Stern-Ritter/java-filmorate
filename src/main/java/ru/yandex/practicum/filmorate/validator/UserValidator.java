package ru.yandex.practicum.filmorate.validator;

public class UserValidator {
    public static final String EMPTY_REQUEST_BODY_EXCEPTION = "Request body does not contain user data";
    public static final String EMAIL_VALIDATION_EXCEPTION = "Email should not be empty and should contain '@' character.";
    public static final String LOGIN_VALIDATION_EXCEPTION = "Login should not be empty and contain whitespaces.";
    public static final String BIRTHDAY_VALIDATION_EXCEPTION = "Birthdate should not be after current date.";
}
