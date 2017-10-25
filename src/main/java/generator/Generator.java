package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.stream.IntStream;


/**
 * Класс генератор получающий рандомные ФИО и выполняющий их сохранение
 *
 * @author rassoll
 * @created 20.10.2017
 * @$Author$
 * @$Revision$
 */
public class Generator
{
    private int generatorSize;

    public Generator(int generatorSize)
    {
        this.generatorSize = generatorSize;
    }

    /**
     * Генерация случайных ФИО
     *
     * @param needTransliterate признак необходимости выполнения транслитерации
     * @return заполненный контейнер
     */
    public Map<Integer, UserAccount> generateMap(boolean needTransliterate)
    {
        Map<Integer, UserAccount> map = new HashMap<>();

        try (FullNameDataHandler handler = new FullNameDataHandler())
        {
            IntStream.range(0, generatorSize)
                    .forEach(key -> map.put(key, new UserAccount(
                            handler.getFirstName(needTransliterate),
                            handler.getLastName(needTransliterate),
                            handler.getPatronymic(needTransliterate))));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Генерация случайных ФИО с выводом в указанный источник
     *
     * @param needTransliterate признак необходимости выполнения транслитерации
     * @param outputDirection источник вывода информации
     * @throws UnsupportedOperationException в случае некорректного источника для вывода
     */
    public void generate(boolean needTransliterate, String outputDirection) throws UnsupportedOperationException
    {
        switch (outputDirection)
        {
            case "file":
            {
                Path path = Paths.get(new File("output.txt").getAbsolutePath());
                try (BufferedWriter writer = Files.newBufferedWriter(path))
                {
                    Map<Integer, UserAccount> map = generateMap(needTransliterate);
                    map.forEach((k, v) ->
                    {
                        try
                        {
                            writer.write(v.toString());
                            writer.newLine();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }

                break;
            }
            case "imdg":
            {
                throw new UnsupportedOperationException();
            }
            default:
            {
                throw new UnsupportedOperationException();
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        try
        {
            if (args.length != 3)
            {
                throw new InputMismatchException();
            }
            else
            {
                int generatorSize = Integer.parseInt(args[0]);
                boolean needTransliterate = Boolean.parseBoolean(args[1]);
                String outputDirection = args[2];

                Generator generator = new Generator(generatorSize);
                generator.generate(needTransliterate, outputDirection);
            }
        }
        catch (InputMismatchException | UnsupportedOperationException | NumberFormatException e)
        {
            System.out.println("Wrong data format\n (generatorSize, needTransliterate, outputDirection)");
        }
        catch (Exception e)
        {
            System.out.println("Возникла ошибка " + e.getMessage());
        }
    }

}