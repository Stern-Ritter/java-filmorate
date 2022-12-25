package ru.yandex.practicum.filmorate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = AfterDateValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface AfterDate {
    String value();

    String message() default "The date must be later than the specified date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}