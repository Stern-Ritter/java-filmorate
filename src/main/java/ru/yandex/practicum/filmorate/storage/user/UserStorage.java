package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User get(long id) throws NotFoundException;

    Collection<User> getAll();

    User add(User user) throws UserAlreadyExistException;

    User update(User user) throws NotFoundException;

    User delete(long id) throws NotFoundException;
}
