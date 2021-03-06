package facade;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Thomas Freese
 */
public class DataCreator
{
    /**
     *
     */
    private static final String COUNTRY_LABEL = "country";

    /**
     *
     */
    private static final String CURRENCY_LABEL = "currency";

    /**
     *
     */
    private static final String EXIT_CAPTION = "exit";

    /**
     *
     */
    private static final String GUI_TITLE = "title";

    /**
     *
     */
    private static final String PHONE_LABEL = "phone";

    /**
     *
     */
    private static void saveFrData()
    {
        try (OutputStream os = new FileOutputStream("french.properties"))
        {
            Properties textSettings = new Properties();

            textSettings.setProperty(GUI_TITLE, "Demonstration du Pattern Facade");
            textSettings.setProperty(EXIT_CAPTION, "Sortir");
            textSettings.setProperty(COUNTRY_LABEL, "Pays");
            textSettings.setProperty(CURRENCY_LABEL, "Monnaie");
            textSettings.setProperty(PHONE_LABEL, "Numero de Telephone");

            textSettings.store(os, "French Settings");
        }
        catch (IOException exc)
        {
            System.err.println("Error storing settings to output");
            exc.printStackTrace();
        }
    }

    /**
     *
     */
    private static void saveNlData()
    {
        try (OutputStream os = new FileOutputStream("dutch.properties"))
        {
            Properties textSettings = new Properties();

            textSettings.setProperty(GUI_TITLE, "Facade Pattern voorbeeld");
            textSettings.setProperty(EXIT_CAPTION, "Exit");
            textSettings.setProperty(COUNTRY_LABEL, "Land");
            textSettings.setProperty(CURRENCY_LABEL, "Munt eenheid");
            textSettings.setProperty(PHONE_LABEL, "Telefoonnummer");
            textSettings.store(os, "Dutch Settings");
        }
        catch (IOException exc)
        {
            System.err.println("Error storing settings to output");
            exc.printStackTrace();
        }
    }

    /**
     *
     */
    private static void saveUsData()
    {
        try (OutputStream os = new FileOutputStream("us.properties"))
        {
            Properties textSettings = new Properties();

            textSettings.setProperty(GUI_TITLE, "Facade Pattern Demonstration");
            textSettings.setProperty(EXIT_CAPTION, "Exit");
            textSettings.setProperty(COUNTRY_LABEL, "Country");
            textSettings.setProperty(CURRENCY_LABEL, "Currency");
            textSettings.setProperty(PHONE_LABEL, "Phone Number");
            textSettings.store(os, "US Settings");
        }
        catch (IOException exc)
        {
            System.err.println("Error storing settings to output");
            exc.printStackTrace();
        }
    }

    /**
     * @param fileName String
     */
    public static void serialize(final String fileName)
    {
        saveFrData();
        saveUsData();
        saveNlData();
    }
}
