package ru.mrak.LargeDictionary.repository.book.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.PreparedWord;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudPrepareRepository extends JpaRepository<PreparedWord, Integer> {

    @Transactional
    @Modifying
    Integer deleteReturnedIntById(int id);

    List<PreparedWord> findByBook(Book book);
}
