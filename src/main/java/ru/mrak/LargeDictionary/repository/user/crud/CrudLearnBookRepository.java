package ru.mrak.LargeDictionary.repository.user.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.user.LearnBook;
import ru.mrak.LargeDictionary.model.user.User;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudLearnBookRepository extends JpaRepository<LearnBook, Integer> {

    @Transactional
    @Modifying
    Integer deleteReturnIntByIdAndUser(int id, User user);

    List<LearnBook> findByUser(User user);
}
