package ru.mrak.LargeDictionary.data;

import ru.mrak.LargeDictionary.model.user.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.mrak.LargeDictionary.data.BookData.BOOK_TEST_BOOK;
import static ru.mrak.LargeDictionary.data.TranslationWordData.*;

public class UserData {
    public static final User USER_ADMIN = new User();
    public static final User USER_USER = new User();

    static {
        USER_ADMIN.setName("admin");
        USER_ADMIN.setEmail("mrsaydron@gmail.com");
        USER_ADMIN.setPassword("admin");
        USER_ADMIN.setRegistered(LocalDate.now());
        USER_ADMIN.setRoles(Role.ROLE_ADMIN);

        USER_USER.setName("user");
        USER_USER.setEmail("test@test.com");
        USER_USER.setPassword("user");
        USER_USER.setRegistered(LocalDate.now());
        USER_USER.setRoles(Role.ROLE_USER);
        USER_USER.setLearnWords(
                new LearnWord(TRANSLATION_DICTIONARY, 1, LocalDate.now(), null, USER_USER),
                new LearnWord(TRANSLATION_LARGE, 1, LocalDate.now(), null, USER_USER),
                new LearnWord(TRANSLATION_BOOK, 1, LocalDate.now(), null, USER_USER),
                new LearnWord(TRANSLATION_USER, 1, LocalDate.now(), null, USER_USER, true)
        );
        USER_USER.setLearnSets(
                new LearnSet("Все слова", USER_USER.getLearnWords(), USER_USER)
        );
        USER_USER.setLearnBooks(
                new LearnBook(USER_USER, BOOK_TEST_BOOK, false)
        );
    }

    public static void assertMath(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,
                "id",
                "password",
                "registred",
                "learnWords",
                "learnSets",
                "learnBooks");
    }

    public static void assertMathLearnWord(LearnWord actual, LearnWord expected) {
        actual.setUser(null);
        assertThat(actual).isEqualToIgnoringGivenFields(expected,
                "id",
                "translationWord",
                "user");
        assertThat(actual.getTranslationWord()).isEqualToIgnoringGivenFields(expected.getTranslationWord(),
                "id",
                "translations",
                "userId");
    }

}
