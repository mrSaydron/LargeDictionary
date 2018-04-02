package ru.mrak.LargeDictionary.model.book;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.mrak.LargeDictionary.model.AbstractBaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

/**
 * Слово для перевода,
 * с указателем на текст в котором было найденно слови и его позиция
 * word - слово
 * book - указатель на книгу где было найдено слово
 * startWord - номер символа в книге с которого начинается слово
 * endWord - номер символа в тексте на котором заканчивается слово
 */

@Entity
@Table(name = "prepare_words")
public class PreparedWord extends AbstractBaseEntity {

    @Column(name = "word")
    @NotNull
    private String word;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Book book;

    @Column(name = "start_word")
    @NotNull
    private int startWord;

    @Column(name = "end_word")
    @NotNull
    private int endWord;

    public PreparedWord(String word, int startWord, int endWord) {
        this.word = word;
        this.startWord = startWord;
        this.endWord = endWord;
    }

    public PreparedWord(String word, Book book, int startWord, int endWord) {
        this.word = word;
        this.book = book;
        this.startWord = startWord;
        this.endWord = endWord;
    }

    public PreparedWord(Integer id, String word, Book book, int startWord, int endWord) {
        super(id);
        this.word = word;
        this.book = book;
        this.startWord = startWord;
        this.endWord = endWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getStartWord() {
        return startWord;
    }

    public void setStartWord(int startWord) {
        this.startWord = startWord;
    }

    public int getEndWord() {
        return endWord;
    }

    public void setEndWord(int endWord) {
        this.endWord = endWord;
    }

    @Override
    public String toString() {
        return "PreparedWord{" +
                "word='" + word + '\'' +
                '}';
    }
}
