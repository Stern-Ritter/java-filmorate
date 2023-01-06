package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.Exceptions;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {
    private static long currentIdx = 1;

    private static long getNextIdx() {
        return currentIdx++;
    }

    private final Map<Long, User> users;

    public InMemoryUserStorage() {
        users = new HashMap<>();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> get() {
        return List.copyOf(users.values());
    }

    @Override
    public User create(User user) {
        long id = getNextIdx();
        user.setId(id);
        users.put(id, user);

        return user;
    }

    @Override
    public User update(User user) {
        Long id = user.getId();
        if (id == null || !users.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, id));
        }
        users.put(id, user);

        return user;
    }

    @Override
    public User delete(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, id));
        }
        User user = users.get(id);
        users.remove(id);

        return user;
    }
}
