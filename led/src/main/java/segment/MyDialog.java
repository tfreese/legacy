package segment;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Thomas Freese
 */
public class MyDialog extends Dialog implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = -8501617093049943641L;

    /**
     * Erstellt ein neues {@link MyDialog} Object.
     *
     * @param parent {@link Frame}
     */
    public MyDialog(final Frame parent)
    {
        super(parent, "7-Segment-Anzeige", true);

        setBounds(100, 100, 400, 300);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        customizeLayout(panel);
        add(panel, BorderLayout.CENTER);
        // Ende-Button
        Button button = new Button("Ende");
        button.addActionListener(this);
        add(button, BorderLayout.SOUTH);
        pack();
        // Window-Ereignisse
        addWindowListener(new WindowAdapter()
        {
            /**
             * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
             */
            @Override
            public void windowClosing(final WindowEvent event)
            {
                endDialog();
            }
        });
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event)
    {
        String cmd = event.getActionCommand();

        if (cmd.equals("Ende"))
        {
            endDialog();
        }
    }

    /**
     * @param panel {@link Panel}
     */
    private void customizeLayout(final Panel panel)
    {
        panel.setLayout(new FlowLayout());
        panel.add(new Segment7(0));
        panel.add(new Segment7(1));
        panel.add(new Segment7(2));
        panel.add(new Segment7(3));
        panel.add(new Segment7(4));
        panel.add(new Segment7(5));
        panel.add(new Segment7(6));
        panel.add(new Segment7(7));
        panel.add(new Segment7(8));
        panel.add(new Segment7(9));
    }

    /**
     *
     */
    void endDialog()
    {
        setVisible(false);
        dispose();
        ((Window) getParent()).toFront();
        getParent().requestFocus();
    }
}
