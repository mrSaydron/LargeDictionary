package ru.mrak.LargeDictionary.service.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.service.translation.translationLocal.TranslationLocal;
import ru.mrak.LargeDictionary.service.translation.translationLocal.TranslationMemory;
import ru.mrak.LargeDictionary.service.translation.translationWeb.TranslationWeb;
import ru.mrak.LargeDictionary.service.translation.translationWeb.TranslationYandex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>Сервис для получения перевода</p>
 * <p>Позволяет получить список переведенных слов из списка предварительных слов. При переводе модифицирует список,
 * оставляя только не переведенные слова.</p>
 * Позволяет добавить новый перевод
 * Позволяет удалить перевод
 * Позволяет согласовать переводы в разных базах
 * 
 */

@Service
public class BookTranslation {
    private TranslationLocal translationLocal;
    private TranslationWeb translationWeb;

    @Autowired
    public BookTranslation(TranslationLocal translationLocal, TranslationWeb translationWeb) {
        this.translationLocal = translationLocal;
        this.translationWeb = translationWeb;
    }

    private TranslationWord getOneTranslation(PreparedWord preparedWord) {
        TranslationWord translationWord = translationLocal.getTranslation(preparedWord);
        if(translationWord == null) {
            translationWord = translationWeb.getTranslation(preparedWord);
            if(translationWord == null) return null;
            translationLocal.addTranslation(translationWord);
        }
        return translationWord;
    }

    private List<TranslationWord> getTranslations(List<PreparedWord> preparedWords) {
        List<TranslationWord> translationWords = translationLocal.getTranslation(preparedWords);
        if(!preparedWords.isEmpty()){
            List<TranslationWord> webTranslationWords = translationWeb.getTranslation(preparedWords);
            translationLocal.addTranslation(webTranslationWords);
            translationWords.addAll(webTranslationWords);
        }
        return translationWords;
    }

    public void translation(Book book) {
        List<TranslationWord> translationWords = getTranslations(book.getPreparedWords());
        HashMap<TranslationWord, Integer> frequencyWords = new HashMap<>();
        translationWords.forEach(word -> addWord(frequencyWords, word));
        //book.setFrequencyWords(frequencyWords);
        book.setAmountWord(frequencyWords.size());
    }

    private void addWord(Map<TranslationWord, Integer> frequencyWords, TranslationWord word) {
        if(frequencyWords.containsKey(word)) {
            int frequency = frequencyWords.get(word);
            frequencyWords.put(word, frequency + 1);
        } else {
            frequencyWords.put(word, 1);
        }
    }

    public void setTranslationLocal(TranslationLocal translationLocal) {
        this.translationLocal = translationLocal;
    }

    public void setTranslationWeb(TranslationWeb translationWeb) {
        this.translationWeb = translationWeb;
    }
}
