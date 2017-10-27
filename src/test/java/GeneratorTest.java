import generator.Generator;
import generator.Transliterator;
import generator.UserAccount;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author rassoll
 * @created 24.10.2017
 * @$Author$
 * @$Revision$
 */
public class GeneratorTest
{
    private int GENERATOR_SIZE = 10;
    private Generator generator;
    Map<Integer, UserAccount> map;

    @Before
    public void init()
    {
        generator = new Generator(GENERATOR_SIZE);
        map = generator.generateMap(false);
    }

    @Test
    public void checkGenerationSize()
    {
        assertEquals(GENERATOR_SIZE, map.size());
    }

    @Test
    public void checkDictionariesConsistency()
    {
        assertTrue(Transliterator.checkDictionariesConsistency());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTransliteratorHandlingInvalidInput()
    {
        Transliterator.transliterate(null);
    }

    @Test
    public void checkingTheOutputOfCyrillicAlphabet()
    {
        map.forEach((k,v) ->
        {
            assertTrue(Pattern.matches(".*\\p{InCyrillic}.*", v.getFirstName()));
            assertTrue(Pattern.matches(".*\\p{InCyrillic}.*", v.getLastName()));
            assertTrue(Pattern.matches(".*\\p{InCyrillic}.*", v.getPatronymic()));
        });
    }

    @Test
    public void checkingTheLatinOutput()
    {
        generator.generateMap(true).forEach((k,v) ->
                {
                    assertFalse(Pattern.matches(".*\\p{InCyrillic}.*", v.getFirstName()));
                    assertFalse(Pattern.matches(".*\\p{InCyrillic}.*", v.getLastName()));
                    assertFalse(Pattern.matches(".*\\p{InCyrillic}.*", v.getPatronymic()));
                }
        );
    }

    @Test
    public void checkingTheCorrectnessOfTransliteration()
    {
        assertEquals("dezoksiribonukleinovaja kislota", Transliterator.transliterate("дезоксирибонуклеиновая кислота"));
        assertEquals("rosglavstankoinstrumentsnabsbit", Transliterator.transliterate("росглавстанкоинструментснабсбыт"));
        assertEquals("korpuskuljarno volnovoy dualizm", Transliterator.transliterate("корпускулярно волновой дуализм"));
        assertEquals("sinhrofazatron", Transliterator.transliterate("синхрофазатрон"));
    }

    @Test
    public void checkFileCreation()
    {
        generator.generate(true, "file");
        File file = new File(Generator.getOutputFileName());
        //file.deleteOnExit();
        assertTrue(file.exists());
    }

}
