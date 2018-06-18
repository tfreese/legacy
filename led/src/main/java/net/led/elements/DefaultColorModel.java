package net.led.elements;

import java.awt.Color;

/**
 * @author Thomas Freese
 */
public class DefaultColorModel implements ColorModel
{
    /**
     * 
     */
    private Color b = null;

    /**
     * Erstellt ein neues {@link DefaultColorModel} Object.
     */
    public DefaultColorModel()
    {
        this(Color.white);
    }

    /**
     * Erstellt ein neues {@link DefaultColorModel} Object.
     *
     * @param color {@link Color}
     */
    public DefaultColorModel(final Color color)
    {
        super();

        this.b = color;
    }

    /**
     * @see net.led.elements.ColorModel#getColor()
     */
    @Override
    public Color getColor()
    {
        return this.b;
    }

    /**
     * @see net.led.elements.ColorModel#setColor(java.awt.Color)
     */
    @Override
    public void setColor(final Color color)
    {
        this.b = color;
    }
}
