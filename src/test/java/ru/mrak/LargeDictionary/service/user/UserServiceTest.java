package ru.mrak.LargeDictionary.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mrak.LargeDictionary.model.user.LearnSet;
import ru.mrak.LargeDictionary.model.user.User;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void get() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = service.getAll();
        System.out.println("Опять все лейзи");
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void create() throws Exception {
    }

    @Test
    public void getLearnSet() throws Exception {
        User user = service.getByName("user");
        service.getLearnSet(user);
        System.out.println("Stop");
    }

    @Test
    public void getLearnBook() throws Exception {
        User user = service.getByName("user");
        service.getLearnBook(user);
        System.out.println("Stop");
    }

    @Test
    public void getLearnWord() throws Exception {
        User user = service.getByName("user");
        service.getLearnWord(user);
        System.out.println("Stop");
    }

    @Test
    public void getLearnWordById() throws Exception {
        User user = service.getByName("user");
        service.getLearnWord(user);
        System.out.println("Stop");
    }

}