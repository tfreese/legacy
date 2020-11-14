package net.leddisplay;

/**
 * @author Thomas Freese
 */
public final class LedDisplayFactory
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
    private LedDisplayFactory()
    {
        super();
    }
}
