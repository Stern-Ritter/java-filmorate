package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private static final int DEFAULT_ID = 1;
    private static final String DEFAULT_EMAIL = "test_user@mail.ru";
    private static final String EMPTY_EMAIL = "";
    private static final String EMAIL_WITHOUT_AT_SIGN = "test_user2mail.ru";
    private static final String DEFAULT_NAME = "User";
    private static final String DEFAULT_LOGIN = "Stern-Ritter";
    private static final String EMPTY_LOGIN = "";
    private static final String LOGIN_WITH_WHITESPACES = "Default user";
    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.now();
    private static final LocalDate INVALID_BIRTHDAY = LocalDate.now().plusDays(1);

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void addUserWithValidFields() {
        final User user = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();

        User postedUser = userController.addUser(user);

        assertNotNull(postedUser, "User is not returned.");
        assertEquals(postedUser, user, "The posted user does not match.");
    }

    @Test
    void addEmptyUser() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> userController.addUser(null),
                "Expected addFilm() to throw ValidationException"
        );

        assertTrue(exception.getMessage().contains(UserValidator.EMPTY_REQUEST_BODY_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void addUserWithInvalidEmail() {
        final User userWithNullEmail = User.builder().id(DEFAULT_ID).email(null).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmptyEmail = User.builder().id(DEFAULT_ID).email(EMPTY_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmailWithoutAtSign = User.builder().id(DEFAULT_ID).email(EMAIL_WITHOUT_AT_SIGN)
                .login(DEFAULT_LOGIN).name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();

        final ValidationException userWithNullEmailException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithNullEmail),
                "Expected addUser() to throw ValidationException"
        );

        final ValidationException userWithEmptyEmailException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithEmptyEmail),
                "Expected addUser() to throw ValidationException"
        );

        final ValidationException userWithEmailWithoutAtSignException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithEmailWithoutAtSign),
                "Expected addUser() to throw ValidationException"
        );

        assertTrue(userWithNullEmailException.getMessage().contains(UserValidator.EMAIL_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithEmptyEmailException.getMessage().contains(UserValidator.EMAIL_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithEmailWithoutAtSignException.getMessage().contains(UserValidator.EMAIL_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void addUserWithInvalidLogin() {
        final User userWithNullLogin = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(null).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmptyLogin = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(EMPTY_LOGIN)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();
        final User userWithLoginWithWhitespaces = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(LOGIN_WITH_WHITESPACES)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();
        final ValidationException userWithNullLoginException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithNullLogin),
                "Expected addUser() to throw ValidationException"
        );

        final ValidationException userWithEmptyLoginException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithEmptyLogin),
                "Expected addUser() to throw ValidationException"
        );

        final ValidationException userWithLoginWithWhitespacesException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithLoginWithWhitespaces),
                "Expected addUser() to throw ValidationException"
        );

        assertTrue(userWithNullLoginException.getMessage().contains(UserValidator.LOGIN_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithEmptyLoginException.getMessage().contains(UserValidator.LOGIN_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithLoginWithWhitespacesException.getMessage().contains(UserValidator.LOGIN_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void addUserWithInvalidBirthDate() {
        final User userWithNullBirthdate = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(null).build();
        final User userWithInvalidBirthdate = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(INVALID_BIRTHDAY).build();

        final ValidationException userWithNullBirthdateException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithNullBirthdate),
                "Expected addUser() to throw ValidationException"
        );

        final ValidationException userWithInvalidBirthdateException = assertThrows(
                ValidationException.class,
                () -> userController.addUser(userWithInvalidBirthdate),
                "Expected addUser() to throw ValidationException"
        );

        assertTrue(userWithNullBirthdateException.getMessage().contains(UserValidator.BIRTHDAY_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");

        assertTrue(userWithInvalidBirthdateException.getMessage().contains(UserValidator.BIRTHDAY_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateUserWithValidFields() {
        final User user = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();

        userController.addUser(user);
        User postedUser = userController.updateUser(user);

        assertNotNull(postedUser, "User is not returned.");
        assertEquals(postedUser, user, "The posted user does not match.");
    }

    @Test
    void updateEmptyUser() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(null),
                "Expected addFilm() to throw ValidationException"
        );

        assertTrue(exception.getMessage().contains(UserValidator.EMPTY_REQUEST_BODY_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateUserWithInvalidEmail() {
        final User userWithNullEmail = User.builder().id(DEFAULT_ID).email(null).login(DEFAULT_LOGIN).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmptyEmail = User.builder().id(DEFAULT_ID).email(EMPTY_EMAIL).login(DEFAULT_LOGIN).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmailWithoutAtSign = User.builder().id(DEFAULT_ID).email(EMAIL_WITHOUT_AT_SIGN).login(DEFAULT_LOGIN).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();

        final ValidationException userWithNullEmailException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithNullEmail),
                "Expected updateUser() to throw ValidationException"
        );

        final ValidationException userWithEmptyEmailException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithEmptyEmail),
                "Expected updateUser() to throw ValidationException"
        );

        final ValidationException userWithEmailWithoutAtSignException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithEmailWithoutAtSign),
                "Expected updateUser() to throw ValidationException"
        );

        assertTrue(userWithNullEmailException.getMessage().contains(UserValidator.EMAIL_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithEmptyEmailException.getMessage().contains(UserValidator.EMAIL_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithEmailWithoutAtSignException.getMessage().contains(UserValidator.EMAIL_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateUserWithInvalidLogin() {
        final User userWithNullLogin = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(null).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmptyLogin = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(EMPTY_LOGIN).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();
        final User userWithLoginWithWhitespaces = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(LOGIN_WITH_WHITESPACES)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();

        final ValidationException userWithNullLoginException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithNullLogin),
                "Expected updateUser() to throw ValidationException"
        );

        final ValidationException userWithEmptyLoginException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithEmptyLogin),
                "Expected updateUser() to throw ValidationException"
        );

        final ValidationException userWithLoginWithWhitespacesException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithLoginWithWhitespaces),
                "Expected updateUser() to throw ValidationException"
        );

        assertTrue(userWithNullLoginException.getMessage().contains(UserValidator.LOGIN_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithEmptyLoginException.getMessage().contains(UserValidator.LOGIN_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(userWithLoginWithWhitespacesException.getMessage().contains(UserValidator.LOGIN_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateUserWithInvalidBirthDate() {
        final User userWithNullBirthdate = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(null).build();
        final User userWithInvalidBirthdate = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(INVALID_BIRTHDAY).build();

        final ValidationException userWithNullBirthdateException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithNullBirthdate),
                "Expected updateUser() to throw ValidationException"
        );

        final ValidationException userWithInvalidBirthdateException = assertThrows(
                ValidationException.class,
                () -> userController.updateUser(userWithInvalidBirthdate),
                "Expected updateUser() to throw ValidationException"
        );

        assertTrue(userWithNullBirthdateException.getMessage().contains(UserValidator.BIRTHDAY_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");

        assertTrue(userWithInvalidBirthdateException.getMessage().contains(UserValidator.BIRTHDAY_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }
}
