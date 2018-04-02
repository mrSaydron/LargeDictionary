package ru.mrak.LargeDictionary.repository.book;

import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.PreparedWord;

import java.util.List;

public interface PrepareWordRepository {
    PreparedWord save(PreparedWord book);
    boolean delete(int id);
    PreparedWord get(int id);
    List<PreparedWord> getAll(int bookId);
}
