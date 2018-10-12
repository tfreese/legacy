package net.led.elements;

/**
 * @author Thomas Freese
 */
public abstract class Token
{
    /**
     *
     */
    private ColorModel b = null;

    /**
     * Erstellt ein neues {@link Token} Object.
     */
    public Token()
    {
        this(((new DefaultColorModel())));
    }

    /**
     * Erstellt ein neues {@link Token} Object.
     *
     * @param colormodel {@link ColorModel}
     */
    public Token(final ColorModel colormodel)
    {
        setColorModel(colormodel);
    }

    /**
     * @return {@link ColorModel}
     */
    public ColorModel getColorModel()
    {
        return this.b;
    }

    /**
     * @return String
     */
    public abstract String getDisplayValue();

    /**
     * @param colormodel {@link ColorModel}
     */
    public void setColorModel(ColorModel colormodel)
    {
        if (colormodel == null)
        {
            colormodel = new DefaultColorModel();
        }

        this.b = colormodel;
    }

    /**
     * @param obj Object
     */
    public abstract void setValue(Object obj);
}