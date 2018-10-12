package net.led.demo.elements;

import java.awt.Color;
import net.led.demo.elements.display.AbstractDisplayElement;
import net.led.demo.tokens.DateToken;
import net.led.demo.tokens.TextToken;
import net.led.demo.tokens.TimeToken;
import net.led.elements.DefaultColorModel;
import net.led.elements.Token;

/**
 * @author Thomas Freese
 */
public class TimeDisplayElement extends AbstractDisplayElement
{
    /**
     *
     */
    private DateToken date;

    /**
     *
     */
    private TextToken text;

    /**
     *
     */
    private TimeToken time;

    /**
     * Erzeugt ein Anzeigelement mit dem Uebergebenen Namen.
     *
     * @param attributName String
     */
    public TimeDisplayElement(final String attributName)
    {
        super(new Token[3]);

        this.text = new TextToken(attributName, new DefaultColorModel(new Color(0xffffff)));
        this.date = new DateToken();
        this.time = new TimeToken();

        this.tokens[0] = this.text;
        this.tokens[1] = this.date;
        this.tokens[2] = this.time;
    }

    /**
     * @param newValue Object
     */
    public void setTime(final Object newValue)
    {
        this.date.setValue(newValue);
        this.time.setValue(newValue);
    }
}