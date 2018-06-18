package net.led.demo.util;

import java.awt.Color;

/**
 * @author Thomas Freese
 */
public interface ColorSelectorListener
{
    /**
     * @param id String
     * @param color {@link Color}
     */
    public void setColor(String id, Color color);
}