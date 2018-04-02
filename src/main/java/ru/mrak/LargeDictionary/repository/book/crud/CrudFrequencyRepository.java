package ru.mrak.LargeDictionary.repository.book.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.book.FrequencyWord;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudFrequencyRepository extends JpaRepository<FrequencyWord, Integer> {
    @Transactional
    @Modifying
    Integer deleteReturnedIntById(int id);

    List<FrequencyWord> findByBook(Book book);
}
