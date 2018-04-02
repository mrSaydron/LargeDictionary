package ru.mrak.LargeDictionary.repository.word.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudTranslationWordRepository extends JpaRepository<TranslationWord, Integer> {

    @Transactional
    @Modifying
    int deleteReturnedIntById(int id);

    @Transactional
    @Modifying
    int deleteByWordAndUserId(String word, int userId);

    TranslationWord findByWord(String word);

    List<TranslationWord> findAllByUserId(int userId);

    List<TranslationWord> findAllByUserIdOrUserId(int userIdOne, int userIdTwo);

    TranslationWord findByWordAndUserId(String word, int userId);
}
