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
     * @param hGap int
     * @param vGap int
     */
    public void setDotGaps(int hGap, int vGap);

    /**
     * @param color {@link Color}
     */
    public void setDotOffColor(Color color);

    /**
     * @param dotWidth int
     * @param dotHeight int
     */
    public void setDotSize(int dotWidth, int dotHeight);

    /**
     * @param elementGap int
     */
    public void setElementGap(int elementGap);

    /**
     * @param speed int
     */
    public void setSpeed(int speed);

    /**
     * @param tokenGap int
     */
    public void setTokenGap(int tokenGap);

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
