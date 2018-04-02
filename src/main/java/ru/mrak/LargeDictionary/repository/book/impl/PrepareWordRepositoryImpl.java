package ru.mrak.LargeDictionary.repository.book.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.repository.book.PrepareWordRepository;
import ru.mrak.LargeDictionary.repository.book.crud.CrudBookRepository;
import ru.mrak.LargeDictionary.repository.book.crud.CrudPrepareRepository;

import java.util.List;

@Repository
public class PrepareWordRepositoryImpl implements PrepareWordRepository {

    @Autowired
    private CrudPrepareRepository repository;

    @Autowired
    private CrudBookRepository bookRepository;

    @Override
    public PreparedWord save(PreparedWord book) {
        return repository.save(book);
    }

    @Override
    public boolean delete(int id) {
        return repository.deleteReturnedIntById(id) != 0;
    }

    @Override
    public PreparedWord get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<PreparedWord> getAll(int bookId) {
        Book book = bookRepository.getOne(bookId);
        return repository.findByBook(book);
    }
}
