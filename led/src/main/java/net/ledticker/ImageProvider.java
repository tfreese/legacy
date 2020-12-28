package net.ledticker;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import net.led.elements.Element;

/**
 * @author Thomas Freese
 */
public class ImageProvider
{
    /**
     *
     */
    private Component component;

    /**
     *
     */
    private Element element;

    /**
     *
     */
    private Image image;

    /**
     *
     */
    private Matrix matrix;

    /**
     *
     */
    private Object object;

    /**
     * Erstellt ein neues {@link ImageProvider} Object.
     *
     * @param element {@link Element}
     * @param matrix {@link Matrix}
     * @param component {@link Component}
     */
    public ImageProvider(final Element element, final Matrix matrix, final Component component)
    {
        super();

        this.object = new Object();
        this.element = element;
        this.matrix = matrix;
        this.component = component;

        createImage();
    }

    /**
     *
     */
    public void createImage()
    {
        int width = this.matrix.getWidthOf(this.element);
        int heigth = this.matrix.getHeigth();

        if ((this.image == null) || (this.image.getWidth(null) != width) || (this.image.getHeight(null) != heigth) || (this.image instanceof BufferedImage))
        {
            this.image = this.component.createImage(width, heigth);

            if (this.image == null)
            {
                this.image = new BufferedImage(width, heigth, 2);
            }
        }

        if (this.image != null)
        {
            Graphics g = this.image.getGraphics();
            this.matrix.paintDots(g, this.image.getWidth(null), this.image.getHeight(null));
            this.matrix.paintElement(g, this.element);
        }
    }

    /**
     * @return {@link Element}
     */
    public Element getElement()
    {
        return this.element;
    }

    /**
     * @return {@link Image}
     */
    public Image getImage()
    {
        return this.image;
    }

    /**
     * @return Object
     */
    public Object getObject()
    {
        return this.object;
    }
}
