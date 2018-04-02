package ru.mrak.LargeDictionary.repository.user;

import ru.mrak.LargeDictionary.model.user.LearnBook;

import java.util.List;

public interface LearnBookRepository {
    LearnBook save(LearnBook book, int userId);
    boolean delete(int id, int userId);
    LearnBook get(int id, int userId);
    List<LearnBook> getAll(int userId);
}
