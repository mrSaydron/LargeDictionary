package ru.mrak.LargeDictionary.repository.word;

import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.util.List;

public interface TranslationWordRepository {
    TranslationWord save(TranslationWord word);
    boolean delete(int id);
    boolean deleteCommonWord(String word);
    boolean deleteUserWord(String word, int userId);
    TranslationWord get(int id);
    TranslationWord getCommonWord(String word);
    TranslationWord getUserWord(String word, int userId);
    List<TranslationWord> getAll();
    List<TranslationWord> getAllCommon();
    List<TranslationWord> getAllByUser(int userId);
    List<TranslationWord> getAllByUserAndCommon(int userId);
    List<TranslationWord> getInQuantity(int page, int size);
}
