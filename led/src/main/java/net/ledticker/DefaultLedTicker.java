package net.ledticker;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import net.led.demo.tokens.TextToken;
import net.led.elements.Element;
import net.led.elements.Token;

/**
 * @author Thomas Freese
 */
public class DefaultLedTicker implements LedTicker
{
    /**
     * @author Thomas Freese
     */
    private static class DefaultElement implements Element
    {
        /**
         *
         */
        protected Token[] array;

        /**
         * Erstellt ein neues {@link DefaultElement} Object.
         */
        public DefaultElement()
        {
            super();

            TextToken texttoken = new TextToken("WWW.LEDTICKER.NET::");

            this.array = (new Token[]
            {
                    texttoken
            });
        }

        /**
         * @see net.led.elements.Element#getTokens()
         */
        @Override
        public Token[] getTokens()
        {
            return this.array;
        }

        /**
         * @param newValue {@link Color}
         */
        public void setDotOffColor(final Color newValue)
        {
            Color color = new Color(newValue.getRGB() ^ 16777215);
            this.array[0].getColorModel().setColor(color);
        }
    }

    /**
     *
     */
    private List<ImageProvider> elements;

    /**
     *
     */
    private DefaultElement h;

    /**
     *
     */
    private LedPanel ledPanel;

    /**
     *
     */
    private Matrix matrix;

    /**
     * Erstellt ein neues {@link DefaultLedTicker} Object.
     */
    public DefaultLedTicker()
    {
        super();

        this.h = new DefaultElement();
        this.elements = new ArrayList<>();
        this.matrix = new Matrix();
        this.ledPanel = new LedPanel();
        this.ledPanel.setHeight(this.matrix.getHeigth());

        addElement(this.h);
    }

    /**
     * @see net.ledticker.LedTicker#addElement(net.led.elements.Element)
     */
    @Override
    public void addElement(final Element tickerelement)
    {
        ImageProvider c1 = new ImageProvider(tickerelement, this.matrix, this.ledPanel);
        this.elements.add(c1);
        this.ledPanel.b(c1.getImage(), c1.getObject());
    }

    /**
     * @see net.ledticker.LedTicker#getTickerComponent()
     */
    @Override
    public JComponent getTickerComponent()
    {
        return this.ledPanel;
    }

    /**
     * @see net.ledticker.LedTicker#pauseAnimation()
     */
    @Override
    public void pauseAnimation()
    {
        this.ledPanel.pauseAnimation();
    }

    /**
     * @see net.ledticker.LedTicker#removeAll()
     */
    @Override
    public void removeAll()
    {
        this.elements.clear();
        this.ledPanel.g();

        addElement(this.h);
    }

    /**
     * @see net.ledticker.LedTicker#removeElement(net.led.elements.Element)
     */
    @Override
    public void removeElement(final Element tickerelement)
    {
        for (int i = 0; i < this.elements.size(); i++)
        {
            ImageProvider c1 = this.elements.get(i);

            if (c1.getElement() != tickerelement)
            {
                continue;
            }

            this.ledPanel.b(c1.getObject());
            this.elements.remove(c1);

            break;
        }
    }

    /**
     * @see net.ledticker.LedTicker#setBackgroundColor(java.awt.Color)
     */
    @Override
    public void setBackgroundColor(final Color color)
    {
        this.matrix.setBackgroundColor(color);

        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#setDotGaps(int, int)
     */
    @Override
    public void setDotGaps(final int i, final int j)
    {
        this.matrix.setDotGaps(i, j);
        this.ledPanel.setHeight(this.matrix.getHeigth());
        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#setDotOffColor(Color)
     */
    @Override
    public void setDotOffColor(final Color color)
    {
        this.h.setDotOffColor(color);
        this.matrix.setDotOffColor(color);

        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#setDotSize(int, int)
     */
    @Override
    public void setDotSize(final int i, final int j)
    {
        this.matrix.setDotSize(i, j);
        this.ledPanel.setHeight(this.matrix.getHeigth());

        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#setElementGap(int)
     */
    @Override
    public void setElementGap(final int i)
    {
        this.matrix.setElementGap(i);

        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#setSpeed(int)
     */
    @Override
    public void setSpeed(final int i)
    {
        if ((i >= 1) && (i <= 10))
        {
            this.ledPanel.setSpeed(13 - i);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported speed (" + i + "). Speed must be between 1 and 10.");
        }
    }

    /**
     * @see net.ledticker.LedTicker#setTokenGap(int)
     */
    @Override
    public void setTokenGap(final int i)
    {
        this.matrix.setTokenGap(i);

        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#startAnimation()
     */
    @Override
    public void startAnimation()
    {
        this.ledPanel.startAnimation();
    }

    /**
     * @see net.ledticker.LedTicker#stopAnimation()
     */
    @Override
    public void stopAnimation()
    {
        this.ledPanel.stopAnimation();
    }

    /**
     * @see net.ledticker.LedTicker#update(net.led.elements.Element)
     */
    @Override
    public void update(final Element tickerelement)
    {
        for (ImageProvider c1 : this.elements)
        {
            if (c1.getElement() == tickerelement)
            {
                c1.createImage();
                this.ledPanel.b(c1.getImage(), c1.getObject());

                return;
            }
        }

        throw new IllegalArgumentException("Updated element was not found in the Ticker's element list.");
    }

    /**
     * @see net.ledticker.LedTicker#updateAll()
     */
    @Override
    public void updateAll()
    {
        this.ledPanel.b(true);
        this.ledPanel.b(this.matrix.getImage());

        for (ImageProvider c1 : this.elements)
        {
            c1.createImage();
            this.ledPanel.b(c1.getImage(), c1.getObject());
        }

        this.ledPanel.b(false);
    }
}
