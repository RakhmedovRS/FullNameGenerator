package generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс хендлер рандомных ФИО
 *
 * @author rassoll
 * @created 23.10.2017
 * @$Author$
 * @$Revision$
 */
class FullNameDataHandler
{
    private List<String> firstNames;
    private List<String> lastNames;
    private List<String> patronymics;

    private Random random = new Random();

    FullNameDataHandler() throws IOException
    {
        firstNames = new ArrayList<>();
        lastNames = new ArrayList<>();
        patronymics = new ArrayList<>();
        new Reader(Generator.getInputFileName()).fillFullNames(firstNames, lastNames, patronymics);
    }

    /**
     * Получение рандомного имени
     *
     * @return рандомное имя
     */
    public String getFirstName(boolean needTransliterate)
    {
        if (needTransliterate)
        {
            return Transliterator.transliterate(firstNames.get(random.nextInt(firstNames.size())));
        }
        else
        {
            return firstNames.get(random.nextInt(firstNames.size()));
        }
    }

    /**
     * Получение рандомной фамилии
     *
     * @return рандомная фамилия
     */
    public String getLastName(boolean needTransliterate)
    {
        if (needTransliterate)
        {
            return Transliterator.transliterate(lastNames.get(random.nextInt(lastNames.size())));
        }
        else
        {
            return lastNames.get(random.nextInt(lastNames.size()));
        }
    }

    /**
     * Получение рандомного отчества
     *
     * @return рандомное отчество
     */
    public String getPatronymic(boolean needTransliterate)
    {
        if (needTransliterate)
        {
            return Transliterator.transliterate(patronymics.get(random.nextInt(patronymics.size())));
        }
        else
        {
            return patronymics.get(random.nextInt(patronymics.size()));
        }
    }

    /**
     * Генерация рандомного ФИО
     *
     * @param needTransliterate признак необходимости вывода ФИО в виде транслитерации
     * @return рандомное ФИО
     */
    String getRandomFullName(boolean needTransliterate)
    {
        String fullName = String.format("%s %s %s", getLastName(needTransliterate), getFirstName(needTransliterate), getPatronymic(needTransliterate));

        return needTransliterate ? Transliterator.transliterate(fullName): fullName;
    }

}
