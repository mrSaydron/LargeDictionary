package ru.mrak.LargeDictionary.repository.word;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mrak.LargeDictionary.data.TranslationWordData;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;
import static ru.mrak.LargeDictionary.data.TranslationWordData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TranslationWordRepositoryTest {

    @Autowired
    private TranslationWordRepository translationWordRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save() throws Exception {
        translationWordRepository.deleteCommonWord("dictionary");
        TRANSLATION_DICTIONARY.setId(null);
        TRANSLATION_DICTIONARY.getTranslations().get(0).setId(null);
        translationWordRepository.save(TRANSLATION_DICTIONARY);
        TranslationWord word = translationWordRepository.getCommonWord("dictionary");
        assertMath(word, TRANSLATION_DICTIONARY);
    }

    @Test
    public void saveUserWord() throws Exception {
        User user = userRepository.getByName("user");
        if(user == null) throw new Exception();
        translationWordRepository.deleteUserWord("user", user.getId());
        TRANSLATION_USER.setUserId(user.getId());
        translationWordRepository.save(TRANSLATION_USER);
        TranslationWord word = translationWordRepository.getUserWord("user", user.getId());
        assertMath(word, TRANSLATION_USER);
    }

    @Test
    public void updateAddTranslation() throws Exception {
        TranslationWord word = translationWordRepository.getCommonWord("dictionary");
        word.addTranslation(new Translation("test", "test"));
        word = translationWordRepository.save(word);
        if(word == null) throw new Exception();
        translationWordRepository.delete(word.getId());
        TRANSLATION_DICTIONARY.setId(null);
        TRANSLATION_DICTIONARY.getTranslations().get(0).setId(null);
        translationWordRepository.save(TRANSLATION_DICTIONARY);
        TRANSLATION_DICTIONARY.setId(null);
    }

    @Test
    public void updateDeleteTranslation() throws Exception {
        TranslationWord word = translationWordRepository.getCommonWord("large");
        word.deleteTranslition("крупный");
        word = translationWordRepository.save(word);
        if(word == null) throw new Exception();
        translationWordRepository.delete(word.getId());
        translationWordRepository.save(TRANSLATION_LARGE);
        TRANSLATION_LARGE.setId(null);
    }

    @Test
    public void deleteById() throws Exception {
        TranslationWord translationWord = translationWordRepository.getCommonWord("dictionary");
        if(!translationWordRepository.delete(translationWord.getId())) throw new Exception();
        translationWord = translationWordRepository.getCommonWord("dictionary");
        if(translationWord != null) throw new Exception();
        TRANSLATION_DICTIONARY.setId(null);
        TRANSLATION_DICTIONARY.getTranslations().get(0).setId(null);
        translationWordRepository.save(TRANSLATION_DICTIONARY);
        TRANSLATION_DICTIONARY.setId(null);
    }

    @Test
    public void deleteCommonWord() throws Exception {
        if(!translationWordRepository.deleteCommonWord("dictionary")) throw new Exception();
        TranslationWord word = translationWordRepository.getCommonWord("dictionary");
        if(word != null) throw new Exception();
        translationWordRepository.save(TRANSLATION_DICTIONARY);
        TRANSLATION_DICTIONARY.setId(null);
    }

    @Test
    public void deleteUserWord() throws Exception {
        User user = userRepository.getByName("user");
        if(user == null) throw new Exception();
        if(!translationWordRepository.deleteUserWord("user", user.getId())) throw new Exception();
        TRANSLATION_USER.setUserId(user.getId());
        TRANSLATION_USER.setId(null);
        TRANSLATION_USER.getTranslations().get(0).setId(null);
        translationWordRepository.save(TRANSLATION_USER);
    }

    @Test
    public void get() throws Exception {
        TranslationWord word = translationWordRepository.getCommonWord("dictionary");
        if(word == null) throw new Exception();
        TranslationWordData.assertMath(translationWordRepository.get(word.getId()), TRANSLATION_DICTIONARY);
    }

    @Test
    public void getCommonWord() throws Exception {
        TranslationWord actual = translationWordRepository.getCommonWord("dictionary");
        TranslationWordData.assertMath(actual, TRANSLATION_DICTIONARY);
    }

    @Test
    public void getCommonWordNull() throws Exception {
        TranslationWord actual = translationWordRepository.getCommonWord("user");
        assertNull(actual);
    }

    @Test
    public void getUserWord() throws Exception {
        User user = userRepository.getByName("user");
        if(user == null) throw new Exception();
        TranslationWord actual = translationWordRepository.getUserWord("user", user.getId());
        TranslationWordData.assertMath(actual, TRANSLATION_USER);
    }

    @Test
    public void getUserWordNull() throws Exception {
        User user = userRepository.getByName("user");
        if(user == null) throw new Exception();
        TranslationWord actual = translationWordRepository.getUserWord("dictionary", user.getId());
        assertNull(actual);
    }

    @Test
    public void getAll() throws Exception {
        List<TranslationWord> actual = translationWordRepository.getAll();
        TranslationWordData.assertMath(actual,
                TRANSLATION_DICTIONARY,
                TRANSLATION_LARGE,
                TRANSLATION_BOOK,
                TRANSLATION_USER,
                TRANSLATION_HUMAN);
    }

    @Test
    public void getAllCommon() throws Exception {
        List<TranslationWord> actual = translationWordRepository.getAllCommon();
        TranslationWordData.assertMath(actual,
                TRANSLATION_DICTIONARY,
                TRANSLATION_LARGE,
                TRANSLATION_BOOK,
                TRANSLATION_HUMAN);
    }

    @Test
    public void getAllByUser() throws Exception {
        User user = userRepository.getByName("user");
        List<TranslationWord> actual = translationWordRepository.getAllByUser(user.getId());
        TranslationWordData.assertMath(actual,
                TRANSLATION_USER);
    }

    @Test
    public void getAllByUserAndCommon() throws Exception {
        User user = userRepository.getByName("user");
        List<TranslationWord> actual = translationWordRepository.getAllByUserAndCommon(user.getId());
        TranslationWordData.assertMath(actual,
                TRANSLATION_DICTIONARY,
                TRANSLATION_LARGE,
                TRANSLATION_BOOK,
                TRANSLATION_USER,
                TRANSLATION_HUMAN);
    }

    @Test
    public void getInQuantity() throws Exception {
        //TODO
    }


}