package net.leddisplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JPanel;
import net.led.elements.Element;

/**
 * @author Thomas Freese
 */
public class LedPanel extends JPanel
{
    /**
     *
     */
    private static final long serialVersionUID = 3000L;

    /**
     *
     */
    private Element displayElement = null;

    /**
     *
     */
    private int height = 0;

    /**
     *
     */
    private Matrix matrix = null;

    /**
     *
     */
    private Dimension preferredSize = null;

    /**
     * Erstellt ein neues {@link LedPanel} Object.
     *
     * @param b1 {@link Matrix}
     */
    public LedPanel(final Matrix b1)
    {
        super();

        this.matrix = b1;
        setBackground(null);
        setLayout(null);
        setDoubleBuffered(true);
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        Dimension dimension = super.getPreferredSize();

        if (this.preferredSize == null)
        {
            Insets insets = getInsets();
            dimension = new Dimension(dimension.width + 399, this.height + insets.top + insets.bottom);
        }

        return dimension;
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(final Graphics g1)
    {
        super.paintComponent(g1);
        this.matrix.b(g1, getWidth(), getHeight());

        if (this.displayElement == null)
        {
            return;
        }

        this.matrix.b(g1, this.displayElement, getWidth(), getHeight());

        return;
    }

    /**
     * @param newValue {@link Element}
     */
    public void setDisplayElement(final Element newValue)
    {
        this.displayElement = newValue;
        repaint();
    }

    /**
     * @param newValue int
     */
    public void setHeight(final int newValue)
    {
        this.height = newValue;
    }

    /**
     * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
     */
    @Override
    public void setPreferredSize(final Dimension dimension)
    {
        this.preferredSize = dimension;
        super.setPreferredSize(dimension);
    }
}
