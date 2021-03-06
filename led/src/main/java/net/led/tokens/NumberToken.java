package net.led.tokens;

import java.text.NumberFormat;
import java.util.Locale;
import net.led.elements.ColorModel;

/**
 * A token representing a number
 *
 * @version 1.0 12/14/04
 */
public class NumberToken extends Token
{
    /**
     * The representation of the number.
     */
    private String displayValue;

    /**
     * The number representation's format
     */
    private NumberFormat numberFormat;

    /**
     * The represented value
     */
    protected double value;

    /**
     * Creates a <tt>NumberToken</tt> with a default <tt>NumberFormat</tt> using the <tt>ENGLISH</tt> <tt>Locale</tt> and a two fraction digit representation
     */
    public NumberToken()
    {
        this((NumberFormat) null);
    }

    /**
     * Creates a <tt>NumberToken</tt> with a specific ColorModel
     *
     * @param colorModel the ColorModel
     */
    public NumberToken(final ColorModel colorModel)
    {
        this();

        setColorModel(colorModel);
    }

    /**
     * Creates a <tt>NumberToken</tt> with the given <tt>NumberFormat</tt>
     *
     * @param numberFormat the representation's number format
     */
    public NumberToken(final NumberFormat numberFormat)
    {
        this.value = Double.NaN;

        setNumberFormat(numberFormat);
    }

    /**
     * Creates a <tt>NumberToken</tt> with the given <tt>NumberFormat</tt> and a specific ColorModel
     *
     * @param numberFormat the representation's number format
     * @param colorModel the ColorModel
     */
    public NumberToken(final NumberFormat numberFormat, final ColorModel colorModel)
    {
        this(numberFormat);

        setColorModel(colorModel);
    }

    /**
     * Formats the token's display value
     */
    private void formatDisplayValue()
    {
        if (Double.isNaN(this.value) || Double.isInfinite(this.value))
        {
            this.displayValue = "N/A";
        }
        else
        {
            this.displayValue = this.numberFormat.format(this.value);
        }
    }

    /**
     * Gets the default <tt>NumberFormat</tt>
     *
     * @return the default <tt>NumberFormat</tt>
     */
    private NumberFormat getDefaultNumberFormat()
    {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        return nf;
    }

    /**
     * Gets the representation of the number
     *
     * @see net.led.tokens.Token#getDisplayValue()
     */
    @Override
    public String getDisplayValue()
    {
        return this.displayValue;
    }

    /**
     * Sets the <tt>NumberFormat</tt> used to represent the value. If the given value is <tt>null</tt> a default <tt>NumberFormat</tt> is assigned, using the
     * <tt>ENGLISH</tt> <tt>Locale</tt> and a two fraction digit representation
     *
     * @param newValue the new <tt>NumberFormat</tt>
     */
    public void setNumberFormat(NumberFormat newValue)
    {
        if (newValue == null)
        {
            newValue = getDefaultNumberFormat();
        }

        this.numberFormat = newValue;
        formatDisplayValue();
    }

    /**
     * Sets the value of the token
     *
     * @see net.led.tokens.Token#setValue(java.lang.Object)
     * @throws IllegalArgumentException if the given value is not a <tt>Number</tt>
     */
    @Override
    public void setValue(final Object newValue)
    {
        if (newValue instanceof Number)
        {
            this.value = ((Number) newValue).doubleValue();
            formatDisplayValue();

            return;
        }

        throw new IllegalArgumentException("Given value must be a java.lang.Number, not " + newValue.getClass().getName());
    }
}