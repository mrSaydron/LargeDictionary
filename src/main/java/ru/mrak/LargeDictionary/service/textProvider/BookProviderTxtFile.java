package ru.mrak.LargeDictionary.service.textProvider;

import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.util.exception.ProviderException;

import java.io.*;

/**
 * Создает книгу из вайла TXT
 */

class BookProviderTxtFile {
    private static final int BUFFER_SIZE = 1024;

    private BookProviderTxtFile() {
    }

    public static Book getText(String filePath) throws ProviderException {
        try {
            StringBuilder text = new StringBuilder(BUFFER_SIZE);
            InputStreamReader inputStream = new InputStreamReader(new FileInputStream(filePath), "UTF-8");

            char[] buffer = new char[BUFFER_SIZE];
            int size;
            while((size = inputStream.read(buffer, 0, BUFFER_SIZE)) > 0) {
                if(size == BUFFER_SIZE)
                    text.append(buffer);
                else
                    text.append(buffer, 0, size);
            }
            //Создаю книгу и добавляю ей имя
            Book book = new Book();
            book.setText(text.toString());
            int slashPosition = text.lastIndexOf("\\");
            int dotPosition = text.lastIndexOf(".");
            book.setTitle(filePath.substring(slashPosition + 1, dotPosition));
            return book;
        } catch (IOException e) {
            throw new ProviderException();
        }
    }
}