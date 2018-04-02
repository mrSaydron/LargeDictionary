package ru.mrak.LargeDictionary.service.user;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mrak.LargeDictionary.model.user.LearnBook;
import ru.mrak.LargeDictionary.model.user.LearnSet;
import ru.mrak.LargeDictionary.model.user.LearnWord;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.repository.user.LearnBookRepository;
import ru.mrak.LargeDictionary.repository.user.LearnSetRepository;
import ru.mrak.LargeDictionary.repository.user.LearnWordRepository;
import ru.mrak.LargeDictionary.repository.user.UserRepository;
import ru.mrak.LargeDictionary.repository.word.TranslationWordRepository;
import ru.mrak.LargeDictionary.service.word.TranslationWordService;
import ru.mrak.LargeDictionary.util.ValidatorsUtil;
import ru.mrak.LargeDictionary.util.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

import static ru.mrak.LargeDictionary.util.ValidatorsUtil.checkNotFound;
import static ru.mrak.LargeDictionary.util.ValidatorsUtil.checkNotFoundWithId;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LearnWordRepository learnWordRepository;
    private final LearnSetRepository learnSetRepository;
    private final LearnBookRepository learnBookRepository;
    private final TranslationWordRepository translationWordRepository;
    private final TranslationWordService translationWordService;

    public UserService(UserRepository userRepository, LearnWordRepository learnWordRepository, LearnSetRepository learnSetRepository, LearnBookRepository learnBookRepository, TranslationWordRepository translationWordRepository, TranslationWordService translationWordService) {
        this.userRepository = userRepository;
        this.learnWordRepository = learnWordRepository;
        this.learnSetRepository = learnSetRepository;
        this.learnBookRepository = learnBookRepository;
        this.translationWordRepository = translationWordRepository;
        this.translationWordService = translationWordService;
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.get(id), id);
    }

    public void delete(int id) {
        checkNotFound(userRepository.delete(id), "Не могу удалить " + id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getByName(String name) {return userRepository.getByName(name);}

    public User update(User user) {
        return checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    public User getLearnSet(User user) {
        if(user.getLearnSets() instanceof PersistentBag) {
            user.setLearnSets(learnSetRepository.getAll(user.getId()));
            user.getLearnSets().forEach(s -> {
                s.setUser(user);
                s.getLearnWords().forEach(w -> w.setUser(user));});
        }
        return user;
    }

    public User getLearnBook(User user) {
        if(user.getLearnBooks() instanceof PersistentBag) {
            user.setLearnBooks(learnBookRepository.getAll(user.getId()));
            user.getLearnBooks().forEach(b -> b.setUser(user));
        }
        return user;
    }

    public User getLearnWord(User user) {
        if(user.getLearnBooks() instanceof PersistentBag) {
            user.setLearnWords(learnWordRepository.getAll(user.getId()));
        }
        return user;
    }

    public User create(User user) {
        Assert.notNull(user, "Пользователь не должен быть пустым");
        return userRepository.save(user);
    }

    @Transactional
    public void deleteLearnWords(List<Integer> wordsId, User user) {
        for(Integer id: wordsId) {
            checkNotFoundWithId(id, learnWordRepository.delete(id, user.getId()));
        }
    }

    /**
     * Ищу изучаемое пользователем слово.
     * Сначала проводит поиск по словам пользователя, затем по общим словам.
     * @param word - искомое слово
     * @param user - пользователь в изучаемых словах которого ищется слово
     * @return Возвращает изучаемое слово или null если этого слова нет в списке изучаемых.
     * @exception NotFoundException если такого слова нет ни в общем словаре или слово не изучается пользователем
     */
    public LearnWord getLearnWord(String word, User user) {
        TranslationWord translationWord = translationWordRepository.getUserWord(word, user.getId());
        if(translationWord == null) translationWord = translationWordRepository.getCommonWord(word);
        checkNotFound(translationWord, "Для искомого слова нет перевода");
        LearnWord learnWord = learnWordRepository.getByTranslationWord(translationWord.getId(), user.getId());
        checkNotFound(learnWord, "Нет искомого слова в словаре");
        return learnWord;
    }

    /**
     * Ищу изучаемое слово у пользователя
     * @param learnWordId номер изучаемого слова
     * @param user собстввенник изучаемого слова
     * @return изучаемое слово
     * @exception NotFoundException если ислово с данным ID пользователем не изучается
     */
    public LearnWord getLearnWord(int learnWordId, User user) {
        Assert.isNull(user, "Пользователь не может быть пустым");
        LearnWord learnWord = learnWordRepository.get(learnWordId, user.getId());
        checkNotFound(learnWord, "Нет такого слова в словаре пользователя");
        return learnWord;
    }

    /**
     * Метод создает новое изучаемое слово, передварительно сохранив перевод слова ели его еще нет
     * @param learnWord изучаемой слово
     * @param user изучающий слово пользователь
     * @return сохраненное в бд изучаемое слово
     * @exception NotFoundException если не удалось создать изучаемое слово
     */
    @Transactional
    public LearnWord createLearnWord(LearnWord learnWord, User user) {
        Assert.isNull(learnWord, "Изучаемое слово не может быть пустым");
        Assert.isNull(user, "Пользователь не может быть пустым");
        Assert.isNull(learnWord.getTranslationWord(), "Перевод слова не может быть пустым");
        if(learnWord.getTranslationWord().isNew()) {
            TranslationWord translationWord = learnWord.getTranslationWord();
            learnWord.setTranslationWord(translationWordService.create(translationWord));
        }
        learnWord = learnWordRepository.save(learnWord, user.getId());
        checkNotFound(learnWord, "Не удалось создать слово в словаре");
        return learnWord;
    }

    /**
     * Метод обнавляет изучаемое слово для указанного пользователя с обновлением перевода слова.
     * @param learnWord новое изучаемое слово
     * @param user пользователь для которого обноляеь=тся изучаемое слово
     * @return новое изучаемое слово
     * @exception NotFoundException если не удалось обновить изучаемое слово
     */
    @Transactional
    public LearnWord updateLearnWord(LearnWord learnWord, User user) {
        Assert.isNull(learnWord, "Изучаемое слово не может быть пустым");
        Assert.isNull(user, "Пользователь не может быть пустым");
        Assert.isNull(learnWord.getTranslationWord(), "Перевод слова не может быть пустым");
        if(learnWord.isNew()) throw new RuntimeException("Нельзя обновить изучаемое слово с пустым Id");
        LearnWord oldLearnWord = learnWordRepository.get(learnWord.getId(), user.getId());
        checkNotFound(oldLearnWord, "Не найдено изучаемое слово с данным Id");
        if(!oldLearnWord.isUserEdited() && learnWord.isUserEdited()) {
            //Если общий первод слова сменился на пользовательский
            TranslationWord translationWord = translationWordService.create(learnWord.getTranslationWord());
            learnWord.setTranslationWord(translationWord);
        } else if(oldLearnWord.isUserEdited() && learnWord.isUserEdited()) {
            //Если изменился пользовательский перевод слова
            TranslationWord translationWord = translationWordService.update(learnWord.getTranslationWord());
        } else if(oldLearnWord.isUserEdited() && !learnWord.isUserEdited()) {
            //Если позьзовательский перевод слова сменился на общий перевод
            translationWordService.delete(oldLearnWord.getTranslationWord().getId());
        }
        learnWord = learnWordRepository.save(learnWord, user.getId());
        checkNotFound(learnWord, "Не удалось обновить изучаемое слово");
        return learnWord;
    }
}
