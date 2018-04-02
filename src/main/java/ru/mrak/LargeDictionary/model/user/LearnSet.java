package ru.mrak.LargeDictionary.model.user;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.mrak.LargeDictionary.model.AbstractBaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * Набор слов для изучения
 * name - имя набора
 * learnWords - набор слов
 */

@Entity
@Table(name = "learn_sets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"}, name = "learn_sets_unique_name_idx")})
public class LearnSet extends AbstractBaseEntity{

    @Column(name = "name")
    @NotNull
    private String name;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "learn_sets_learn_words",
            joinColumns = @JoinColumn(name = "learn_set_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "learn_word_id", referencedColumnName = "id"))
    private List<LearnWord> learnWords;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    public LearnSet() {
    }

    public LearnSet(@NotNull String name, List<LearnWord> learnWords, @NotNull User user) {
        super(null);
        this.name = name;
        this.learnWords = learnWords;
        this.user = user;
    }

    public LearnSet(Integer id, @NotNull String name, List<LearnWord> learnWords, @NotNull User user) {
        super(id);
        this.name = name;
        this.learnWords = learnWords;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LearnWord> getLearnWords() {
        return learnWords;
    }

    public void setLearnWords(List<LearnWord> learnWords) {
        this.learnWords = learnWords;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
