package ru.mrak.LargeDictionary.service.translation.translationWeb;


import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.util.List;

/**
 * Загрузка перевода слова из интернета
 * найти слова в интеренете
 * проверить доступ к интернет словарю
 */

public interface TranslationWeb {
    TranslationWord getTranslation(PreparedWord word);
    List<TranslationWord> getTranslation(List<PreparedWord> words);
    boolean isWork();
}
