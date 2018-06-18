package net.led.demo.elements;

import java.awt.Color;
import net.led.elements.ColorModel;

/**
 * @author Thomas Freese
 */
public class StockColorModel implements ColorModel
{
    /**
     *
     */
    private Color color;

    /**
     *
     */
    private Color downColor;

    /**
     *
     */
    private Color neutralColor;

    /**
     *
     */
    private Color upColor;

    /**
     * Erstellt ein neues {@link StockColorModel} Object.
     */
    public StockColorModel()
    {
        super();

        this.upColor = Color.green;
        this.neutralColor = Color.yellow;
        this.downColor = Color.red;
        this.color = this.neutralColor;
    }

    /**
     * @see net.led.elements.ColorModel#getColor()
     */
    @Override
    public Color getColor()
    {
        return this.color;
    }

    /**
     * @param changePercent double
     */
    public void setChangePercent(final double changePercent)
    {
        if (changePercent > 0)
        {
            this.color = this.upColor;
        }
        else if (changePercent < 0)
        {
            this.color = this.downColor;
        }
        else
        {
            this.color = this.neutralColor;
        }
    }

    /**
     * @see net.led.elements.ColorModel#setColor(java.awt.Color)
     */
    @Override
    public void setColor(final Color color)
    {
        // Empty
    }

    /**
     * @param c {@link Color}
     */
    public void setDownColor(final Color c)
    {
        if (this.color.equals(this.downColor))
        {
            this.color = c;
        }

        this.downColor = c;
    }

    /**
     * @param c {@link Color}
     */
    public void setNeutralColor(final Color c)
    {
        if (this.color.equals(this.neutralColor))
        {
            this.color = c;
        }

        this.neutralColor = c;
    }

    /**
     * @param c {@link Color}
     */
    public void setUpColor(final Color c)
    {
        if (this.color.equals(this.upColor))
        {
            this.color = c;
        }

        this.upColor = c;
    }
}