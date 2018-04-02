package ru.mrak.LargeDictionary.web.ajax;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ru.mrak.LargeDictionary.model.user.LearnWord;
import ru.mrak.LargeDictionary.model.user.User;
import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;
import ru.mrak.LargeDictionary.service.user.UserService;
import ru.mrak.LargeDictionary.service.word.TranslationWordService;
import ru.mrak.LargeDictionary.to.LearnWordTo;
import ru.mrak.LargeDictionary.to.SearchWordTo;
import ru.mrak.LargeDictionary.web.AuthorizedUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/learnword")
public class LearnWordController {

    private TranslationWordService translationWordService;
    private UserService userService;
    private AuthorizedUser authorizedUser;

    public LearnWordController(TranslationWordService translationWordService, UserService userService, AuthorizedUser authorizedUser) {
        this.translationWordService = translationWordService;
        this.userService = userService;
        this.authorizedUser = authorizedUser;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LearnWordTo> getAll() {
        User user = userService.get(authorizedUser.getId());
        userService.getLearnWord(user);
        List<LearnWordTo> learnWordTos = new ArrayList<>(user.getLearnWords().size());
        for (LearnWord learnWord: user.getLearnWords()) {
            learnWordTos.add(new LearnWordTo(learnWord));
        }
        return learnWordTos;
    }

    @PostMapping()
    public void delete(@RequestParam("id[]") String[] wordsId) {
        User user = userService.get(authorizedUser.getId());
        List<Integer> wordsIdList = new ArrayList<>(wordsId.length);
        Arrays.stream(wordsId).forEach(s -> wordsIdList.add(Integer.parseInt(s)));
        userService.deleteLearnWords(wordsIdList, user);
    }

    /**
     * Ищу слова
     * Сначала проверяю словарь пользователя, затем ищу общей вариант слова
     * @param word Искомое слово
     * @return - составляю объект из общего слова и слова пользователя
     */
    @PostMapping(value = "/word")
    public SearchWordTo getWord(@RequestParam(value = "word") String word) {
        //TODO решить переводить ли в строчные буквы
        User user = userService.get(authorizedUser.getId());
        LearnWord learnWord = userService.getLearnWord(word, user);
        TranslationWord translationWord = null;
        //Если это слово есть у пользователя и оно отредактированно пользователем
        if(learnWord != null && learnWord.isUserEdited()) {
            translationWord = translationWordService.getCommonWord(word);
        } else if(learnWord != null && !learnWord.isUserEdited()) {
            //Если это слово общее и есть у пользователя
            translationWord = learnWord.getTranslationWord();
        } else {
            //Если такого слова у пользователя нет
            translationWord = translationWordService.getCommonWord(word);
        }
        //Если такого слова не найдено ни у пользователя, ни в общих словах
        if(translationWord == null && learnWord == null) return null;
        SearchWordTo searchWordTo = new SearchWordTo();
        searchWordTo.setWord(word);
        if(learnWord != null) {
            searchWordTo.setTranscription(learnWord.getTranslationWord().getTranscription());
            searchWordTo.setTranslations(learnWord.getTranslationWord().getOneTranslation());
            searchWordTo.setLearnWordId(learnWord.getId());
            if(learnWord.isUserEdited()) {
                searchWordTo.setUserEdit(true);
                searchWordTo.setUserWordId(learnWord.getTranslationWord().getId());
            } else {
                searchWordTo.setUserWordId(TranslationWord.COMMON_WORD);
            }
        }
        if(translationWord != null) {
            searchWordTo.setCommonTranscription(translationWord.getTranscription());
            searchWordTo.setCommonTranslation(translationWord.getOneTranslation());
            searchWordTo.setCommonWordId(translationWord.getId());
        }
        return searchWordTo;
    }

    /**
     * Сохраняю или обновляю изучаемое слово
     * @param id если не пустое, то слово на изучении
     * @param word новое слово
     * @param transcription транскрипция нового слова
     * @param translation перевод нового слова
     * @return новое изучаемое слово
     */
    @PostMapping(value = "/addWord")
    public LearnWordTo addWord(@RequestParam(value = "id", required = false) String id,
                               @RequestParam(value = "word") String word,
                               @RequestParam(value = "transcription") String transcription,
                               @RequestParam(value = "translation") String translation) {
        Integer learnWordId = null;
        if(id != null && !id.isEmpty()) learnWordId = Integer.parseInt(id);
        if(word == null || word.isEmpty()) throw new RuntimeException();
        if(transcription == null || transcription.isEmpty()) throw new RuntimeException();
        if(translation == null || translation.isEmpty()) throw new RuntimeException();

        User user = userService.get(authorizedUser.getId());
        TranslationWord commonWord = translationWordService.getCommonWord(word);
        TranslationWord newWord =
                new TranslationWord(word, transcription, user, new Translation(translation, null));
        LearnWord learnWord = null;
        if(learnWordId == null) {
            //Id пустой, создаю новое изучаемое слово с переводом
            if(commonWord != null) {
                //Имеется общий перевод для слова
                if(commonWord.equalsWithoutUser(newWord)) {
                    //Перевод совпадает с имеющимся переводом, использую этот перевод
                    learnWord = new LearnWord(commonWord, 0, LocalDate.now(), null, user, false);
                    learnWord = userService.createLearnWord(learnWord, user);
                } else {
                    //Перевод не совпадает с имеющимся переводом, создаю пользовательский перевод
                    newWord = translationWordService.create(newWord);
                    learnWord = new LearnWord(newWord, 0, LocalDate.now(), null, user, true);
                    learnWord = userService.createLearnWord(learnWord, user);
                }
            } else {
                //Общего перевода для слова нет, создаю пользовательский перевод
                newWord = translationWordService.create(newWord);
                learnWord = new LearnWord(newWord, 0, LocalDate.now(), null, user, true);
                learnWord = userService.createLearnWord(learnWord, user);
            }
        } else {
            //Id не пустй, ищу такое слово в словаре пользователя
            learnWord = userService.getLearnWord(learnWordId, user);
            if(learnWord.getTranslationWord().getWord().equals(word)) {
                //Если Id и слово совпадают то обновляю слово
                if(learnWord.isUserEdited()) {
                    //Если у слова пользовательскиу перевод, то обновляю перевод
                    if(commonWord.equalsWithoutUser(newWord)) {
                        //Пользователь возвращает перевод к общему переводу
                        //TODO
                    } else {
                        //Пользователь обновляет свой перевод
                    }
                } else {
                    //У слова был общий первод
                    if(!commonWord.equalsWithoutUser(newWord)) {
                        //Создаю пользовательский перевод для этого слова
                    }
                }
            } else {
                //Id и слово не совпали. Ошибка на стороне пользовательского интерфеса
                throw new RuntimeException("Id и слово не совпали");
            }
        }
        return new LearnWordTo(learnWord);
    }
}
