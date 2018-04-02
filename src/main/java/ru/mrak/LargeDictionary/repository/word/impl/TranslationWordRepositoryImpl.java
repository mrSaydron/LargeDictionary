package ru.mrak.LargeDictionary.repository.word.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.repository.word.TranslationWordRepository;
import ru.mrak.LargeDictionary.repository.word.crud.CrudTranslationRepository;
import ru.mrak.LargeDictionary.repository.word.crud.CrudTranslationWordRepository;

import java.util.List;

@Repository
public class TranslationWordRepositoryImpl implements TranslationWordRepository {
    private static final Sort SORT_WORD = new Sort(Sort.Direction.ASC, "word");

    private CrudTranslationWordRepository wordRepository;
    private CrudTranslationRepository translationRepository;

    @Autowired
    public TranslationWordRepositoryImpl(CrudTranslationWordRepository wordRepository, CrudTranslationRepository translationRepository) {
        this.wordRepository = wordRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    public TranslationWord save(TranslationWord word) {
        wordRepository.save(word);
        word.getTranslations().forEach(t -> translationRepository.save(t));
        return wordRepository.save(word);
    }

    @Override
    public boolean delete(int id) {
        return wordRepository.deleteReturnedIntById(id) != 0;
    }

    @Override
    public boolean deleteCommonWord(String word) {
        return wordRepository.deleteByWordAndUserId(word, TranslationWord.COMMON_WORD) != 0;
    }

    @Override
    public boolean deleteUserWord(String word, int userId) {
        return wordRepository.deleteByWordAndUserId(word, userId) != 0;
    }

    @Override
    public TranslationWord get(int id) {
        return wordRepository.findById(id).orElse(null);
    }

    @Override
    public List<TranslationWord> getAll() {
        return wordRepository.findAll(SORT_WORD);
    }

    @Override
    public List<TranslationWord> getAllCommon() {
        return wordRepository.findAllByUserId(TranslationWord.COMMON_WORD);
    }

    @Override
    public List<TranslationWord> getAllByUser(int userId) {
        return wordRepository.findAllByUserId(userId);
    }

    @Override
    public List<TranslationWord> getAllByUserAndCommon(int userId) {
        return wordRepository.findAllByUserIdOrUserId(userId, TranslationWord.COMMON_WORD);
    }

    @Override
    public List<TranslationWord> getInQuantity(int page, int size) {
        return wordRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public TranslationWord getCommonWord(String word) {
        return wordRepository.findByWordAndUserId(word, TranslationWord.COMMON_WORD);
    }

    @Override
    public TranslationWord getUserWord(String word, int userId) {
        return wordRepository.findByWordAndUserId(word, userId);
    }
}
