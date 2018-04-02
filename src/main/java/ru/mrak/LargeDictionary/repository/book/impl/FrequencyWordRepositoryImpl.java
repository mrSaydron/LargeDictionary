package ru.mrak.LargeDictionary.repository.book.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.FrequencyWord;
import ru.mrak.LargeDictionary.repository.book.FrequencyWordRepository;
import ru.mrak.LargeDictionary.repository.book.crud.CrudBookRepository;
import ru.mrak.LargeDictionary.repository.book.crud.CrudFrequencyRepository;

import java.util.List;

@Repository
public class FrequencyWordRepositoryImpl implements FrequencyWordRepository {

    @Autowired
    private CrudFrequencyRepository repository;

    @Autowired
    CrudBookRepository bookRepository;

    @Override
    public FrequencyWord save(FrequencyWord book) {
        return repository.save(book);
    }

    @Override
    public boolean delete(int id) {
        return repository.deleteReturnedIntById(id) != 0;
    }

    @Override
    public FrequencyWord get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<FrequencyWord> getAll(int bookId) {
        Book book = bookRepository.getOne(bookId);
        return repository.findByBook(book);
    }
}
