package net.ledticker;

/**
 * @author Thomas Freese
 */
public class LedTickerFactory
{
    /**
     * @return {@link LedTicker}
     */
    public static LedTicker createLedTicker()
    {
        return new DefaultLedTicker();
    }

    /**
     * Erstellt ein neues {@link LedTickerFactory} Object.
     */
    public LedTickerFactory()
    {
        super();
    }
}
