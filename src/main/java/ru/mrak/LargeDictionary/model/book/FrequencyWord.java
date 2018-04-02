package ru.mrak.LargeDictionary.model.book;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.mrak.LargeDictionary.model.AbstractBaseEntity;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "frequency_words")
public class FrequencyWord extends AbstractBaseEntity{

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "translation_word_id", nullable = false)
    private TranslationWord translationWord;

    @Column(name = "frequency")
    private int frequency;

    public FrequencyWord() {
    }

    public FrequencyWord(Integer id, Book book, TranslationWord translationWord, int frequency) {
        super(id);
        this.book = book;
        this.translationWord = translationWord;
        this.frequency = frequency;
    }

    public FrequencyWord(Book book, TranslationWord translationWord, int frequency) {
        this(null, book, translationWord, frequency);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public TranslationWord getTranslationWord() {
        return translationWord;
    }

    public void setTranslationWord(TranslationWord translationWord) {
        this.translationWord = translationWord;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "FrequencyWord{" +
                "book=" + book +
                ", translationWord=" + translationWord +
                ", frequency=" + frequency +
                ", id=" + id +
                '}';
    }
}
