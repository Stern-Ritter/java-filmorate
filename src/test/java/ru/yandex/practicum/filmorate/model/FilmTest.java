package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmTest {
    private static final long DEFAULT_ID = 1;
    private static final String DEFAULT_NAME = "Star Wars: Episode V - The Empire Strikes Back";
    private static final String EMPTY_NAME = "";
    private static final String DEFAULT_DESCRIPTION = "The Empire Strikes Back (also known as Star Wars: Episode V " +
            "– The Empire Strikes Back) is a 1980 American epic space opera film.";
    private static final String TOO_LONG_DESCRIPTION = "The Empire Strikes Back (also known as Star Wars: Episode V – " +
            "The Empire Strikes Back) is a 1980 American epic space opera film directed by Irvin Kershner from a " +
            "screenplay by Leigh Brackett and Lawrence Kasdan, based on a story by George Lucas. The sequel to Star " +
            "Wars (1977),[b] it is the second film in the Star Wars film series and the fifth chronological chapter of " +
            "the \"Skywalker Saga\". Set three years after the events of Star Wars, the film recounts the battle " +
            "between the malevolent Galactic Empire, led by the Emperor, and the Rebel Alliance, led by Princess Leia. " +
            "Luke Skywalker trains to master the Force so he can confront the powerful Sith lord, Darth Vader. The " +
            "ensemble cast includes Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams, Anthony Daniels, " +
            "David Prowse, Kenny Baker, Peter Mayhew, and Frank Oz.";
    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private static final LocalDate TOO_EARLIER_RELEASE_DATE = LocalDate.of(1895, 12, 27);
    private static final int DEFAULT_DURATION = 1;
    private static final int NON_POSITIVE_DURATION = 0;

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void filmWithValidFields() {
        final Film film = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    void filmWithInvalidName() {
        final Film filmWithNullName = Film.builder().id(DEFAULT_ID).name(null).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();
        final Film filmWithEmptyName = Film.builder().id(DEFAULT_ID).name(EMPTY_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final Set<ConstraintViolation<Film>> filmWithNullNameViolations = validator.validate(filmWithNullName);
        assertEquals(filmWithNullNameViolations.size(), 1);

        final ConstraintViolation<Film> filmWithNullNameViolation = filmWithNullNameViolations.iterator().next();
        assertEquals(FilmValidator.NAME_VALIDATION_EXCEPTION, filmWithNullNameViolation.getMessage());
        assertEquals("name", filmWithNullNameViolation.getPropertyPath().toString());
        assertNull(filmWithNullNameViolation.getInvalidValue());

        final Set<ConstraintViolation<Film>> filmWithEmptyNameViolations = validator.validate(filmWithEmptyName);
        assertEquals(filmWithNullNameViolations.size(), 1);

        final ConstraintViolation<Film> filmWithEmptyNameViolation = filmWithEmptyNameViolations.iterator().next();
        assertEquals(FilmValidator.NAME_VALIDATION_EXCEPTION, filmWithEmptyNameViolation.getMessage());
        assertEquals("name", filmWithEmptyNameViolation.getPropertyPath().toString());
        assertEquals(EMPTY_NAME, filmWithEmptyNameViolation.getInvalidValue());
    }

    @Test
    void filmWithInvalidDescription() {
        final Film filmWithNullDescription = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(null)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();
        final Film filmWithTooLongDescription = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(TOO_LONG_DESCRIPTION).releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final Set<ConstraintViolation<Film>> filmWithNullDescriptionViolations = validator.validate(filmWithNullDescription);
        assertEquals(filmWithNullDescriptionViolations.size(), 1);

        final ConstraintViolation<Film> filmWithNullDescriptionViolation = filmWithNullDescriptionViolations.iterator().next();
        assertEquals(FilmValidator.DESCRIPTION_VALIDATION_EXCEPTION, filmWithNullDescriptionViolation.getMessage());
        assertEquals("description", filmWithNullDescriptionViolation.getPropertyPath().toString());
        assertNull(filmWithNullDescriptionViolation.getInvalidValue());

        final Set<ConstraintViolation<Film>> filmWithTooLongDescriptionViolations = validator.validate(filmWithTooLongDescription);
        assertEquals(filmWithTooLongDescriptionViolations.size(), 1);

        final ConstraintViolation<Film> filmWithTooLongDescriptionViolation = filmWithTooLongDescriptionViolations.iterator().next();
        assertEquals(FilmValidator.DESCRIPTION_VALIDATION_EXCEPTION, filmWithTooLongDescriptionViolation.getMessage());
        assertEquals("description", filmWithTooLongDescriptionViolation.getPropertyPath().toString());
        assertEquals(TOO_LONG_DESCRIPTION, filmWithTooLongDescriptionViolation.getInvalidValue());
    }

    @Test
    void filmWithInvalidReleaseDate() {
        final Film filmWithNullReleaseDate = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION).releaseDate(null).duration(DEFAULT_DURATION).build();
        final Film filmWithToEarlierReleaseDate = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION).releaseDate(TOO_EARLIER_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final Set<ConstraintViolation<Film>> filmWithNullReleaseDateViolations = validator.validate(filmWithNullReleaseDate);
        assertEquals(filmWithNullReleaseDateViolations.size(), 1);

        final ConstraintViolation<Film> filmWithNullReleaseDateViolation = filmWithNullReleaseDateViolations.iterator().next();
        assertEquals(FilmValidator.RELEASE_DATE_VALIDATION_EXCEPTION, filmWithNullReleaseDateViolation.getMessage());
        assertEquals("releaseDate", filmWithNullReleaseDateViolation.getPropertyPath().toString());
        assertNull(filmWithNullReleaseDateViolation.getInvalidValue());

        final Set<ConstraintViolation<Film>> filmWithToEarlierReleaseDateViolations = validator.validate(filmWithToEarlierReleaseDate);
        assertEquals(filmWithToEarlierReleaseDateViolations.size(), 1);

        final ConstraintViolation<Film> filmWithToEarlierReleaseDateViolation = filmWithToEarlierReleaseDateViolations.iterator().next();
        assertEquals(FilmValidator.RELEASE_DATE_VALIDATION_EXCEPTION, filmWithToEarlierReleaseDateViolation.getMessage());
        assertEquals("releaseDate", filmWithToEarlierReleaseDateViolation.getPropertyPath().toString());
        assertEquals(TOO_EARLIER_RELEASE_DATE, filmWithToEarlierReleaseDateViolation.getInvalidValue());
    }

    @Test
    void filmWithInvalidDuration() {
        final Film filmWithNullDuration = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(null).build();
        final Film filmWithInvalid = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(NON_POSITIVE_DURATION).build();

        final Set<ConstraintViolation<Film>> filmWithNullDurationViolations = validator.validate(filmWithNullDuration);
        assertEquals(filmWithNullDurationViolations.size(), 1);

        final ConstraintViolation<Film> filmWithNullDurationViolation = filmWithNullDurationViolations.iterator().next();
        assertEquals(FilmValidator.DURATION_VALIDATION_EXCEPTION, filmWithNullDurationViolation.getMessage());
        assertEquals("duration", filmWithNullDurationViolation.getPropertyPath().toString());
        assertNull(filmWithNullDurationViolation.getInvalidValue());

        final Set<ConstraintViolation<Film>> filmWithInvalidViolations = validator.validate(filmWithInvalid);
        assertEquals(filmWithInvalidViolations.size(), 1);

        final ConstraintViolation<Film> filmWithInvalidViolation = filmWithInvalidViolations.iterator().next();
        assertEquals(FilmValidator.DURATION_VALIDATION_EXCEPTION, filmWithInvalidViolation.getMessage());
        assertEquals("duration", filmWithInvalidViolation.getPropertyPath().toString());
        assertEquals(NON_POSITIVE_DURATION, filmWithInvalidViolation.getInvalidValue());
    }
}
