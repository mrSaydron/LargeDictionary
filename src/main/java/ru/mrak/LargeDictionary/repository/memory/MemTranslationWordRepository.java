package ru.mrak.LargeDictionary.repository.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.repository.word.TranslationWordRepository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemTranslationWordRepository {/*implements TranslationWordRepository, Serializable {
    private Map<String, TranslationWord> translations = new ConcurrentHashMap<>();
    private AtomicInteger index;
    private static final String FILE_PATH = "translation.dat";
    private static final Logger LOG = LoggerFactory.getLogger(MemTranslationWordRepository.class);

    public MemTranslationWordRepository() {
        LOG.debug("Read translation word data");
        try(FileInputStream fileStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream)){
            MemTranslationWordRepository memRepository = (MemTranslationWordRepository) objectStream.readObject();
            translations = memRepository.getTranslations();
            index = memRepository.getIndex();
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Do not read translation word data");
            index = new AtomicInteger(0);
        }
    }

    public void saveRepository() {
        LOG.debug("Save translation word data");
        try(FileOutputStream fileStream = new FileOutputStream(FILE_PATH);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(this);
        } catch (IOException e) {
            LOG.error("Do not save translation word data");
        }
    }

    private Map<String, TranslationWord> getTranslations() {
        return translations;
    }

    private AtomicInteger getIndex() {
        return index;
    }

    @Override
    public TranslationWord save(TranslationWord translationWord) {
        LOG.debug("Save in memory repository");
        Objects.requireNonNull(translationWord);
        if(translationWord.isNew()){
            LOG.debug("Save new word: " + translationWord.getWord());
            if(translations.containsKey(translationWord.getWord())) return null;
            int newIndex = index.incrementAndGet();
            translationWord.setId(newIndex);
            translations.put(translationWord.getWord(), translationWord);
        } else {
            if(!translations.containsKey(translationWord.getWord())) return null;
            LOG.debug("Update word: " + translationWord.getWord());
            translations.put(translationWord.getWord(), translationWord);
        }
        return translationWord;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }


    public Integer save(List<TranslationWord> translationWords) {
        LOG.debug("Save list in memory repository");
        Objects.requireNonNull(translationWords);
        Integer result = 0;
        if(translationWords.isEmpty()) return null;
        for (TranslationWord translationWord : translationWords) {
            if(this.save(translationWord) != null) result += 1;
        }
        LOG.debug("Save " + result + " words");
        return result;
    }

    @Override
    public boolean delete(String word) {
        LOG.debug("Delete in memory repository");
        Objects.requireNonNull(word);
        if(!translations.containsKey(word)) return false;
        translations.remove(word);
        LOG.debug("Delete word: " + word);
        return true;
    }

    @Override
    public TranslationWord get(int id) {
        return null;
    }

    @Override
    public TranslationWord get(String word) {
        LOG.debug("Get of memory repository word: " + word);
        Objects.requireNonNull(word);
        return translations.get(word);
    }


    public TranslationWord get(PreparedWord word) {
        LOG.debug("Get of memory repository word: " + word.getWord());
        Objects.requireNonNull(word);
        return translations.get(word.getWord());
    }


    public List<TranslationWord> get(List<PreparedWord> words) {
        LOG.debug("Get of memory repository words");
        Objects.requireNonNull(words);
        List<TranslationWord> result = new LinkedList<>();
        Iterator<PreparedWord> iterator = words.iterator();
        while(iterator.hasNext()) {
            PreparedWord word = iterator.next();
            TranslationWord translationWord = get(word);
            if(translationWord != null) {
                result.add(translationWord);
                iterator.remove();
            }
        }
        return result;
    }

    @Override
    public List<TranslationWord> getAll() {
        LOG.debug("Get all of memory repository");
        return new LinkedList<>(translations.values());
    }

    @Override
    public List<TranslationWord> getInQuantity(int page, int size) {
        return null;
    }*/
}
