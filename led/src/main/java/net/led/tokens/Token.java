package net.led.tokens;

import net.led.elements.ColorModel;
import net.led.elements.DefaultColorModel;

/**
 * @author Thomas Freese
 */
public abstract class Token
{
    /**
     *
     */
    private ColorModel colorModel;

    /**
     * Erstellt ein neues {@link Token} Object.
     */
    public Token()
    {
        this(new DefaultColorModel());
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
        return this.colorModel;
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

        this.colorModel = colormodel;
    }

    /**
     * @param obj Object
     */
    public abstract void setValue(Object obj);
}
