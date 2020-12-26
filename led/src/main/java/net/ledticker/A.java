package net.ledticker;

import java.awt.Image;

/**
 * @author Thomas Freese
 */
public class A
{
    /**
     *
     */
    private int b;

    /**
     *
     */
    private Image image;

    /**
     *
     */
    private Image lastImage;

    /**
     *
     */
    private int width;

    /**
     * Erstellt ein neues {@link A} Object.
     */
    public A()
    {
        super();
    }

    /**
     * @return int
     */
    public int b()
    {
        return this.width;
    }

    /**
     * @param image {@link Image}
     * @param flag boolean
     */
    public void b(final Image image, final boolean flag)
    {
        if (image == null)
        {
            this.image = image;
            this.lastImage = image;
            this.width = 0;
            this.b = 0;
            return;
        }

        if (flag)
        {
            this.image = image;
            this.width = image.getWidth(null);
        }
        else
        {
            this.lastImage = image;
        }
    }

    /**
     * @param i int
     */
    public void b(final int i)
    {
        this.b = i;
    }

    /**
     * @return int
     */
    public int c()
    {
        return this.b;
    }

    /**
     * @param i int
     */
    public void c(final int i)
    {
        this.b -= i;
    }

    /**
     *
     */
    public void d()
    {
        if (this.lastImage != null)
        {
            this.image = this.lastImage;
            this.width = this.lastImage.getWidth(null);
            this.lastImage = null;
            this.b = 0;
        }
    }

    /**
     * @return {@link Image}
     */
    public Image getImage()
    {
        return this.image;
    }
}
