package ru.mrak.LargeDictionary.model.word;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.mrak.LargeDictionary.model.AbstractBaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * Вариант перевода слова (значение)
 * translation - перевод
 * partOfSpeech - часть речи
 */

@Entity
@Table(name = "translations")
public class Translation extends AbstractBaseEntity {

    @Column(name = "translation", nullable = false)
    @NotEmpty
    private String translation;

    @Column(name = "part_of_speech")
    private String partOfSpeech;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private TranslationWord translationWord;

    public Translation() {
    }

    public Translation(Integer id, String translation, String partOfSpeech) {
        super(id);
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
    }

    public Translation(Integer id, @NotEmpty String translation, String partOfSpeech, @NotNull TranslationWord translationWord) {
        super(id);
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.translationWord = translationWord;
    }

    public Translation(String translation, String partOfSpeech) {
        this(null, translation, partOfSpeech);
    }

    public Translation(@NotEmpty String translation, String partOfSpeech, @NotNull TranslationWord translationWord) {
        this(null, translation, partOfSpeech, translationWord);
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public TranslationWord getTranslationWord() {
        return translationWord;
    }

    public void setTranslationWord(TranslationWord translationWord) {
        this.translationWord = translationWord;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "translation='" + translation + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                '}';
    }
}
