package ru.mrak.LargeDictionary.repository.user.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.book.Book;
import ru.mrak.LargeDictionary.model.user.User;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    Integer deleteReturnIntById(int id);

    User getByName(String name);
}
