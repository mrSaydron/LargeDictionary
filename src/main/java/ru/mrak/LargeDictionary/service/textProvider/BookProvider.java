package ru.mrak.LargeDictionary.service.textProvider;

import org.springframework.stereotype.Service;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.util.exception.ProviderException;

import java.net.URL;
import java.util.Map;

/**
 * Предоставляет книгу из переданного источника
 * Источник предоставляется в параметре source:
 * filePath - из текстового файла
 * text - передается текст
 *
 * Дополнительные параметры:
 * title - наименование
 * author - автор
 * url - ссылка
 */

@Service
public class BookProvider {
    public Book getBook(Map<String, Object> parametrs) {
        Book book;
        String source = (String)checkMapKey(parametrs, "source");
        switch (source) {
            case "txt_file":
                String filePath = (String)checkMapKey(parametrs, "filePath");
                book = BookProviderTxtFile.getText(filePath);
                break;
            case "text":
                String text = (String)checkMapKey(parametrs, "text");
                book = BookProviderText.getBook(text);
                break;
            default: throw new ProviderException("Нет провайдера книг для источника:" + source);
        }
        if(parametrs.containsKey("title")) book.setTitle((String)parametrs.get("title"));
        if(parametrs.containsKey("author")) book.setAuthor((String)parametrs.get("author"));
        //if(parametrs.containsKey("url")) book.setUrl((URL)parametrs.get("url"));
        return book;
    }

    private static Object checkMapKey(Map<String, Object> checkMap, String key) {
        if(checkMap.containsKey(key)) return checkMap.get(key);
        throw new ProviderException("Провайдер книг не нашел ключ:" + key);
    }
}
