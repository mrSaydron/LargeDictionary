package ru.mrak.LargeDictionary.repository.user.impl;

import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.user.LearnBook;
import ru.mrak.LargeDictionary.repository.user.LearnBookRepository;
import ru.mrak.LargeDictionary.repository.user.crud.CrudLearnBookRepository;
import ru.mrak.LargeDictionary.repository.user.crud.CrudUserRepository;

import java.util.List;

@Repository
public class LearnBookRepositoryImpl implements LearnBookRepository {

    private CrudLearnBookRepository repository;
    private CrudUserRepository userRepository;

    public LearnBookRepositoryImpl(CrudLearnBookRepository repository, CrudUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public LearnBook save(LearnBook book, int userId) {
        if (!book.isNew() && (get(book.getId(), userId) == null)) {
            return null;
        }
        book.setUser(userRepository.getOne(userId));
        return repository.save(book);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.deleteReturnIntByIdAndUser(id, userRepository.getOne(userId)) != 0;
    }

    @Override
    public LearnBook get(int id, int userId) {
        return repository.findById(id).filter(word -> word.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<LearnBook> getAll(int userId) {
        return repository.findByUser(userRepository.getOne(userId));
    }
}
