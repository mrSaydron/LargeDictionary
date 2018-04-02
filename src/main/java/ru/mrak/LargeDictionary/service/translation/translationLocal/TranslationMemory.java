package ru.mrak.LargeDictionary.service.translation.translationLocal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.repository.word.TranslationWordRepository;

import java.util.List;

@Service
public class TranslationMemory implements TranslationLocal{

    @Autowired
    private TranslationWordRepository repository;

    @Autowired
    public TranslationMemory(TranslationWordRepository repository) {
        this.repository = repository;
    }

    @Override
    public TranslationWord getTranslation(String word) {
        return repository.getCommonWord(word);
    }

    @Override
    public TranslationWord getTranslation(PreparedWord word) {
        //return repository.get(word);
        return null;
    }

    @Override
    public List<TranslationWord> getTranslation(List<PreparedWord> words) {
        //return repository.get(words);
        return null;
    }

    @Override
    public boolean addTranslation(TranslationWord word) {
        if(repository.save(word) != null) {
            //repository.saveRepository();
            return true;
        }
        return false;
    }

    @Override
    public Integer addTranslation(List<TranslationWord> words) {
        //Integer result = repository.save(words);
        //if(result != null && result > 0) repository.saveRepository();
        //return result;
        return null;
    }

    @Override
    public boolean deleteTranslation(TranslationWord word) {
        //return repository.delete(word.getWord());
        return false;
    }

    @Override
    public boolean updateTranslation(TranslationWord word) {
        if(repository.save(word) != null) {
            //repository.saveRepository();
            return true;
        }
        return false;
    }
}
