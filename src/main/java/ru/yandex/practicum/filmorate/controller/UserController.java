package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Exceptions;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static int currentIdx = 1;

    private static int getNextIdx() {
        return currentIdx++;
    }

    private final Map<Integer, User> users;

    public UserController() {
        users = new HashMap<>();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        log.info(String.format("POST '/users', parameters={%s}", user));

        try {
            UserValidator.validate(user);

            Integer id = user.getId();
            if (id != null && users.containsKey(id)) {
                throw new UserAlreadyExistException(String.format(Exceptions.USER_ALREADY_EXISTS_TEMPLATE, id));
            }

            id = getNextIdx();
            user.setId(id);

            String name = user.getName();
            if(name == null) {
                String login = user.getLogin();
                user.setName(login);
            }

            users.put(id, user);

            return user;
        } catch (ValidationException ex) {
            log.warn(String.format("POST '/users', request body={%s} validation exception: %s.", user, ex.getMessage()));
            throw new ValidationException(ex.getMessage());
        } catch (UserAlreadyExistException ex) {
            log.warn(String.format("POST '/users' exception: %s.", ex.getMessage()));
            throw new UserAlreadyExistException(ex.getMessage());
        }
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info(String.format("PUT '/users', parameters={%s}", user));

        try {
            UserValidator.validate(user);

            Integer id = user.getId();
            if (id == null) {
                id = getNextIdx();
                user.setId(id);
            } else if (!users.containsKey(id)) {
                throw new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, id));
            }

            users.put(id, user);

            return user;
        } catch (ValidationException ex) {
            log.warn(String.format("PUT '/users', request body={%s} validation exception: %s.", user, ex.getMessage()));
            throw new ValidationException(ex.getMessage());
        }
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
