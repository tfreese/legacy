package net.ledticker;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 * @author Thomas Freese
 */
public class LedPanel extends JPanel implements Runnable
{
    // /**
    // *
    // */
    // private static final long d = 10000L;

    /**
     *
     */
    public static final byte j = -1;

    /**
     *
     */
    public static final byte l = 1;

    /**
     *
     */
    private static final long serialVersionUID = 7648903683131199825L;

    /**
     *
     */
    private Dimension dimension;

    /**
     *
     */
    protected byte e;

    /**
     *
     */
    private int height;

    /**
     *
     */
    private boolean i;

    /**
     *
     */
    private Image image;

    /**
     *
     */
    protected int k;

    /**
     *
     */
    private List<A> n;

    /**
     *
     */
    private Map<Object, A> o;

    /**
     *
     */
    private volatile boolean pause;

    /**
     *
     */
    protected int speed;

    /**
     *
     */
    protected volatile Thread thread;

    /**
     * Erstellt ein neues {@link LedPanel} Object.
     */
    public LedPanel()
    {
        super();

        this.k = 1;
        this.speed = 10;
        this.pause = false;
        this.n = new ArrayList<>();
        this.o = new HashMap<>();
        this.i = false;
        this.e = 1;

        setBackground(null);
        setLayout(null);
        setDoubleBuffered(true);
    }

    /**
     * @param flag boolean
     */
    public void b(final boolean flag)
    {
        this.i = flag;
    }

    /**
     * @param byte0 byte
     */
    public void b(final byte byte0)
    {
        this.e = byte0;
    }

    /**
     * @param image {@link Image}
     */
    public void b(final Image image)
    {
        this.image = image;
    }

    /**
     * @param obj Object
     */
    public void b(final Object obj)
    {
        synchronized (this.n)
        {
            A a = this.o.remove(obj);

            if (a != null)
            {
                this.n.remove(a);
            }
        }
    }

    /**
     *
     */
    public void g()
    {
        synchronized (this.n)
        {
            this.n.clear();
            this.o.clear();
        }
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize()
    {
        Dimension dimension = super.getPreferredSize();

        if (this.dimension == null)
        {
            Insets insets = getInsets();
            dimension = new Dimension(dimension.width + 399, this.height + insets.top + insets.bottom);
        }

        return dimension;
    }

    /**
     *
     */
    private void h()
    {
        A a;

        try
        {
            label0:
            {
                synchronized (this.n)
                {
                    if (!this.n.isEmpty())
                    {
                        break label0;
                    }
                }

                return;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        a = this.n.get(0);

        if ((a.c() + a.b()) <= 0)
        {
            this.n.remove(0);

            int i1 = a.c() + a.b();
            a.d();
            this.n.add(a);
            a = this.n.get(0);
            a.b(i1);
        }

        a.c(this.k);
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(final Graphics g1)
    {
        int i1;
        int j1;
        label0:
        {
            super.paintComponent(g1);
            i1 = getInsets().top;
            j1 = getWidth();

            synchronized (this.n)
            {
                if (!this.n.isEmpty())
                {
                    break label0;
                }

                int k1 = this.image.getWidth(this);

                for (int i2 = 0; i2 < j1; i2 += k1)
                {
                    g1.drawImage(this.image, i2, i1, this);
                }
            }

            return;
        }

        int l1 = 0;
        int j2 = (this.n.get(0)).c();
        int k2 = this.n.size();

        while (j2 < j1)
        {
            A a = this.n.get(l1);

            if (a.getImage() != null)
            {
                g1.drawImage(a.getImage(), j2, i1, this);
            }

            j2 += a.b();

            if (++l1 != k2)
            {
                continue;
            }

            if (j2 < 0)
            {
                break;
            }

            l1 = 0;
        }
    }

    /**
     *
     */
    public void pauseAnimation()
    {
        this.pause = !this.pause;
    }

    /**
     * @param image {@link Image}
     * @param obj Object
     */
    public void repaint(final Image image, final Object obj)
    {
        A a = this.o.get(obj);

        synchronized (this.n)
        {
            if (a == null)
            {
                a = new A();
                this.o.put(obj, a);
                this.n.add(a);
                a.b(image, true);
            }
            else if (a.b() == image.getWidth(null))
            {
                a.b(image, true);
            }
            else
            {
                boolean flag = this.i;
                int i1 = (this.n.get(0)).c();
                int j1 = getWidth();
                int k1 = 0;

                for (int l1 = this.n.size(); (k1 < l1) && (i1 < j1) && !flag; k1++)
                {
                    A a1 = this.n.get(k1);

                    if (a1 == a)
                    {
                        flag = true;

                        break;
                    }

                    i1 += a1.b();
                }

                a.b(image, flag);
            }
        }

        if ((this.thread == null) || this.pause)
        {
            repaint();
        }
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        Thread thread = Thread.currentThread();

        while (this.thread == thread)
        {
            long l1 = System.currentTimeMillis();

            if (!this.pause)
            {
                h();
                repaint();
            }

            l1 = this.speed - (System.currentTimeMillis() - l1);

            if (l1 < 1L)
            {
                l1 = 1L;
            }

            try
            {
                Thread.sleep(l1);
            }
            catch (InterruptedException interruptedexception)
            {
                // Ignore
            }
        }
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
        this.dimension = dimension;
        super.setPreferredSize(dimension);
    }

    /**
     * @param speed int
     */
    public void setSpeed(final int speed)
    {
        this.speed = speed;
    }

    /**
     *
     */
    public void startAnimation()
    {
        if ((this.thread != null) && this.pause)
        {
            this.pause = false;
        }
        else
        {
            this.pause = false;
            this.thread = new Thread(this, "Overload.TickerScroll");
            this.thread.setPriority(10);
            this.thread.start();
        }
    }

    /**
     *
     */
    public void stopAnimation()
    {
        this.thread = null;
    }
}
