package ru.mrak.LargeDictionary.repository.user.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.user.LearnWord;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudLearnWordRepository extends JpaRepository<LearnWord, Integer> {

    @Transactional
    @Modifying
    Integer deleteReturnIntByIdAndUser(int id, User user);

    List<LearnWord> findByUser(User user);

    LearnWord findByUserAndTranslationWord(User user, TranslationWord translationWord);
}
