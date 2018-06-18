package net.led.elements;

/**
 * @author Thomas Freese
 */
public final class ArrowToken extends Token
{
    /**
     *
     */
    public static final Object DECREASING = new Object();

    /**
     *
     */
    public static final Object INCREASING = new Object();

    /**
     *
     */
    public static final Object UNCHANGED = new Object();

    /**
     *
     */
    private Object g = null;

    /**
     * Erstellt ein neues {@link ArrowToken} Object.
     */
    public ArrowToken()
    {
        super();

        setValue(UNCHANGED);
    }

    /**
     * Erstellt ein neues {@link ArrowToken} Object.
     *
     * @param colormodel {@link ColorModel}
     */
    public ArrowToken(final ColorModel colormodel)
    {
        this();

        setColorModel(colormodel);
    }

    /**
     * @return Object
     */
    public Object getArrowType()
    {
        return this.g;
    }

    /**
     * @see net.led.elements.Token#getDisplayValue()
     */
    @Override
    public String getDisplayValue()
    {
        return "";
    }

    /**
     * @see net.led.elements.Token#setValue(java.lang.Object)
     */
    @Override
    public void setValue(final Object obj)
    {
        this.g = obj;
    }
}
