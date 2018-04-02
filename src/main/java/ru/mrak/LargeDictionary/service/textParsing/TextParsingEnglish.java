package ru.mrak.LargeDictionary.service.textParsing;

import org.springframework.stereotype.Service;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.PreparedWord;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Парсинг английского текста
 */

@Service
public class TextParsingEnglish implements TextParsing {
    private static final int ADDITIONAL_TEXT_LENGTH = 50;
    private static final char[] chars = {'A','B','C','D','E','F','G','H','I','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final char[] endChars = {'!','.','?'};
    private static final char[] joinChars = {'-'};
    private static final char[] breakChars = {'\n','\r'};

    @Override
    public void preparedBook(Book book) {
        LinkedList<PreparedWord> words = new LinkedList<>();
        int numberChar = 0;
        int startWord = -1;
        int finishWord = -1;
        StringBuilder workWord = new StringBuilder("");

        boolean firstWord = true;
        boolean isPreviousChar = false;

        String text = book.getText();
        while(true) {
            char workChar = text.charAt(numberChar);
            //Есть ли рабочий символ буква
            if(Arrays.binarySearch(chars, workChar) >= 0) {
                if(Character.getType(workChar) == Character.UPPERCASE_LETTER && firstWord) {
                    workChar = Character.toLowerCase(workChar);
                }
                if(!isPreviousChar) {
                    startWord = numberChar;
                    workWord = new StringBuilder("");
                }
                finishWord = numberChar;
                workWord.append(workChar);
                isPreviousChar = true;
                firstWord = false;
                numberChar++;
            }
            //Если тире
            else if (Arrays.binarySearch(joinChars, workChar) >= 0 && isPreviousChar && (numberChar + 1) < text.length()) {
                char nextChar = text.charAt(numberChar++);
                if(Arrays.binarySearch(joinChars, nextChar) >= 0) {
                    workWord.append(workChar);
                    numberChar++;
                    finishWord = numberChar;
                } else {
                    numberChar++;
                    while (Arrays.binarySearch(breakChars, text.charAt(numberChar)) >= 0) {
                        if ((numberChar + 1) < text.length()) numberChar++;
                    }
                }
            } else {
                //Если точка
                if (Arrays.binarySearch(endChars, workChar) > 0) {
                    firstWord = true;
                }
                if(isPreviousChar){
                    words.add(new PreparedWord(workWord.toString(), book, startWord, finishWord + 1));
                    isPreviousChar = false;
                }
                numberChar++;
            }
            if(numberChar >= text.length()) break;
        }
        if(isPreviousChar) {
            words.add(new PreparedWord(workWord.toString(), book, startWord, finishWord + 1));
        }
        book.setPreparedWords(words);
    }
}
