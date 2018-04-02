package ru.mrak.LargeDictionary.repository.user;

import ru.mrak.LargeDictionary.model.user.LearnSet;

import java.util.List;

public interface LearnSetRepository {
    LearnSet save(LearnSet set, int userId);
    boolean delete(int id, int userId);
    LearnSet get(int id, int userId);
    List<LearnSet> getAll(int userId);
}
