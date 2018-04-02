package ru.mrak.LargeDictionary.model.book;

import ru.mrak.LargeDictionary.model.AbstractBaseEntity;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static javax.persistence.FetchType.LAZY;

/**
 * Книга (текст) для изучения и составления словаря
 * title - наименование текста
 * author - автор текста
 * url - ссылка на источник
 * text - оригинал тектса
 * textParsing - отредактированный текст
 * preparedWords - список не переведенных слов
 * frequencyWords - список слов с переводом и количеством вхождения в текст
 * amountWord - суммарное количество слов в тексте
 */

//TODO - надо добавить поле с пользователем который добавил книгу
//TODO - надо добавить список пользователей которые добавили книгу к себе

@Entity
@Table(name = "books",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"}, name = "book_unique_title_idx")})
public class Book extends AbstractBaseEntity {

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "url")
    private String url;

    @Column(name = "text")
    @NotNull
    private String text;

    @OneToMany(fetch = LAZY, mappedBy = "book")
    private List<PreparedWord> preparedWords;

    @OneToMany(fetch = LAZY, mappedBy = "book")
    private List<FrequencyWord> frequencyWords;

    @Column(name = "amount_word")
    @NotNull
    private int amountWord;

    @Column(name = "description")
    private String description;

    @Column(name = "picture")
    private String pictureName;

    public Book() {
    }

    public Book(String title,
                String author,
                String url,
                String text,
                List<PreparedWord> preparedWords,
                List<FrequencyWord> frequencyWords,
                int amountWord,
                String description,
                String pictureName) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.text = text;
        this.preparedWords = preparedWords;
        this.frequencyWords = frequencyWords;
        this.amountWord = amountWord;
        this.description = description;
        this.pictureName = pictureName;
    }

    public Book(Integer id,
                String title,
                String author,
                String url,
                String text,
                List<PreparedWord> preparedWords,
                List<FrequencyWord> frequencyWords,
                int amountWord,
                String description,
                String pictureName) {
        super(id);
        this.title = title;
        this.author = author;
        this.url = url;
        this.text = text;
        this.preparedWords = preparedWords;
        this.frequencyWords = frequencyWords;
        this.amountWord = amountWord;
        this.description = description;
        this.pictureName = pictureName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<PreparedWord> getPreparedWords() {
        return preparedWords;
    }

    public void setPreparedWords(List<PreparedWord> preparedWords) {
        this.preparedWords = preparedWords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAmountWord() {
        return amountWord;
    }

    public void setAmountWord(int amountWord) {
        this.amountWord = amountWord;
    }

    public List<FrequencyWord> getFrequencyWords() {
        return frequencyWords;
    }

    public void setFrequencyWords(List<FrequencyWord> frequencyWords) {
        this.frequencyWords = frequencyWords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", text='" + text + '\'' +
                ", preparedWords=" + preparedWords +
                ", frequencyWords=" + frequencyWords +
                ", amountWord=" + amountWord +
                ", id=" + id +
                '}';
    }
}
