package ru.mrak.LargeDictionary.service.textProvider;

import ru.mrak.LargeDictionary.model.book.Book;

class BookProviderText {
    private BookProviderText() {
    }

    public static Book getBook(String text) {
        Book book = new Book();
        book.setText(text);
        return book;
    }
}
