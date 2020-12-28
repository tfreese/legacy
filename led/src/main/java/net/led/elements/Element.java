package net.led.elements;

import net.led.tokens.Token;

/**
 * @author Thomas Freese
 */
@FunctionalInterface
public interface Element
{
    /**
     * @return {@link Token}[]
     */
    public Token[] getTokens();
}
