package generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Класс ридер файлов для генератора
 *
 * @author rassoll
 * @created 23.10.2017
 * @$Author$
 * @$Revision$
 */
class Reader
{
    private String filePath;

    Reader(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     * Читаем файл
     * @return буферизируемый символьный поток
     * @throws IOException в случае если файл не найден
     * @throws URISyntaxException в случае если преобразование URL -> URI не удалось
     */
    private BufferedReader read() throws IOException, URISyntaxException
    {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

        return new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
    }


    /**
     * Заполняет переданные по ссылке массивы данным прочитанными из потока
     *
     * @param firstNames ссылка на массив с именами
     * @param lastNames ссылка на массив с фамилиями
     * @param patronymics ссылка на массив с отчествами
     */
    void fillFullNames(List<String> firstNames, List<String> lastNames, List<String> patronymics)
    {
        try
        {
            BufferedReader bufferedReader = read();

            String currentLine;
            int delCount = 0;
            while ((currentLine = bufferedReader.readLine()) != null)
            {
                if (currentLine.contains("-"))
                {
                    delCount++;
                }
                else
                {
                    if (delCount == 0)
                    {
                        firstNames.add(currentLine);
                    }
                    else if (delCount == 1)
                    {
                        lastNames.add(currentLine);
                    }
                    else
                    {
                        patronymics.add(currentLine);
                    }
                }
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
