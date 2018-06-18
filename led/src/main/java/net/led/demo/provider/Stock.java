package net.led.demo.provider;

/**
 * @author Thomas Freese
 */
public class Stock
{
    /**
     *
     */
    private Double changePercent;

    /**
     *
     */
    private String id;

    /**
     *
     */
    private Double last;

    /**
     * Erstellt ein neues {@link Stock} Object.
     *
     * @param id String
     * @param last Double
     * @param changePercent Double
     */
    public Stock(final String id, final Double last, final Double changePercent)
    {
        this.id = id;
        this.last = last;
        this.changePercent = changePercent;
    }

    /**
     * @return Double
     */
    public Double getChangePercent()
    {
        return this.changePercent;
    }

    /**
     * @return String
     */
    public String getID()
    {
        return this.id;
    }

    /**
     * @return Double
     */
    public Double getLast()
    {
        return this.last;
    }

    /**
     * @param newValue Double
     */
    public void setChangePercent(final Double newValue)
    {
        this.changePercent = newValue;
    }

    /**
     * @param newValue Double
     */
    public void setLast(final Double newValue)
    {
        this.last = newValue;
    }
}