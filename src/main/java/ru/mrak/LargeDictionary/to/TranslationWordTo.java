package ru.mrak.LargeDictionary.to;

import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.io.Serializable;

public class TranslationWordTo extends BaseTo implements Serializable{
    private static final long serialVersionUID = 1L;

    private String word;
    private String transcription;
    private String translations;

    public TranslationWordTo() {
    }

    public TranslationWordTo(Integer id, String word, String transcription, String translations) {
        super(id);
        this.word = word;
        this.transcription = transcription;
        this.translations = translations;
    }

    public TranslationWordTo(String word, String transcription, String translations) {
        super(null);
        this.word = word;
        this.transcription = transcription;
        this.translations = translations;
    }

    public TranslationWordTo convertTo(TranslationWord translationWord) {
        this.word = translationWord.getWord();
        this.transcription = translationWord.getTranscription();
        this.translations = translationWord.getOneTranslation();
        return this;
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
}
