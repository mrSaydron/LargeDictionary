package ru.mrak.LargeDictionary.data;

import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.mrak.LargeDictionary.data.UserData.USER_USER;

public class TranslationWordData {
    public static final TranslationWord TRANSLATION_DICTIONARY =
            new TranslationWord("dictionary", "[ˈdɪkʃəˌnɛri]", null,
                    new Translation("словарь", "существительное"));
    public static final TranslationWord TRANSLATION_LARGE =
            new TranslationWord("large", "[lärj]", null,
                    new Translation("большой", "прилогательное"),
                    new Translation("крупный", "прилогательное"));
    public static final TranslationWord TRANSLATION_BOOK =
            new TranslationWord("book", "[bʊk]", null,
                    new Translation("книга", "прилогательное'"));
    public static final TranslationWord TRANSLATION_HUMAN =
            new TranslationWord("human", "[ˈhjuːmən]", null,
                    new Translation("человеческий", "прилогательное"),
                    new Translation("человек", "существительное"));
    public static final TranslationWord TRANSLATION_USER =
            new TranslationWord("user", "[ˈjuːzə]", null,
                    new Translation("потребитель", "существительное"));

    //private static final String FIELD_TO_IGNORE = "id transcription translations userId";
    private static final String FIELD_TO_IGNORE = "id translations userId";

    public static void assertMath(TranslationWord actual, TranslationWord expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, FIELD_TO_IGNORE.split(" "));
    }

    public static void assertMath(List<TranslationWord> actual, List<TranslationWord> expected) {
        Comparator<TranslationWord> comparator = new Comparator<TranslationWord>() {
            @Override
            public int compare(TranslationWord o1, TranslationWord o2) {
                return o1.getWord().compareTo(o2.getWord());
            }
        };
        actual.sort(comparator);
        expected.sort(comparator);
        assertThat(actual).usingElementComparatorIgnoringFields(FIELD_TO_IGNORE.split(" ")).isEqualTo(expected);
    }

    public static void assertMath(List<TranslationWord> actual, TranslationWord... expected) {
        assertMath(actual, Arrays.asList(expected));
    }

    public static void assertContain(Iterable<TranslationWord> actual, TranslationWord... expected) {
        assertThat(actual).usingElementComparatorIgnoringFields(FIELD_TO_IGNORE.split(" ")).contains(expected);
    }
}
