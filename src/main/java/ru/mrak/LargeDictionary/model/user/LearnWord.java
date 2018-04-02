package ru.mrak.LargeDictionary.model.user;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.mrak.LargeDictionary.model.AbstractBaseEntity;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * Слово для обучения:
 * translationWord - слово с переводом
 * frequency - чатота слова для словаря в которое это слово входит
 * createDate - дата добавления слова в словарь
 * status - статус слова (новое/изучается/изучено)
 * user - пользователь изучающий слово
 */

@Entity
@Table(name = "learn_words",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "translation_word_id"}, name = "learn_words_unique_word_idx")})
public class LearnWord extends AbstractBaseEntity {

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "translation_word_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private TranslationWord translationWord;

    @Column(name = "frequency")
    private int frequency;

    @Column(name = "create_date", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate createDate;

    @Column(name = "status")
    private String status; //TODO - сделать энум для статусов

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @Column(name = "user_edited")
    @NotNull
    private boolean userEdited;

    public LearnWord(TranslationWord translationWord, int frequency, LocalDate createDate, String status, User user) {
        this.translationWord = translationWord;
        this.frequency = frequency;
        this.createDate = createDate;
        this.status = status;
        this.user = user;
    }

    public LearnWord(@NotNull TranslationWord translationWord,
                     int frequency,
                     @NotNull LocalDate createDate,
                     String status,
                     @NotNull User user,
                     @NotNull boolean userEdited) {
        this.translationWord = translationWord;
        this.frequency = frequency;
        this.createDate = createDate;
        this.status = status;
        this.user = user;
        this.userEdited = userEdited;
    }

    public LearnWord(Integer id, TranslationWord translationWord, int frequency, LocalDate createDate, String status, User user) {
        super(id);
        this.translationWord = translationWord;
        this.frequency = frequency;
        this.createDate = createDate;
        this.status = status;
        this.user = user;
    }

    public LearnWord() {
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isUserEdited() {
        return userEdited;
    }

    public void setUserEdited(boolean userEdited) {
        this.userEdited = userEdited;
    }

    @Override
    public String toString() {
        return "LearnWord{" +
                "translationWord=" + translationWord +
                ", frequency=" + frequency +
                ", createDate=" + createDate +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}
