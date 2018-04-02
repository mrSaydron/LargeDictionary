package ru.mrak.LargeDictionary.repository.book;

import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.FrequencyWord;

import java.util.List;

public interface FrequencyWordRepository {
    FrequencyWord save(FrequencyWord book);
    boolean delete(int id);
    FrequencyWord get(int id);
    List<FrequencyWord> getAll(int bookId);
}
