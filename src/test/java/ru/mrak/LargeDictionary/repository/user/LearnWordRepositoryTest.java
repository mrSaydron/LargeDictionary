package ru.mrak.LargeDictionary.repository.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mrak.LargeDictionary.data.TranslationWordData;
import ru.mrak.LargeDictionary.data.UserData;
import ru.mrak.LargeDictionary.model.user.LearnWord;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.repository.word.TranslationWordRepository;

import static org.junit.Assert.*;
import static ru.mrak.LargeDictionary.data.TranslationWordData.TRANSLATION_USER;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LearnWordRepositoryTest {

    @Autowired private LearnWordRepository learnWordRepository;
    @Autowired private TranslationWordRepository translationWordRepository;
    @Autowired private UserRepository userRepository;

    @Test
    public void save() {
        //TODO
    }

    @Test
    public void delete() {
        //TODO
    }

    @Test
    public void get() {
        //TODO
    }

    @Test
    public void getAll() {
        //TODO
    }

    @Test
    public void getByTranslationWord() {
        User user = userRepository.getByName("user");
        TranslationWord translationWord = translationWordRepository.getUserWord("user", user.getId());
        LearnWord learnWord = learnWordRepository.getByTranslationWord(translationWord.getId(), user.getId());
        TranslationWordData.assertMath(translationWord, TranslationWordData.TRANSLATION_USER);
        UserData.assertMathLearnWord(learnWord, UserData.USER_USER.getLearnWords().get(3));
    }
}