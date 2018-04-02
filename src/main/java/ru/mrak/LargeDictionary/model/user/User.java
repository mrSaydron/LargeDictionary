package ru.mrak.LargeDictionary.model.user;

import ru.mrak.LargeDictionary.model.AbstractBaseEntity;
import ru.mrak.LargeDictionary.model.book.Book;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Пользователь
 * name - имя пользователя
 * learnWords - изучаемые слова
 * learnSets - наборы слов
 * books - изучаемые книги
 */

//TODO - добавить пользователю много очень нужных полей

@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx"),
        @UniqueConstraint(columnNames = "name", name = "users_unique_name_idx")})
public class User extends AbstractBaseEntity {

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "email")
    @Email
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "registered", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate registered;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<LearnWord> learnWords;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<LearnSet> learnSets;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<LearnBook> learnBooks;

    public User() {
    }

    public User(@NotNull String name, @Email @NotNull String email, @NotNull String password, @NotNull LocalDate registered, Set<Role> roles, List<LearnWord> learnWords, List<LearnSet> learnSets, List<LearnBook> learnBooks) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.roles = roles;
        this.learnWords = learnWords;
        this.learnSets = learnSets;
        this.learnBooks = learnBooks;
    }

    public User(Integer id, @NotNull String name, @Email @NotNull String email, @NotNull String password, @NotNull LocalDate registered, Set<Role> roles, List<LearnWord> learnWords, List<LearnSet> learnSets, List<LearnBook> learnBooks) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.roles = roles;
        this.learnWords = learnWords;
        this.learnSets = learnSets;
        this.learnBooks = learnBooks;
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

    public void setLearnWords(LearnWord... learnWords) {
        this.learnWords = new LinkedList<>(Arrays.asList(learnWords));
    }

    public List<LearnSet> getLearnSets() {
        return learnSets;
    }

    public void setLearnSets(List<LearnSet> learnSets) {
        this.learnSets = learnSets;
    }

    public void setLearnSets(LearnSet... learnSets) {
        this.learnSets = new LinkedList<>(Arrays.asList(learnSets));
    }

    public List<LearnBook> getLearnBooks() {
        return learnBooks;
    }

    public void setLearnBooks(List<LearnBook> learnBooks) {
        this.learnBooks = learnBooks;
    }

    public void setLearnBooks(LearnBook... learnBooks) {
        this.learnBooks = new LinkedList<>(Arrays.asList(learnBooks));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRoles(Role... roles) {
        this.roles = new HashSet<>(Arrays.asList(roles));
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registered=" + registered +
                ", roles=" + roles +
                ", learnWords=" + learnWords +
                ", learnSets=" + learnSets +
                ", learnBooks=" + learnBooks +
                ", id=" + id +
                '}';
    }
}
