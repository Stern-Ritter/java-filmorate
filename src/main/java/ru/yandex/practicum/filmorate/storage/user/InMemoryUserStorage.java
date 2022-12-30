package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.Exceptions;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
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
    public User get(long id) throws NotFoundException {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, id)));
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User add(User user) throws UserAlreadyExistException {
        Long id = user.getId();
        if (id != null && users.containsKey(id)) {
            throw new UserAlreadyExistException(String.format(Exceptions.USER_ALREADY_EXISTS_TEMPLATE, id));
        }

        id = getNextIdx();
        user.setId(id);

        String name = user.getName();
        if (name == null || name.isBlank()) {
            String login = user.getLogin();
            user.setName(login);
        }

        users.put(id, user);

        return user;
    }

    @Override
    public User update(User user) throws NotFoundException {
        Long id = user.getId();
        if (id == null) {
            id = getNextIdx();
            user.setId(id);
        } else if (!users.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, id));
        }

        users.put(id, user);

        return user;
    }

    @Override
    public User delete(long id) throws NotFoundException {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format(Exceptions.USER_NOT_EXISTS_TEMPLATE, id));
        }

        User user = users.get(id);
        users.remove(id);

        return user;
    }
}
