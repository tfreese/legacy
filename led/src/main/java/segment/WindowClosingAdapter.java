package segment;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Thomas Freese
 */
public class WindowClosingAdapter extends WindowAdapter
{
    /**
     *
     */
    private boolean exitSystem = false;

    /**
     * Erzeugt einen WindowClosingAdapter zum Schliessen des Fensters. Das Programm wird nicht beendet.
     */
    public WindowClosingAdapter()
    {
        this(false);
    }

    /**
     * Erzeugt einen WindowClosingAdapter zum Schliessen des Fensters. Ist exitSystem true, wird das komplette Programm beendet.
     * 
     * @param exitSystem boolean
     */
    public WindowClosingAdapter(final boolean exitSystem)
    {
        super();

        this.exitSystem = exitSystem;
    }

    /**
     * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
     */
    @Override
    public void windowClosing(final WindowEvent event)
    {
        event.getWindow().setVisible(false);
        event.getWindow().dispose();

        if (this.exitSystem)
        {
            System.exit(0);
        }
    }
}
