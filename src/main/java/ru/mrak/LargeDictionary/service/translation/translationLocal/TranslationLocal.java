package ru.mrak.LargeDictionary.service.translation.translationLocal;

import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.util.List;

/**
 * Интерфейс для поиска слов в памяти
 * поиск слова
 * список всех слов
 * добавление слова
 * удалить слова
 */

public interface TranslationLocal {
    //null если такого перевода нет
    TranslationWord getTranslation(String word);
    TranslationWord getTranslation(PreparedWord word);

    //Возвращает только имеющиеся переводы, удаляет из words имеющиеся перевода
    List<TranslationWord> getTranslation(List<PreparedWord> words);

    //false если не удалось сохранить
    boolean addTranslation(TranslationWord word);

    //возвращает колличество сохраненных слов
    Integer addTranslation(List<TranslationWord> words);

    //false если не удалось сохранить
    boolean deleteTranslation(TranslationWord word);

    //false если не удалось
    boolean updateTranslation(TranslationWord word);
}