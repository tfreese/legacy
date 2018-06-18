package net.led.demo.elements.display;

import net.led.elements.Element;
import net.led.elements.Token;

/**
 * Abstract implementation of the {@net.leddisplay.elements.DisplayElement
 *
 * } interface
 */
public abstract class AbstractDisplayElement implements Element
{

    /**
     * The element's tokens
     */
    protected Token[] tokens;

    /**
     * Creates an element without tokens
     * 
     * @param newValue {@link Token}[]
     */
    protected AbstractDisplayElement(final Token[] newValue)
    {
        super();

        if (newValue == null)
        {
            throw new IllegalArgumentException("tokens array is null");
        }

        this.tokens = newValue;
    }

    /**
     * @see net.led.elements.Element#getTokens()
     */
    @Override
    public Token[] getTokens()
    {
        return this.tokens;
    }
}