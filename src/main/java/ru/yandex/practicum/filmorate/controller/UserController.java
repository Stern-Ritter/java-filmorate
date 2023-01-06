package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.logger.Template.DELETE_USER_FRIEND_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.GET_USERS_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.GET_USER_BY_ID_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.GET_USER_COMMON_FRIENDS_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.GET_USER_FRIENDS_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.POST_USER_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.PUT_USER_FRIEND_TEMPLATE;
import static ru.yandex.practicum.filmorate.logger.Template.PUT_USER_TEMPLATE;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info(String.format(POST_USER_TEMPLATE, user));
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info(String.format(PUT_USER_TEMPLATE, user));
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        log.info(String.format(GET_USER_BY_ID_TEMPLATE, id));
        return userService.get(id);
    }

    @GetMapping
    public List<User> get() {
        log.info(GET_USERS_TEMPLATE);
        return userService.get();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable(name = "id") long userId, @PathVariable long friendId) {
        log.info(String.format(PUT_USER_FRIEND_TEMPLATE, userId, friendId));
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable(name = "id") long userId, @PathVariable long friendId) {
        log.info(String.format(DELETE_USER_FRIEND_TEMPLATE, userId, friendId));
        return userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable(name = "id") long userId, @PathVariable(name = "otherId") long otherUserId) {
        log.info(String.format(GET_USER_COMMON_FRIENDS_TEMPLATE, userId, otherUserId));
        return userService.getCommonFriends(userId, otherUserId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable(name = "id") long userId) {
        log.info(String.format(GET_USER_FRIENDS_TEMPLATE, userId));
        return userService.getFriends(userId);
    }
}
