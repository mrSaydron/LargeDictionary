package ru.mrak.LargeDictionary.repository.word.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.repository.word.TranslationRepository;
import ru.mrak.LargeDictionary.repository.word.crud.CrudTranslationRepository;

@Repository
public class TranslationRepositoryImpl implements TranslationRepository {

    @Autowired
    CrudTranslationRepository repository;

    @Override
    public Translation save(Translation translation) {
        return repository.save(translation);
    }

    @Override
    public boolean delete(int id) {
        return repository.deleteReturnedIntById(id) != 0;
    }

    @Override
    public Translation get(int id) {
        return repository.findById(id).orElse(null);
    }
}
