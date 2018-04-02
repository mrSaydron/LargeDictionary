package ru.mrak.LargeDictionary.to;

import ru.mrak.LargeDictionary.model.user.LearnWord;
import ru.mrak.LargeDictionary.model.word.Translation;

import java.io.Serializable;

public class LearnWordTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String word;
    private String transcription;
    private String translations;
    private int frequency;

    public LearnWordTo() {
    }

    public LearnWordTo(Integer id, String word, String transcription, String translations, int frequency) {
        super(id);
        this.word = word;
        this.transcription = transcription;
        this.translations = translations;
        this.frequency = frequency;
    }

    public LearnWordTo(LearnWord learnWord) {
        convertTo(learnWord);
    }

    public LearnWordTo convertTo(LearnWord learnWord) {
        setId(learnWord.getId());
        this.word = learnWord.getTranslationWord().getWord();
        this.transcription = learnWord.getTranslationWord().getTranscription();
        this.translations = learnWord.getTranslationWord().getOneTranslation();
        this.frequency = learnWord.getFrequency();
        return this;
    }

    public LearnWord toConvert(LearnWordTo learnWordTo) {
        return null;
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

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}