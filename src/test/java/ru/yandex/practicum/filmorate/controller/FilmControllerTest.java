package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private static final int DEFAULT_ID = 1;
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

    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void addFilmWithValidFields() {
        final Film film = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        Film postedFilm = filmController.addFilm(film);

        assertNotNull(postedFilm, "Film is not returned.");
        assertEquals(postedFilm, film, "The posted film does not match.");
    }

    @Test
    void addEmptyFilm() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(null),
                "Expected addFilm() to throw ValidationException"
        );

        assertTrue(exception.getMessage().contains(FilmValidator.EMPTY_REQUEST_BODY_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void addFilmWithInvalidName() {
        final Film filmWithNullName = Film.builder().id(DEFAULT_ID).name(null).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();
        final Film filmWithEmptyName = Film.builder().id(DEFAULT_ID).name(EMPTY_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final ValidationException filmWithNullNameException = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(filmWithNullName),
                "Expected addFilm() to throw ValidationException"
        );

        final ValidationException filmWithEmptyNameException = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(filmWithEmptyName),
                "Expected addFilm() to throw ValidationException"
        );

        assertTrue(filmWithNullNameException.getMessage().contains(FilmValidator.NAME_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(filmWithEmptyNameException.getMessage().contains(FilmValidator.NAME_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void addFilmWithInvalidDescription() {
        final Film filmWithNullDescription = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(null)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();
        final Film filmWithTooLongDescription = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(TOO_LONG_DESCRIPTION).releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final ValidationException filmWithNullDescriptionException = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(filmWithNullDescription),
                "Expected addFilm() to throw ValidationException"
        );

        final ValidationException filmWithTooLongDescriptionException = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(filmWithTooLongDescription),
                "Expected addFilm() to throw ValidationException"
        );

        assertTrue(filmWithNullDescriptionException.getMessage().contains(FilmValidator.DESCRIPTION_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(filmWithTooLongDescriptionException.getMessage().contains(FilmValidator.DESCRIPTION_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void addFilmWithInvalidReleaseDate() {
        final Film filmWithNullReleaseDate = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION).releaseDate(null).duration(DEFAULT_DURATION).build();
        final Film filmWithToEarlierReleaseDate = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION).releaseDate(TOO_EARLIER_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final ValidationException filmWithNullReleaseDateException = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(filmWithNullReleaseDate),
                "Expected addFilm() to throw ValidationException"
        );


        final ValidationException filmWithToEarlierReleaseDateException = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(filmWithToEarlierReleaseDate),
                "Expected addFilm() to throw ValidationException"
        );

        assertTrue(filmWithNullReleaseDateException.getMessage().contains(FilmValidator.RELEASE_DATE_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(filmWithToEarlierReleaseDateException.getMessage().contains(FilmValidator.RELEASE_DATE_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void addTestWithInvalidDuration() {
        final Film film = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(NON_POSITIVE_DURATION).build();

        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.addFilm(film),
                "Expected addFilm() to throw ValidationException"
        );

        assertTrue(exception.getMessage().contains(FilmValidator.DURATION_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }


    @Test
    void updateFilmWithValidFields() {
        final Film film = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        filmController.addFilm(film);
        Film postedFilm = filmController.updateFilm(film);

        assertNotNull(postedFilm, "Film is not returned.");
        assertEquals(postedFilm, film, "The puted film does not match.");
    }

    @Test
    void updateEmptyFilm() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(null),
                "Expected updateFilm() to throw ValidationException"
        );

        assertTrue(exception.getMessage().contains(FilmValidator.EMPTY_REQUEST_BODY_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateFilmWithInvalidName() {
        final Film filmWithNullName = Film.builder().id(DEFAULT_ID).name(null).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();
        final Film filmWithEmptyName = Film.builder().id(DEFAULT_ID).name(EMPTY_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final ValidationException filmWithNullNameException = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(filmWithNullName),
                "Expected updateFilm() to throw ValidationException"
        );

        final ValidationException filmWithEmptyNameException = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(filmWithEmptyName),
                "Expected updateFilm() to throw ValidationException"
        );

        assertTrue(filmWithNullNameException.getMessage().contains(FilmValidator.NAME_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(filmWithEmptyNameException.getMessage().contains(FilmValidator.NAME_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateFilmWithInvalidDescription() {
        final Film filmWithNullDescription = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(null)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();
        final Film filmWithTooLongDescription = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(TOO_LONG_DESCRIPTION).releaseDate(DEFAULT_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final ValidationException filmWithNullDescriptionException = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(filmWithNullDescription),
                "Expected updateFilm() to throw ValidationException"
        );

        final ValidationException filmWithTooLongDescriptionException = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(filmWithTooLongDescription),
                "Expected updateFilm() to throw ValidationException"
        );

        assertTrue(filmWithNullDescriptionException.getMessage().contains(FilmValidator.DESCRIPTION_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(filmWithTooLongDescriptionException.getMessage().contains(FilmValidator.DESCRIPTION_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateFilmWithInvalidReleaseDate() {
        final Film filmWithNullReleaseDate = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION).releaseDate(null).duration(DEFAULT_DURATION).build();
        final Film filmWithToEarlierReleaseDate = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION).releaseDate(TOO_EARLIER_RELEASE_DATE).duration(DEFAULT_DURATION).build();

        final ValidationException filmWithNullReleaseDateException = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(filmWithNullReleaseDate),
                "Expected updateFilm() to throw ValidationException"
        );


        final ValidationException filmWithToEarlierReleaseDateException = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(filmWithToEarlierReleaseDate),
                "Expected updateFilm() to throw ValidationException"
        );

        assertTrue(filmWithNullReleaseDateException.getMessage().contains(FilmValidator.RELEASE_DATE_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
        assertTrue(filmWithToEarlierReleaseDateException.getMessage().contains(FilmValidator.RELEASE_DATE_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }

    @Test
    void updateTestWithInvalidDuration() {
        final Film film = Film.builder().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
                .releaseDate(DEFAULT_RELEASE_DATE).duration(NON_POSITIVE_DURATION).build();

        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.updateFilm(film),
                "Expected updateFilm() to throw ValidationException"
        );

        assertTrue(exception.getMessage().contains(FilmValidator.DURATION_VALIDATION_EXCEPTION),
                "Text of exception is incorrect");
    }
}
