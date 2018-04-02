package ru.mrak.LargeDictionary.to;

import java.io.Serializable;

public class SearchWordTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String word;
    private String transcription;
    private String translations;
    private String commonTranscription;
    private String commonTranslation;

    private boolean userEdit;

    private int commonWordId;
    private int userWordId;
    private int learnWordId;

    public SearchWordTo() {
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

    public String getCommonTranscription() {
        return commonTranscription;
    }

    public void setCommonTranscription(String commonTranscription) {
        this.commonTranscription = commonTranscription;
    }

    public String getCommonTranslation() {
        return commonTranslation;
    }

    public void setCommonTranslation(String commonTranslation) {
        this.commonTranslation = commonTranslation;
    }

    public boolean isUserEdit() {
        return userEdit;
    }

    public void setUserEdit(boolean userEdit) {
        this.userEdit = userEdit;
    }

    public int getUserWordId() {
        return userWordId;
    }

    public void setUserWordId(int userWordId) {
        this.userWordId = userWordId;
    }

    public int getLearnWordId() {
        return learnWordId;
    }

    public void setLearnWordId(int learnWordId) {
        this.learnWordId = learnWordId;
    }

    public int getCommonWordId() {
        return commonWordId;
    }

    public void setCommonWordId(int comonWordId) {
        this.commonWordId = comonWordId;
    }
}
