package ru.mrak.LargeDictionary.service.book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mrak.LargeDictionary.model.book.Book;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BookServiceTest {

    @Autowired
    private BookService service;

    @Test
    public void get() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
        List<Book> books = service.getAll();
        System.out.println("Выдаст эксепшен так как поля в режие лайзи");
    }

    @Test
    public void create() throws Exception {
    }

    @Test
    public void update() throws Exception {

    }

}