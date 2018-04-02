package ru.mrak.LargeDictionary.data;

import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.FrequencyWord;
import ru.mrak.LargeDictionary.model.book.PreparedWord;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.mrak.LargeDictionary.data.TranslationWordData.*;

public class BookData {
    public static final Book BOOK_TEST_BOOK = new Book();

    static {
        List<PreparedWord> preparedWords = new LinkedList<>();
        preparedWords.add(new PreparedWord("xyz", 0, 2));

        List<FrequencyWord> frequencyWords = new LinkedList<>();
        frequencyWords.add(new FrequencyWord(BOOK_TEST_BOOK, TRANSLATION_DICTIONARY, 1));
        frequencyWords.add(new FrequencyWord(BOOK_TEST_BOOK, TRANSLATION_LARGE, 1));
        frequencyWords.add(new FrequencyWord(BOOK_TEST_BOOK, TRANSLATION_BOOK, 1));
        frequencyWords.add(new FrequencyWord(BOOK_TEST_BOOK, TRANSLATION_HUMAN, 1));

        BOOK_TEST_BOOK.setTitle("Test book");
        BOOK_TEST_BOOK.setAuthor(null);
        BOOK_TEST_BOOK.setUrl(null);
        BOOK_TEST_BOOK.setText("xyz dictionary large book human");
        BOOK_TEST_BOOK.setPreparedWords(preparedWords);
        BOOK_TEST_BOOK.setFrequencyWords(frequencyWords);
        BOOK_TEST_BOOK.setAmountWord(5);
    }

    public static void assertMath(Book actual, Book expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,
            "id",
            "author",
            "url",
            "text",
            "preparedWords",
            "frequencyWords");
    }

};