package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info(String.format("POST '/users', parameters={%s}", user));

        try {
            return userService.addUser(user);
        } catch (UserAlreadyExistException ex) {
            log.warn(String.format("POST '/users', request body={%s} exception: %s", user, ex.getMessage()));
            throw new UserAlreadyExistException(ex.getMessage());
        }
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info(String.format("PUT '/users', parameters={%s}", user));

        try {
            return userService.updateUser(user);
        } catch (NotFoundException ex) {
            log.warn(String.format("PUT '/users', request body={%s} exception: %s", user, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info(String.format("GET '/users/%s'", id));

        try {
            return userService.getUser(id);
        } catch (NotFoundException ex) {
            log.warn(String.format("GET '/users/%s, exception: %s", id, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("GET '/users'");

        return userService.getAllUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable(name = "id") long userId, @PathVariable long friendId) {
        log.info(String.format("PUT '/users/%s/friends/%s'", userId, friendId));

        try {
            return userService.addFriend(userId, friendId);
        } catch (NotFoundException ex) {
            log.warn(String.format("PUT '/users/%s/friends/%s', exception: %s", userId, friendId, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable(name = "id") long userId, @PathVariable long friendId) {
        log.info(String.format("DELETE '/users/%s/friends/%s'", userId, friendId));

        try {
            return userService.deleteFriend(userId, friendId);
        } catch (NotFoundException ex) {
            log.warn(String.format("DELETE '/users/%s/friends/%s', exception: %s", userId, friendId, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable(name = "id") long userId, @PathVariable(name = "otherId") long otherUserId) {
        log.info(String.format("GET '/users/%s/friends/common/%s'", userId, otherUserId));

        try {
            return userService.getCommonFriends(userId, otherUserId);
        } catch (NotFoundException ex) {
            log.warn(String.format("GET '/users/{%s}/friends/common/{%s}', exception: %s", userId, otherUserId, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable(name = "id") long userId) {
        log.info(String.format("GET '/%s/friends/'", userId));

        try {
            return userService.getFriends(userId);
        } catch (NotFoundException ex) {
            log.warn(String.format("GET '/users/%s/friends', exception: %s", userId, ex.getMessage()));
            throw new NotFoundException(ex.getMessage());
        }
    }
}
