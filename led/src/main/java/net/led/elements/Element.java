package net.led.elements;

/**
 * @author Thomas Freese
 */
@FunctionalInterface
public interface Element
{
    /**
     * @return {@link Token}[]
     */
    public abstract Token[] getTokens();
}
