package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.Exceptions;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage storage;

    public User create(User user) {
        updateEmptyUserName(user);
        return storage.create(user);
    }

    public User update(User user) {
        updateEmptyUserName(user);
        return storage.update(user);
    }

    public User get(long id) {
        return storage.get(id)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, id)));
    }

    public List<User> get() {
        return storage.get();
    }

    public User addFriend(long userId, long otherUserId) {
        User user = storage.get(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, userId)));
        User otherUser = storage.get(otherUserId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, otherUserId)));

        Set<Long> userFriends = user.getFriends();
        Set<Long> otherUserFriends = otherUser.getFriends();
        userFriends.add(otherUserId);
        otherUserFriends.add(userId);

        return user;
    }

    public User deleteFriend(long userId, long otherUserId) {
        User user = storage.get(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, userId)));
        User otherUser = storage.get(otherUserId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, otherUserId)));

        Set<Long> userFriends = user.getFriends();
        Set<Long> otherUserFriends = otherUser.getFriends();
        userFriends.remove(otherUserId);
        otherUserFriends.remove(userId);

        return user;
    }

    public List<User> getCommonFriends(long userId, long otherUserId) {
        User user = storage.get(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, userId)));
        User otherUser = storage.get(otherUserId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, otherUserId)));
        Set<Long> userFriends = user.getFriends();
        Set<Long> otherUserFriends = otherUser.getFriends();

        return userFriends.stream()
                .filter(otherUserFriends::contains)
                .map(storage::get)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(long userId) {
        User user = storage.get(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, userId)));
        Set<Long> friends = user.getFriends();

        return friends.stream()
                .map(storage::get)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private void updateEmptyUserName(User user) {
        String name = user.getName();
        if (name == null || name.isBlank()) {
            String login = user.getLogin();
            user.setName(login);
        }
    }
}
