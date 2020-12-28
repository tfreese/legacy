package net.ledticker;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import net.led.elements.Element;
import net.led.tokens.TextToken;
import net.led.tokens.Token;

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
        ImageProvider imageProvider = new ImageProvider(tickerelement, this.matrix, this.ledPanel);
        this.elements.add(imageProvider);
        this.ledPanel.repaint(imageProvider.getImage(), imageProvider.getObject());
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
            ImageProvider imageProvider = this.elements.get(i);

            if (imageProvider.getElement() != tickerelement)
            {
                continue;
            }

            this.ledPanel.b(imageProvider.getObject());
            this.elements.remove(imageProvider);

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
    public void setDotGaps(final int hGap, final int vGap)
    {
        this.matrix.setDotGaps(hGap, vGap);
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
    public void setDotSize(final int dotWidth, final int dotHeight)
    {
        this.matrix.setDotSize(dotWidth, dotHeight);
        this.ledPanel.setHeight(this.matrix.getHeigth());

        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#setElementGap(int)
     */
    @Override
    public void setElementGap(final int elementGap)
    {
        this.matrix.setElementGap(elementGap);

        updateAll();
    }

    /**
     * @see net.ledticker.LedTicker#setSpeed(int)
     */
    @Override
    public void setSpeed(final int speed)
    {
        if ((speed >= 1) && (speed <= 10))
        {
            this.ledPanel.setSpeed(13 - speed);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported speed (" + speed + "). Speed must be between 1 and 10.");
        }
    }

    /**
     * @see net.ledticker.LedTicker#setTokenGap(int)
     */
    @Override
    public void setTokenGap(final int tokenGap)
    {
        this.matrix.setTokenGap(tokenGap);

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
        for (ImageProvider imageProvider : this.elements)
        {
            if (imageProvider.getElement() == tickerelement)
            {
                imageProvider.createImage();
                this.ledPanel.repaint(imageProvider.getImage(), imageProvider.getObject());

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

        for (ImageProvider imageProvider : this.elements)
        {
            imageProvider.createImage();
            this.ledPanel.repaint(imageProvider.getImage(), imageProvider.getObject());
        }

        this.ledPanel.b(false);
    }
}
