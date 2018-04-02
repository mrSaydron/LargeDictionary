package ru.mrak.LargeDictionary.repository.book.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.repository.book.BookRepository;
import ru.mrak.LargeDictionary.repository.book.crud.CrudBookRepository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private CrudBookRepository repository;

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public boolean delete(int id) {
        return repository.deleteReturnIntById(id) != 0;
    }

    @Override
    public Book get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }
}
