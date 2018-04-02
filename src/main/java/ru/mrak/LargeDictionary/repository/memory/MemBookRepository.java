package ru.mrak.LargeDictionary.repository.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.repository.book.BookRepository;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemBookRepository implements BookRepository, Serializable {
    private Map<Integer, Book> books = new ConcurrentHashMap<>();
    private AtomicInteger index;
    private static final String FILE_PATH = "book.dat";
    private static final Logger LOG = LoggerFactory.getLogger(MemTranslationWordRepository.class);

    public MemBookRepository() {
        LOG.debug("Читаю базу книг");
        try(FileInputStream fileStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream)){
            MemBookRepository memBooks = (MemBookRepository) objectStream.readObject();
            books = memBooks.getBooks();
            index = memBooks.getIndex();
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Не могу прочитать базу книг");
            index = new AtomicInteger(0);
        }
    }

    public void saveRepository() {
        LOG.debug("Сохраняю базу книг");
        try(FileOutputStream fileStream = new FileOutputStream(FILE_PATH);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(this);
        } catch (IOException e) {
            LOG.error("Не удалось сохранить базу книг");
        }
    }

    private Map<Integer, Book> getBooks() {
        return books;
    }

    private AtomicInteger getIndex() {
        return index;
    }

    @Override
    public Book save(Book book) {
        LOG.debug("Сохраняю книгу");
        Objects.requireNonNull(book);
        if(book.isNew()){
            int newIndex = index.incrementAndGet();
            LOG.debug("Сохраняю новую книгу: " + newIndex);
            book.setId(newIndex);
            books.put(book.getId(), book);
        } else {
            if(!books.containsKey(book.getId())) return null;
            LOG.debug("Обнавляю книгу: " + book.getId());
            books.put(book.getId(), book);
        }
        return book;
    }

    @Override
    public boolean delete(int id) {
        LOG.debug("Удаляю книгу номер: " + id);
        if(!books.containsKey(id)) {
            LOG.debug("Нет книги с номером: " + id);
            return false;
        }
        books.remove(id);
        return true;
    }

    @Override
    public Book get(int id) {
        LOG.debug("запрос книги номер: " + id);
        Book book = books.get(id);
        if(book == null) {
            LOG.debug("Нет книги с номером: " + id);
            return null;
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        LOG.debug("Запрос всех книг");
        return new LinkedList<>(books.values());
    }
}
