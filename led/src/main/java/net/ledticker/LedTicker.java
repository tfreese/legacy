package net.ledticker;

import java.awt.Color;
import javax.swing.JComponent;
import net.led.elements.Element;

/**
 * @author Thomas Freese
 */
public interface LedTicker
{
    /**
     * @param tickerelement {@link Element}
     */
    public void addElement(Element tickerelement);

    /**
     * @return {@link JComponent}
     */
    public JComponent getTickerComponent();

    /**
     * 
     */
    public void pauseAnimation();

    /**
     * 
     */
    public void removeAll();

    /**
     * @param tickerelement {@link Element}
     */
    public void removeElement(Element tickerelement);

    /**
     * @param color {@link Color}
     */
    public void setBackgroundColor(Color color);

    /**
     * @param i int
     * @param j int
     */
    public void setDotGaps(int i, int j);

    /**
     * @param color {@link Color}
     */
    public void setDotOffColor(Color color);

    /**
     * @param i int
     * @param j int
     */
    public void setDotSize(int i, int j);

    /**
     * @param i int
     */
    public void setElementGap(int i);

    /**
     * @param i int
     */
    public void setSpeed(int i);

    /**
     * @param i int
     */
    public void setTokenGap(int i);

    /**
     * 
     */
    public void startAnimation();

    /**
     * 
     */
    public void stopAnimation();

    /**
     * @param tickerelement {@link Element}
     */
    public void update(Element tickerelement);

    /**
     * 
     */
    public void updateAll();
}
