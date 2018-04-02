package ru.mrak.LargeDictionary.repository.user;

import ru.mrak.LargeDictionary.model.user.LearnWord;

import java.util.List;

public interface LearnWordRepository {
    LearnWord save(LearnWord word, int userId);
    boolean delete(int id, int userId);
    LearnWord get(int id, int userId);
    List<LearnWord> getAll(int userId);
    LearnWord getByTranslationWord(int translationWordId, int userId);
}
