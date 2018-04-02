package ru.mrak.LargeDictionary.repository.user.impl;

import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.repository.user.UserRepository;
import ru.mrak.LargeDictionary.repository.user.crud.CrudUserRepository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final CrudUserRepository repository;

    public UserRepositoryImpl(CrudUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return repository.deleteReturnIntById(id) != 0;
    }

    @Override
    public User get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}
