package net.leddisplay;

/**
 * @author Thomas Freese
 */
public class LedDisplayFactory
{
    /**
     * @return {@link LedDisplay}
     */
    public static LedDisplay createLedDisplay()
    {
        return new DefaultLedDisplay();
    }

    /**
     * Erstellt ein neues {@link LedDisplayFactory} Object.
     */
    public LedDisplayFactory()
    {
        super();
    }
}
