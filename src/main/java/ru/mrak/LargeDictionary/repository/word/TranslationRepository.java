package ru.mrak.LargeDictionary.repository.word;

import ru.mrak.LargeDictionary.model.word.Translation;

import java.util.List;

public interface TranslationRepository {
    Translation save(Translation translation);
    boolean delete(int id);
    Translation get(int id);
}
