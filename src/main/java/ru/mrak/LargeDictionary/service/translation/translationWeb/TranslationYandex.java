package ru.mrak.LargeDictionary.service.translation.translationWeb;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.mrak.LargeDictionary.model.book.PreparedWord;
import ru.mrak.LargeDictionary.model.word.Translation;
import ru.mrak.LargeDictionary.model.word.TranslationWord;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.mrak.LargeDictionary.model.word.TranslationWord.COMMON_WORD;

@Service
public class TranslationYandex implements TranslationWeb {
    private static final String FORMAT_TO_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=%s&lang=en-ru&text=%s";
    private static String KEY;
    private static final Logger LOG = getLogger(TranslationYandex.class);

    static
    {
        InputStream stream = TranslationYandex.class.getResourceAsStream("keys.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
            KEY = properties.getProperty("yandex_dictionary_key");
        } catch (IOException e) {
            e.printStackTrace();
            KEY = null;
            LOG.error("Не могу прочитать ключ для яндекса: " + e.getMessage());
        }
    }

    @Override
    public TranslationWord getTranslation(PreparedWord word) {
        LOG.debug("Translation for yandex: " + word);
        if(KEY == null) {
            LOG.error("Key for yandex is null");
            return null;
        }

        try {
            //Подключаюсь к яндексу
            URL url = new URL(String.format(FORMAT_TO_URL, KEY, word.getWord()));
            URLConnection connection = url.openConnection();
            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());

            //читаю JSON ответ
            StringBuilder jsonText = new StringBuilder();
            char[] buffer = new char[1024];
            int size;
            while((size = inputStream.read(buffer, 0, 1024)) > 0) {
                if(size == 1024)
                    jsonText.append(buffer);
                else
                    jsonText.append(buffer, 0, size);
            }

            inputStream.close();

            try {
                //парсю ответ
                ObjectMapper mapper = new ObjectMapper();
                DicResult dicResult = mapper.readValue(jsonText.toString(), DicResult.class);
                //преобразую в перевод
                return convert(dicResult);
            } catch (JsonMappingException e) {
                LOG.error("Ошибка парсинга JSON от яндекса: " + e.getLocalizedMessage());
                return null;
            }
        } catch (IOException e) {
            LOG.error("Connection failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TranslationWord> getTranslation(List<PreparedWord> words) {
        LOG.debug("Translation words for yandex");
        if(KEY == null) {
            LOG.error("Key for yandex is null");
            return null;
        }

        Map<String, TranslationWord> cashe = new HashMap<>();
        List<TranslationWord> result = new LinkedList<>();
        Iterator<PreparedWord> iterator = words.iterator();
        while (iterator.hasNext()) {
            PreparedWord preparedWord = iterator.next();
            TranslationWord translationWord;
            if(cashe.containsKey(preparedWord.getWord())) {
                translationWord = cashe.get(preparedWord.getWord());
            } else {
                translationWord = getTranslation(preparedWord);
                cashe.put(preparedWord.getWord(), translationWord);
            }
            if (translationWord != null) {
                result.add(translationWord);
                iterator.remove();
            }
        }
        return result;
    }

    @Override
    public boolean isWork() {
        return true;
    }

    private TranslationWord convert(DicResult dicResult) {
        if(dicResult.defines.isEmpty()) return null;

        List<Translation> translations = new ArrayList<>();
        for (Define define: dicResult.defines) {
            String partOfSpeech;
            if(define.pos == null) {
                partOfSpeech = "";
            }
            else {
                switch (define.pos) {
                    case "noun":
                        partOfSpeech = "существительное";
                        break;
                    case "verb":
                        partOfSpeech = "глагол";
                        break;
                    case "adjective":
                        partOfSpeech = "прилагательное";
                        break;
                    case "adverb":
                        partOfSpeech = "наречие";
                        break;
                    case "pronoun":
                        partOfSpeech = "местоимение";
                        break;
                    case "preposition":
                        partOfSpeech = "предлог";
                        break;
                    case "conjunction":
                        partOfSpeech = "конъюнкция";
                        break;
                    case "interjection":
                        partOfSpeech = "междоменте";
                        break;
                    case "determiner":
                        partOfSpeech = "детерминанта";
                        break;
                    default:
                        partOfSpeech = define.pos;
                }
            }

            for (Trans trans: define.translations) {
                if(!trans.examples.isEmpty()) {
                    translations.add(new Translation(trans.text, partOfSpeech));
                }
            }
        }
        if(translations.isEmpty()) {
            String partOfSpeech = dicResult.defines.get(0).pos;
            translations.add(new Translation(dicResult.defines.get(0).translations.get(0).text, partOfSpeech));
        }

        return new TranslationWord(
                dicResult.defines.get(0).text,
                dicResult.defines.get(0).transcription,
                null,
                translations);
    }

    public static void main(String[] args) {
        TranslationYandex translationYandex = new TranslationYandex();
        PreparedWord preparedWord = new PreparedWord("is", null, 0, 0);
        TranslationWord translationWord = translationYandex.getTranslation(preparedWord);
        System.out.println(translationWord);
    }
}

//Результирующий объект
@JsonAutoDetect
class DicResult {
    @JsonProperty(value = "head")
    public Object head;

    @JsonProperty(value = "def")
    @JsonDeserialize(as = ArrayList.class, contentAs = Define.class)
    List<Define> defines = new ArrayList<>();
}

//Словарная статья
@JsonAutoDetect
class Define {
    @JsonProperty(value = "text")
    public String text;

    @JsonProperty(value = "pos")
    public String pos;

    @JsonProperty(value = "ts")
    public String transcription;

    @JsonProperty(value = "tr")
    @JsonDeserialize(as = ArrayList.class, contentAs = Trans.class)
    List<Trans> translations = new ArrayList<>();

    @JsonProperty(value = "fl")
    public String irregularVerb;

    @JsonProperty(value = "new")
    public String state;
}

//Переводы
@JsonAutoDetect
class Trans {
    //Перевод
    @JsonProperty(value = "text")
    String text;

    //Часть речи
    @JsonProperty(value = "pos")
    String pos;

    //род
    @JsonProperty(value = "gen")
    String gender;

    //Аспект
    @JsonProperty(value = "asp")
    String aspect;

    //Синонимы
    @JsonProperty(value = "syn")
    @JsonDeserialize(as = ArrayList.class, contentAs = Synonym.class)
    List<Synonym> synonims = new ArrayList<>();

    //Значения
    @JsonProperty(value = "mean")
    @JsonDeserialize(as = ArrayList.class, contentAs = Text.class)
    List<Text> means = new ArrayList<>();

    //Примеры
    @JsonProperty(value = "ex")
    @JsonDeserialize(as = ArrayList.class, contentAs = Example.class)
    List<Example> examples = new ArrayList<>();
}

//Синонимы
@JsonAutoDetect
class Synonym {
    @JsonProperty(value = "text")
    String text;

    @JsonProperty(value = "pos")
    String pos;

    @JsonProperty(value = "gen")
    String gender;

    @JsonProperty(value = "asp")
    String aspect;
}

//Примеры
@JsonAutoDetect
class Example {
    @JsonProperty(value = "text")
    String text;

    @JsonProperty(value = "tr")
    @JsonDeserialize(as = ArrayList.class, contentAs = Text.class)
    List<Text> translation = new ArrayList<>();
}

//Текст
@JsonAutoDetect
class Text {
    @JsonProperty(value = "text")
    String text;
}


