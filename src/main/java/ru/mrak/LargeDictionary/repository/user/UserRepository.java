package ru.mrak.LargeDictionary.repository.user;

import ru.mrak.LargeDictionary.model.user.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    boolean delete(int id);
    User get(int id);
    User getByName(String name);
    List<User> getAll();
}
