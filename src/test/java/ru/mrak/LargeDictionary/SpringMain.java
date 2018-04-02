package ru.mrak.LargeDictionary;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.service.textParsing.TextParsing;
import ru.mrak.LargeDictionary.service.textProvider.BookProvider;
import ru.mrak.LargeDictionary.service.translation.BookTranslation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SpringMain {
    public static void main(String[] args) throws IOException {
        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            BookProvider bookProvider = appCtx.getBean(BookProvider.class);
            TextParsing textParsing = appCtx.getBean(TextParsing.class);
            BookTranslation bookTranslation = appCtx.getBean(BookTranslation.class);

            Map<String, Object> parametrs = new HashMap<>();
            parametrs.put("source", "text");

            BufferedReader consol = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введи текст:");
            String text = consol.readLine();
            parametrs.put("text", text);

            Book book = bookProvider.getBook(parametrs);
            textParsing.preparedBook(book);
            bookTranslation.translation(book);

            System.out.println("Слова без перевода:");
            for (PreparedWord word: book.getPreparedWords()) {
                System.out.println(word);
            }
            System.out.println("Перевод слов:");
            /*Map<TranslationWord, Integer> frequencyWrod = book.getFrequencyWords();
            for (TranslationWord word: frequencyWrod.keySet()) {
                System.out.println(word + " : " + frequencyWrod.get(word));
            }*/
        }
    }
}
