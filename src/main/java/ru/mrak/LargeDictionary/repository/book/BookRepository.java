package ru.mrak.LargeDictionary.repository.book;

import ru.mrak.LargeDictionary.model.book.Book;

import java.util.List;

public interface BookRepository {
    Book save(Book book);
    boolean delete(int id);
    Book get(int id);
    List<Book> getAll();
}
