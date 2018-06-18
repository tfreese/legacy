package net.led.elements;

import java.awt.Color;

/**
 * @author Thomas Freese
 */
public interface ColorModel
{
    /**
     * @return {@link Color}
     */
    public abstract Color getColor();

    /**
     * @param color {@link Color}
     */
    public abstract void setColor(Color color);
}
