package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

import static ru.yandex.practicum.filmorate.validator.UserValidator.BIRTHDAY_VALIDATION_EXCEPTION;
import static ru.yandex.practicum.filmorate.validator.UserValidator.EMAIL_VALIDATION_EXCEPTION;
import static ru.yandex.practicum.filmorate.validator.UserValidator.EMPTY_REQUEST_BODY_EXCEPTION;
import static ru.yandex.practicum.filmorate.validator.UserValidator.LOGIN_VALIDATION_EXCEPTION;

@Data
@Builder
@NotNull(message = EMPTY_REQUEST_BODY_EXCEPTION)
public class User {
    private Long id;

    @NotEmpty(message = EMAIL_VALIDATION_EXCEPTION)
    @Email(message = EMAIL_VALIDATION_EXCEPTION)
    private String email;

    @NotEmpty(message = LOGIN_VALIDATION_EXCEPTION)
    @Pattern(regexp = "^\\S*$",message = LOGIN_VALIDATION_EXCEPTION)
    private String login;

    private String name;

    @NotNull(message = BIRTHDAY_VALIDATION_EXCEPTION)
    @PastOrPresent(message = BIRTHDAY_VALIDATION_EXCEPTION)
    private LocalDate birthday;

    private Set<Long> friends;
}
