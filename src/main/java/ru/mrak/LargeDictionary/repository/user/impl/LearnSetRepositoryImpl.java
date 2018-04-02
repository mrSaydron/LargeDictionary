package ru.mrak.LargeDictionary.repository.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.user.LearnSet;
import ru.mrak.LargeDictionary.repository.user.LearnSetRepository;
import ru.mrak.LargeDictionary.repository.user.crud.CrudLearnSetRepository;
import ru.mrak.LargeDictionary.repository.user.crud.CrudUserRepository;

import java.beans.Transient;
import java.util.List;

@Repository
public class LearnSetRepositoryImpl implements LearnSetRepository {

    private CrudLearnSetRepository repository;
    private CrudUserRepository userRepository;

    @Autowired
    public LearnSetRepositoryImpl(CrudLearnSetRepository repository, CrudUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public LearnSet save(LearnSet set, int userId) {
        if (!set.isNew() && (get(set.getId(), userId) == null)) {
            return null;
        }
        set.setUser(userRepository.getOne(userId));
        return repository.save(set);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.deleteReturnIntByIdAndUser(id, userRepository.getOne(userId)) != 0;
    }

    @Override
    public LearnSet get(int id, int userId) {
        return repository.findById(id).filter(word -> word.getUser().getId() == userId).orElse(null);
    }

    @Override
    @Transactional
    public List<LearnSet> getAll(int userId) {
        List<LearnSet> learnSets = repository.findByUser(userRepository.getOne(userId));
        learnSets.forEach(s -> s.getLearnWords().size());
        return learnSets;
    }
}
