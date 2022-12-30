package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public User addUser(User user) {
        return storage.add(user);
    }

    public User updateUser(User user) {
        return storage.update(user);
    }

    public User getUser(long id) {
        return storage.get(id);
    }

    public Collection<User> getAllUsers() {
        return storage.getAll();
    }

    public User addFriend(long userId, long otherUserId) {
        User user = storage.get(userId);
        User otherUser = storage.get(otherUserId);

        Set<Long> userFriends = Optional.ofNullable(user.getFriends()).orElse(new HashSet<>());
        Set<Long> otherUserFriends = Optional.ofNullable(otherUser.getFriends()).orElse(new HashSet<>());
        userFriends.add(otherUserId);
        otherUserFriends.add(userId);
        user.setFriends(userFriends);
        otherUser.setFriends(otherUserFriends);

        storage.update(user);
        storage.update(otherUser);

        return user;
    }

    public User deleteFriend(long userId, long otherUserId) {
        User user = storage.get(userId);
        User otherUser = storage.get(otherUserId);

        Set<Long> userFriends = Optional.ofNullable(user.getFriends()).orElse(new HashSet<>());
        Set<Long> otherUserFriends = Optional.ofNullable(otherUser.getFriends()).orElse(new HashSet<>());
        userFriends.remove(otherUserId);
        otherUserFriends.remove(userId);
        user.setFriends(userFriends);
        otherUser.setFriends(otherUserFriends);

        storage.update(user);
        storage.update(otherUser);

        return user;
    }

    public Collection<User> getCommonFriends(long userId, long otherUserId) {
        User user = storage.get(userId);
        User otherUser = storage.get(otherUserId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> otherUserFriends = otherUser.getFriends();

        if (userFriends == null || otherUserFriends == null) return Collections.emptyList();

        return userFriends.stream()
                .filter(otherUserFriends::contains)
                .map(storage::get)
                .collect(Collectors.toList());
    }

    public Collection<User> getFriends(long userId) {
        User user = storage.get(userId);
        Set<Long> friends = Optional.ofNullable(user.getFriends()).orElse(new HashSet<>());

        return friends.stream()
                .map(storage::get)
                .collect(Collectors.toList());
    }
}
