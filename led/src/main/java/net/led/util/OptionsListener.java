package net.led.util;

/**
 * @author Thomas Freese
 */
public interface OptionsListener
{
    /**
     * @param symbol String
     */
    public void addSymbol(String symbol);

    /**
     * @param symbol String
     */
    public void removeSymbol(String symbol);
}