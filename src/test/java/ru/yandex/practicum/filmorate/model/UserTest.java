package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private static final long DEFAULT_ID = 1;
    private static final String DEFAULT_EMAIL = "test_user@mail.ru";
    private static final String EMPTY_EMAIL = "";
    private static final String EMAIL_WITHOUT_AT_SIGN = "test_user2mail.ru";
    private static final String DEFAULT_NAME = "User";
    private static final String DEFAULT_LOGIN = "Stern-Ritter";
    private static final String EMPTY_LOGIN = "";
    private static final String LOGIN_WITH_WHITESPACES = "Default user";
    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.now();
    private static final LocalDate INVALID_BIRTHDAY = LocalDate.now().plusDays(1);

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void userWithValidFields() {
        final User user = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();

        final Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void userWithInvalidEmail() {
        final User userWithNullEmail = User.builder().id(DEFAULT_ID).email(null).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmptyEmail = User.builder().id(DEFAULT_ID).email(EMPTY_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmailWithoutAtSign = User.builder().id(DEFAULT_ID).email(EMAIL_WITHOUT_AT_SIGN)
                .login(DEFAULT_LOGIN).name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();

        final Set<ConstraintViolation<User>> userWithNullEmailViolations = validator.validate(userWithNullEmail);
        assertEquals(userWithNullEmailViolations.size(), 1);

        final ConstraintViolation<User> userWithNullEmailViolation = userWithNullEmailViolations.iterator().next();
        assertEquals(UserValidator.EMAIL_VALIDATION_EXCEPTION, userWithNullEmailViolation.getMessage());
        assertEquals("email", userWithNullEmailViolation.getPropertyPath().toString());
        assertNull(userWithNullEmailViolation.getInvalidValue());

        final Set<ConstraintViolation<User>> userWithEmptyEmailViolations = validator.validate(userWithEmptyEmail);
        assertEquals(userWithEmptyEmailViolations.size(), 1);

        final ConstraintViolation<User> userWithEmptyEmailViolation = userWithEmptyEmailViolations.iterator().next();
        assertEquals(UserValidator.EMAIL_VALIDATION_EXCEPTION, userWithEmptyEmailViolation.getMessage());
        assertEquals("email", userWithEmptyEmailViolation.getPropertyPath().toString());
        assertEquals(EMPTY_EMAIL, userWithEmptyEmailViolation.getInvalidValue());

        final Set<ConstraintViolation<User>> userWithEmailWithoutAtSignViolations = validator.validate(userWithEmailWithoutAtSign);
        assertEquals(userWithEmailWithoutAtSignViolations.size(), 1);

        final ConstraintViolation<User> userWithEmailWithoutAtSignViolation = userWithEmailWithoutAtSignViolations.iterator().next();
        assertEquals(UserValidator.EMAIL_VALIDATION_EXCEPTION, userWithEmailWithoutAtSignViolation.getMessage());
        assertEquals("email", userWithEmailWithoutAtSignViolation.getPropertyPath().toString());
        assertEquals(EMAIL_WITHOUT_AT_SIGN, userWithEmailWithoutAtSignViolation.getInvalidValue());
    }

    @Test
    void addUserWithInvalidLogin() {
        final User userWithNullLogin = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(null).name(DEFAULT_NAME)
                .birthday(DEFAULT_BIRTHDAY).build();
        final User userWithEmptyLogin = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(EMPTY_LOGIN)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();
        final User userWithLoginWithWhitespaces = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(LOGIN_WITH_WHITESPACES)
                .name(DEFAULT_NAME).birthday(DEFAULT_BIRTHDAY).build();

        final Set<ConstraintViolation<User>> userWithNullLoginViolations = validator.validate(userWithNullLogin);
        assertEquals(userWithNullLoginViolations.size(), 1);

        final ConstraintViolation<User> userWithNullLoginViolation = userWithNullLoginViolations.iterator().next();
        assertEquals(UserValidator.LOGIN_VALIDATION_EXCEPTION, userWithNullLoginViolation.getMessage());
        assertEquals("login", userWithNullLoginViolation.getPropertyPath().toString());
        assertNull(userWithNullLoginViolation.getInvalidValue());

        final Set<ConstraintViolation<User>> userWithEmptyLoginViolations = validator.validate(userWithEmptyLogin);
        assertEquals(userWithEmptyLoginViolations.size(), 1);

        final ConstraintViolation<User> userWithEmptyLoginViolation = userWithEmptyLoginViolations.iterator().next();
        assertEquals(UserValidator.LOGIN_VALIDATION_EXCEPTION, userWithEmptyLoginViolation.getMessage());
        assertEquals("login", userWithEmptyLoginViolation.getPropertyPath().toString());
        assertEquals(EMPTY_LOGIN, userWithEmptyLoginViolation.getInvalidValue());

        final Set<ConstraintViolation<User>> userWithLoginWithWhitespacesViolations = validator.validate(userWithLoginWithWhitespaces);
        assertEquals(userWithLoginWithWhitespacesViolations.size(), 1);

        final ConstraintViolation<User> userWithLoginWithWhitespacesViolation = userWithLoginWithWhitespacesViolations.iterator().next();
        assertEquals(UserValidator.LOGIN_VALIDATION_EXCEPTION, userWithLoginWithWhitespacesViolation.getMessage());
        assertEquals("login", userWithLoginWithWhitespacesViolation.getPropertyPath().toString());
        assertEquals(LOGIN_WITH_WHITESPACES, userWithLoginWithWhitespacesViolation.getInvalidValue());
    }

    @Test
    void userWithInvalidBirthDate() {
        final User userWithNullBirthdate = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(null).build();
        final User userWithInvalidBirthdate = User.builder().id(DEFAULT_ID).email(DEFAULT_EMAIL).login(DEFAULT_LOGIN)
                .name(DEFAULT_NAME).birthday(INVALID_BIRTHDAY).build();

        final Set<ConstraintViolation<User>> userWithNullBirthdateViolations = validator.validate(userWithNullBirthdate);
        assertEquals(userWithNullBirthdateViolations.size(), 1);

        final ConstraintViolation<User> userWithNullBirthdateViolation = userWithNullBirthdateViolations.iterator().next();
        assertEquals(UserValidator.BIRTHDAY_VALIDATION_EXCEPTION, userWithNullBirthdateViolation.getMessage());
        assertEquals("birthday", userWithNullBirthdateViolation.getPropertyPath().toString());
        assertNull(userWithNullBirthdateViolation.getInvalidValue());

        final Set<ConstraintViolation<User>> userWithInvalidBirthdateViolations = validator.validate(userWithInvalidBirthdate);
        assertEquals(userWithInvalidBirthdateViolations.size(), 1);

        final ConstraintViolation<User> userWithInvalidBirthdateViolation = userWithInvalidBirthdateViolations.iterator().next();
        assertEquals(UserValidator.BIRTHDAY_VALIDATION_EXCEPTION, userWithInvalidBirthdateViolation.getMessage());
        assertEquals("birthday", userWithInvalidBirthdateViolation.getPropertyPath().toString());
        assertEquals(INVALID_BIRTHDAY, userWithInvalidBirthdateViolation.getInvalidValue());
    }
}
