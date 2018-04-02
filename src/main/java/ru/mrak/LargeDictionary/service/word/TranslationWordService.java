package ru.mrak.LargeDictionary.service.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.mrak.LargeDictionary.model.AbstractBaseEntity;
import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.repository.word.TranslationWordRepository;
import ru.mrak.LargeDictionary.repository.word.TranslationRepository;
import ru.mrak.LargeDictionary.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static ru.mrak.LargeDictionary.util.ValidatorsUtil.checkNotFound;
import static ru.mrak.LargeDictionary.util.ValidatorsUtil.checkNotFoundWithId;

@Service
public class TranslationWordService {

    private final TranslationWordRepository translationWordRepository;
    private final TranslationRepository translationRepository;

    @Autowired
    public TranslationWordService(TranslationWordRepository wordRepository, TranslationRepository translationRepository) {
        this.translationWordRepository = wordRepository;
        this.translationRepository = translationRepository;
    }

    public TranslationWord get(int id) {
        return checkNotFoundWithId(translationWordRepository.get(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(translationWordRepository.delete(id), id);
    }

    public List<TranslationWord> getAll() {
        return translationWordRepository.getAll();
    }

    /**
     * Метод обновляет перевод слова предварительно проверив были ли удалены, обновлены или созданы новые переводы.
     * @param word новый перевод слова для обновления
     * @return обновленный перевод слова
     * @exception RuntimeException если не удалось удалить первод
     * @exception NotFoundException если не удалось создать перевод или перевод слова
     */
    @Transactional
    public TranslationWord update(TranslationWord word) {
        Assert.notNull(word, "Перевод слова не должен быть пустым");
        TranslationWord oldWord = translationWordRepository.get(word.getId());
        List<Translation> newTranslations = word.getTranslations();
        List<Translation> resultTranslations = word.getTranslations();
        //Удалить неиспользуемые переводы
        List<Translation> deleteTranslations = new ArrayList<>(resultTranslations);
        deleteTranslations.retainAll(newTranslations);
        deleteTranslations.forEach(t -> {
            if(!translationRepository.delete(t.getId())) throw new RuntimeException("Не удалось удалить перевод");
        });
        resultTranslations.retainAll(newTranslations);
        //Обновить переводы
        resultTranslations.forEach(t -> {
            Translation compareTranslation = newTranslations.get(newTranslations.indexOf(t));
            if(!compareTranslation.getTranslation().equals(t.getTranslation()) ||
                    !compareTranslation.getPartOfSpeech().equals(t.getPartOfSpeech())) {
                compareTranslation = translationRepository.save(compareTranslation);
                checkNotFound(compareTranslation, "Не удалось обновить перевод");
                t = compareTranslation;
            }
        });
        //Добавить новые переводы
        newTranslations.stream().filter(Translation::isNew).forEach(t -> {
            t = translationRepository.save(t);
            checkNotFound(t, "Не удалось создать перевод");
            resultTranslations.add(t);
        });
        //Обновляю перевод слова
        word.setTranslations(resultTranslations);
        return checkNotFoundWithId(translationWordRepository.save(word), word.getId());
    }

    /**
     * Метод создает новые перевод слова предварительно создав переводы
     * @param word новый перевод слова
     * @return новый перевод слова
     * @exception NotFoundException если не удалось создать перевод или перевод слова
     */
    @Transactional
    public TranslationWord create(TranslationWord word) {
        Assert.notNull(word, "Перевод слова не должен быть пустым");
        List<Translation> translationList = new ArrayList<>(word.getTranslations().size());
        for (Translation translation : word.getTranslations()) {
            translation = translationRepository.save(translation);
            checkNotFound(translation, "Не удалось сохранить перевод");
            translationList.add(translation);
        }
        word.setTranslations(translationList);
        word = translationWordRepository.save(word);
        checkNotFound(word, "Не удалось создать первод слова");
        return word;
    }

    public TranslationWord addTranslation(TranslationWord word, Translation translation) {
        translation.setTranslationWord(word);
        word.addTranslation(translation);
        if (translationRepository.save(translation) == null) {
            return null;
        }
        return word;
    }

    public TranslationWord getCommonWord(String word) {
        return translationWordRepository.getCommonWord(word);
    }

    //TODO: добавить удаление перевода
    //TODO: добавить редактирование перевода
}
