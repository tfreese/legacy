package net.leddisplay;

import java.awt.Color;
import javax.swing.JComponent;
import net.led.elements.Element;

/**
 * @author Thomas Freese
 */
public class DefaultLedDisplay implements LedDisplay
{
    /**
     *
     */
    private LedPanel ledPanel;

    /**
     *
     */
    private Matrix matrix;

    /**
     * Erstellt ein neues {@link DefaultLedDisplay} Object.
     */
    public DefaultLedDisplay()
    {
        super();

        this.matrix = new Matrix();
        this.ledPanel = new LedPanel(this.matrix);
    }

    /**
     * @see net.leddisplay.LedDisplay#getComponent()
     */
    @Override
    public JComponent getComponent()
    {
        return this.ledPanel;
    }

    /**
     * @see net.leddisplay.LedDisplay#setAnchor(int)
     */
    @Override
    public void setAnchor(final int i)
    {
        this.matrix.setAnchor((i >= 9) ? (i % 9) : i);
        update();
    }

    /**
     * @see net.leddisplay.LedDisplay#setBackgroundColor(java.awt.Color)
     */
    @Override
    public void setBackgroundColor(final Color color)
    {
        this.matrix.setBackgroundColor(color);
        update();
    }

    /**
     * @see net.leddisplay.LedDisplay#setDisplayElement(net.led.elements.Element)
     */
    @Override
    public void setDisplayElement(final Element element)
    {
        this.ledPanel.setDisplayElement(element);
    }

    /**
     * @see net.leddisplay.LedDisplay#setDotGaps(int, int)
     */
    @Override
    public void setDotGaps(final int i, final int j)
    {
        this.matrix.setDotGaps(i, j);
        this.ledPanel.setHeight(this.matrix.getHeigth());
        update();
    }

    /**
     * @see net.leddisplay.LedDisplay#setDotOffColor(java.awt.Color)
     */
    @Override
    public void setDotOffColor(final Color color)
    {
        this.matrix.setDotOffColor(color);
        update();
    }

    /**
     * @see net.leddisplay.LedDisplay#setDotSize(int, int)
     */
    @Override
    public void setDotSize(final int width, final int height)
    {
        this.matrix.setDotSize(width, height);
        this.ledPanel.setHeight(this.matrix.getHeigth());
        update();
    }

    /**
     * @see net.leddisplay.LedDisplay#setPadding(int, int, int, int)
     */
    @Override
    public void setPadding(final int top, final int left, final int bottom, final int right)
    {
        this.matrix.setPadding(top, left, bottom, right);
        update();
    }

    /**
     * @see net.leddisplay.LedDisplay#setTokenGap(int)
     */
    @Override
    public void setTokenGap(final int gap)
    {
        this.matrix.setTokenGap(gap);
        update();
    }

    /**
     * @see net.leddisplay.LedDisplay#update()
     */
    @Override
    public void update()
    {
        this.ledPanel.repaint();
    }
}
