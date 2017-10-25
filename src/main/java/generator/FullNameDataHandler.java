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
class FullNameDataHandler implements AutoCloseable
{
    private final static String FULL_NAMES_FILE_NAME = "full-names.txt";

    private List<String> firstNames;
    private List<String> lastNames;
    private List<String> patronymics;

    private Random random = new Random();

    FullNameDataHandler() throws IOException
    {
        firstNames = new ArrayList<>();
        lastNames = new ArrayList<>();
        patronymics = new ArrayList<>();
        new Reader(FULL_NAMES_FILE_NAME).fillFullNames(firstNames, lastNames, patronymics);
    }

    /**
     * Получение рандомного имени
     *
     * @return рандомное имя
     */
    private String getFirstName()
    {
        return firstNames.get(random.nextInt(firstNames.size()));
    }

    /**
     * Получение рандомной фамилии
     *
     * @return рандомная фамилия
     */
    private String getLastName()
    {
        return lastNames.get(random.nextInt(lastNames.size()));
    }

    /**
     * Получение рандомного отчества
     *
     * @return рандомное отчество
     */
    private String getPatronymics()
    {
        return patronymics.get(random.nextInt(patronymics.size()));
    }

    /**
     * Генерация рандомного ФИО
     *
     * @param needTransliterate признак необходимости вывода ФИО в виде транслитерации
     * @return рандомное ФИО
     */
    String getRandomFullName(boolean needTransliterate)
    {
        String fullName = String.format("%s %s %s", getLastName(), getFirstName(), getPatronymics());

        return needTransliterate ? Transliterator.transliterate(fullName): fullName;
    }

    @Override
    public void close() throws Exception
    {
        firstNames = null;
        lastNames = null;
        patronymics = null;
    }
}
