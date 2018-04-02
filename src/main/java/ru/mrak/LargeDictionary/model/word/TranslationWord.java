package ru.mrak.LargeDictionary.model.word;

import ru.mrak.LargeDictionary.model.AbstractBaseEntity;
import ru.mrak.LargeDictionary.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

//Перевод слова со списком значений

/** Перевод слова со списком значений
 * word - слово
 * transcription - транскрипция
 * translations - список значений
 * userId - слово отредактированно/создано пользователем и его видит только этот пользователь
 */

@Entity
@Table(name = "translation_words",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"word", "user_id"}, name = "translation_word_unique_word_idx")})
public class TranslationWord extends AbstractBaseEntity {
    public static final int COMMON_WORD = -1;

    @Column(name = "word", nullable = false)
    @NotEmpty
    private String word;

    @Column(name = "transcription", nullable = false)
    @NotEmpty
    private String transcription;

    @OneToMany(fetch = EAGER, mappedBy = "translationWord")
    private List<Translation> translations;

    @Column(name = "user_id")
    @NotNull
    private int userId;

    public TranslationWord() {}

    public TranslationWord(Integer id, String word, String transcription, User user, List<Translation> translations) {
        super(id);
        this.word = word;
        this.transcription = transcription;
        translations.forEach(t -> t.setTranslationWord(this));
        this.translations = translations;
        userId = user == null ? COMMON_WORD : user.getId();
    }

    public TranslationWord(String word, String transcription, User user, List<Translation> translations) {
        this(null, word, transcription, user, translations);
    }

    public TranslationWord(String word, String transcription, User user, Translation... translations) {
        this(null, word, transcription, user, new LinkedList<>(Arrays.asList(translations)));
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOneTranslation() {
        StringBuilder translationSum = new StringBuilder("");
        for (Translation translationOne : translations) {
            translationSum.append(translationOne.getTranslation()).append(" ");
        }
        return translationSum.toString();
    }

    public void addTranslation(Translation translation) {
        translation.setTranslationWord(this);
        translations.add(translation);
    }

    public boolean deleteTranslition(String translationWord) {
        Iterator<Translation> iterator = translations.iterator();
        while (iterator.hasNext()) {
            Translation translation = iterator.next();
            if(translation.getTranslation().equals(translationWord)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Сравниваю переданный перевод слова с переданным
     * переводы сравниваются составленными в одну строку
     * принадлежность пользователю не сравнивается
     * @param actualWord перевод слова для сравнения
     * @return правду если переводы слов иддентичны
     */
    public boolean equalsWithoutUser (TranslationWord actualWord) {
        return word.equals(actualWord.getWord()) &&
                transcription.equals(actualWord.getTranscription()) &&
                getOneTranslation().equals(actualWord.getOneTranslation());
    }

    @Override
    public String toString() {
        return "TranslationWord{" +
                "word='" + word + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translations=" + translations +
                '}';
    }
}


