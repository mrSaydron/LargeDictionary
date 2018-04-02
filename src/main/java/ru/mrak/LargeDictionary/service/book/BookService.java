package ru.mrak.LargeDictionary.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.DispatcherServlet;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.repository.book.BookRepository;
import ru.mrak.LargeDictionary.repository.book.FrequencyWordRepository;
import ru.mrak.LargeDictionary.repository.book.PrepareWordRepository;
import ru.mrak.LargeDictionary.util.ValidatorsUtil;

import java.util.List;

import static ru.mrak.LargeDictionary.util.ValidatorsUtil.checkNotFoundWithId;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final FrequencyWordRepository frequencyRepository;
    private final PrepareWordRepository prepareRepository;

    @Autowired
    public BookService(BookRepository bookRepository, FrequencyWordRepository frequencyRepository, PrepareWordRepository prepareRepository) {
        this.bookRepository = bookRepository;
        this.frequencyRepository = frequencyRepository;
        this.prepareRepository = prepareRepository;
    }

    public Book get(int id) {
        return ValidatorsUtil.checkNotFoundWithId(bookRepository.get(id), id);
    }

    public void delete(int id) {
        ValidatorsUtil.checkNotFound(bookRepository.delete(id), "Не смог удалить " + id);
    }

    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    public Book create(Book book) {
        Assert.notNull(book, "Книга не должна быть пустой");
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return checkNotFoundWithId(bookRepository.save(book), book.getId());
    }
}
