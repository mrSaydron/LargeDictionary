package ru.mrak.LargeDictionary.repository.book.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.book.Book;

@Transactional(readOnly = true)
public interface CrudBookRepository extends JpaRepository<Book, Integer> {

    @Transactional
    @Modifying
    Integer deleteReturnIntById(int id);
}
