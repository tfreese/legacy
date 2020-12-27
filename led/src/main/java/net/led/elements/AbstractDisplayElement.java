package net.led.elements;

/**
 * Abstract implementation of the {@overload.ledticker.elements.TickerElement } interface
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
     * @param tokens {@link Token}[]
     */
    protected AbstractDisplayElement(final Token[] tokens)
    {
        super();

        if (tokens == null)
        {
            throw new IllegalArgumentException("tokens array is null");
        }

        this.tokens = tokens;
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