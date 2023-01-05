package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    Optional<User> get(long id);

    List<User> get();

    User create(User user);

    User update(User user);

    User delete(long id);
}
