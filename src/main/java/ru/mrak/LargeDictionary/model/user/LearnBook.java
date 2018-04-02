package ru.mrak.LargeDictionary.model.user;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.mrak.LargeDictionary.model.AbstractBaseEntity;
import ru.mrak.LargeDictionary.model.book.Book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "learn_books",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "book_id"}, name = "learn_books_unique_book_user_idx")})
public class LearnBook extends AbstractBaseEntity{

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Book book;

    @Column(name = "read")
    private Boolean read;

    public LearnBook() {
    }

    public LearnBook(User user, Book book, Boolean read) {
        super(null);
        this.user = user;
        this.book = book;
        this.read = read;
    }

    public LearnBook(Integer id, User user, Book book, Boolean read) {
        super(id);
        this.user = user;
        this.book = book;
        this.read = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
