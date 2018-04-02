package ru.mrak.LargeDictionary.repository.user.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.LargeDictionary.model.user.LearnSet;
import ru.mrak.LargeDictionary.model.user.User;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudLearnSetRepository extends JpaRepository<LearnSet, Integer> {

    @Transactional
    @Modifying
    Integer deleteReturnIntByIdAndUser(int id, User user);

    List<LearnSet> findByUser(User user);

}
