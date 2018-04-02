package ru.mrak.LargeDictionary.repository.user.impl;

import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.user.LearnWord;
import ru.mrak.LargeDictionary.repository.user.LearnWordRepository;
import ru.mrak.LargeDictionary.repository.user.crud.CrudLearnWordRepository;
import ru.mrak.LargeDictionary.repository.user.crud.CrudUserRepository;
import ru.mrak.LargeDictionary.repository.word.crud.CrudTranslationWordRepository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class LearnWordRepositoryImpl implements LearnWordRepository {
    private final CrudLearnWordRepository repository;
    private final CrudUserRepository userRepository;
    private final CrudTranslationWordRepository translationWordRepository;

    public LearnWordRepositoryImpl(CrudLearnWordRepository repository, CrudUserRepository userRepository, CrudTranslationWordRepository translationWordRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.translationWordRepository = translationWordRepository;
    }

    @Override
    public LearnWord save(LearnWord word, int userId) {
        if (!word.isNew() && (get(word.getId(), userId) == null)) {
            return null;
        }
        word.setUser(userRepository.getOne(userId));
        return repository.save(word);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.deleteReturnIntByIdAndUser(id, userRepository.getOne(userId)) != 0;
    }

    @Override
    public LearnWord get(int id, int userId) {
        return repository.findById(id).filter(word -> word.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<LearnWord> getAll(int userId) {
        return repository.findByUser(userRepository.getOne(userId));
    }

    @Override
    public LearnWord getByTranslationWord(int translationWordId, int userId) {
        return repository.findByUserAndTranslationWord(
                userRepository.getOne(userId),
                translationWordRepository.getOne(translationWordId));
    }
}