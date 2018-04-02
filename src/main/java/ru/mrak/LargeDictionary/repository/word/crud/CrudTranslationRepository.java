package ru.mrak.LargeDictionary.repository.word.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

@Transactional(readOnly = true)
public interface CrudTranslationRepository extends JpaRepository<Translation, Integer> {

    @Transactional
    @Modifying
    int deleteReturnedIntById(int id);

}
