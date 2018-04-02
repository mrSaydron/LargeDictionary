package ru.mrak.LargeDictionary.service.word;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TranslationWordServiceTest {

    @Autowired
    private TranslationWordService service;

    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() {
        List<TranslationWord> words = service.getAll();
        System.out.println(words);
    }

    @Test
    public void update() {
    }

    @Test
    public void create() {
    }

    @Test
    public void addTranslation() {
    }
}