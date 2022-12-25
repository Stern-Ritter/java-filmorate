package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {
    private LocalDate earliestDate;

    @Override
    public void initialize(AfterDate afterDate) {
        earliestDate = LocalDate.parse(afterDate.value(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public boolean isValid(
            LocalDate date, ConstraintValidatorContext context) {

        if (date == null) {
            return true;
        }

        return !earliestDate.isAfter(date);
    }
}