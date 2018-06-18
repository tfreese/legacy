package net.led.demo.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Thomas Freese
 */
public class TimeProvider implements Runnable
{
    /**
     *
     */
    private Thread feedThread;

    /**
     *
     */
    private List<UpdateListener> listeners = new ArrayList<>();

    /**
     *
     */
    private List<String> symbols = new ArrayList<>();

    /**
     * Erstellt ein neues {@link TimeProvider} Object.
     */
    public TimeProvider()
    {
        super();
    }

    /**
     * Adds a symbol to be updated.
     *
     * @param symbol String
     */
    public void addSymbol(final String symbol)
    {
        synchronized (this.symbols)
        {
            this.symbols.add(symbol);
        }
    }

    /**
     * @param listener {@link UpdateListener}
     */
    public void addUpdateListener(final UpdateListener listener)
    {
        synchronized (this.listeners)
        {
            if (!this.listeners.contains(listener))
            {
                this.listeners.add(listener);
            }
        }
    }

    /**
     * Reads data from Yahoo! for each symbol.
     *
     * @param symbol String
     */
    private void readSymbolData(final String symbol)
    {
        sendTime(new Date());
    }

    /**
     * Removes all symbols.
     */
    public void removeAllElements()
    {
        synchronized (this.symbols)
        {
            this.symbols.clear();
        }
    }

    /**
     * Removes a specific symbol from the provider's list of symbols to updated.
     *
     * @param symbol String
     */
    public void removeSymbol(final String symbol)
    {
        synchronized (this.symbols)
        {
            this.symbols.remove(symbol);
        }
    }

    /**
     * @param listener {@link UpdateListener}
     */
    public void removeUpdateListener(final UpdateListener listener)
    {
        synchronized (this.listeners)
        {
            this.listeners.remove(listener);
        }
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        Thread currentThread = Thread.currentThread();

        int index = 0;
        String symbol;
        long time;

        while (currentThread == this.feedThread)
        {
            time = System.currentTimeMillis();

            synchronized (this.symbols)
            {
                if (index >= this.symbols.size())
                {
                    index = 0;
                }

                if (this.symbols.isEmpty())
                {
                    symbol = null;
                }
                else
                {
                    symbol = this.symbols.get(index++);
                }
            }

            if (symbol != null)
            {
                readSymbolData(symbol);
            }

            time = 1000 - (System.currentTimeMillis() - time);

            if (time > 10)
            {
                try
                {
                    Thread.sleep(time);
                }
                catch (InterruptedException e1)
                {
                    // Ignore
                }
            }
        }
    }

    /**
     * @param newValue {@link Date}
     */
    private void sendTime(final Date newValue)
    {
        synchronized (this.listeners)
        {
            for (Object element : this.listeners)
            {
                ((UpdateListener) element).update(newValue);
            }
        }
    }

    /**
     *
     */
    public void start()
    {
        this.feedThread = new Thread(this, "Time Provider");
        this.feedThread.start();
    }

    /**
     *
     */
    public void stop()
    {
        Thread t = this.feedThread;
        this.feedThread = null;

        if (t != null)
        {
            t.interrupt();
        }
    }
}
