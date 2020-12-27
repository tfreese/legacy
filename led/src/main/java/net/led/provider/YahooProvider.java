package net.led.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Thomas Freese
 */
public class YahooProvider implements Runnable
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
     * Erstellt ein neues {@link YahooProvider} Object.
     */
    public YahooProvider()
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
     * @param s String
     * @return Double
     */
    private Double parseDouble(String s)
    {
        if (s.startsWith("\""))
        {
            s = s.substring(1, s.length() - 1);
        }

        if (s.endsWith("%"))
        {
            s = s.substring(0, s.length() - 1);
        }

        try
        {
            return Double.valueOf(s);
        }
        catch (NumberFormatException ex)
        {
            return null;
        }
    }

    /**
     * Reads data from Yahoo! for each symbol.
     *
     * @param symbol String
     */
    private void readSymbolData(final String symbol)
    {
        String feedURL = "http://finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl9p4&e=.csv";
        URL url = null;

        try
        {
            url = new URL(feedURL);
        }
        catch (MalformedURLException ex)
        {
            System.out.println("Unable to open connection !");
            return;
        }

        String line = null;
        StringTokenizer st;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)))
        {
            line = br.readLine();
        }
        catch (IOException ex)
        {
            // Empty
        }

        if (line != null)
        {
            st = new StringTokenizer(line, ",");

            if (st.hasMoreTokens())
            {
                String name = st.nextToken();

                if (name.startsWith("\""))
                {
                    name = name.substring(1, name.length() - 1);
                }

                if (!(name.equals(symbol)))
                {
                    return;
                }
            }
            else
            {
                return;
            }

            Double last = null;

            if (st.hasMoreTokens())
            {
                last = parseDouble(st.nextToken());
            }

            Double changePercent = null;

            if (st.hasMoreTokens())
            {
                changePercent = parseDouble(st.nextToken());

                // Sometimes the feed sends invalid data (like -9999.00)
                // for change percent
                if (Math.abs(changePercent.doubleValue()) > 50)
                {
                    changePercent = Double.NaN;
                }
            }

            if ((last != null) && (changePercent != null))
            {
                Stock stock = new Stock(symbol, last, changePercent);
                sendStock(stock);
            }
        }
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
                catch (InterruptedException ex)
                {
                    // Empty
                }
            }
        }
    }

    /**
     * @param stock {@link Stock}
     */
    private void sendStock(final Stock stock)
    {
        synchronized (this.listeners)
        {
            for (Object element : this.listeners)
            {
                ((UpdateListener) element).update(stock);
            }
        }
    }

    /**
     *
     */
    public void start()
    {
        this.feedThread = new Thread(this, "Yahoo Provider");
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