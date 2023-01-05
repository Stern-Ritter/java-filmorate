package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info(String.format("POST '/users', parameters={%s}", user));
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info(String.format("PUT '/users', parameters={%s}", user));
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        log.info(String.format("GET '/users/%s'", id));
        return userService.get(id);
    }

    @GetMapping
    public Collection<User> get() {
        log.info("GET '/users'");
        return userService.get();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable(name = "id") long userId, @PathVariable long friendId) {
        log.info(String.format("PUT '/users/%s/friends/%s'", userId, friendId));
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable(name = "id") long userId, @PathVariable long friendId) {
        log.info(String.format("DELETE '/users/%s/friends/%s'", userId, friendId));
        return userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable(name = "id") long userId, @PathVariable(name = "otherId") long otherUserId) {
        log.info(String.format("GET '/users/%s/friends/common/%s'", userId, otherUserId));
        return userService.getCommonFriends(userId, otherUserId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable(name = "id") long userId) {
        log.info(String.format("GET '/%s/friends/'", userId));
        return userService.getFriends(userId);
    }
}
