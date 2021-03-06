package net.leddisplay.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import net.led.elements.StockDisplayElement;
import net.led.provider.Stock;
import net.led.provider.UpdateListener;
import net.led.provider.YahooProvider;
import net.led.util.ColorSelectorListener;
import net.led.util.ColorSelectorPanel;
import net.leddisplay.LedDisplay;
import net.leddisplay.LedDisplayFactory;

/**
 * @author Thomas Freese
 */
public class LedDisplayDemo implements ActionListener, ColorSelectorListener, UpdateListener, ItemListener
{
    /**
     * @param args String[]
     */
    public static void main(final String[] args)
    {
        new LedDisplayDemo();
    }

    /**
     *
     */
    private JComboBox<String> anchor = new JComboBox<>();

    /**
     *
     */
    private JTextField bottomLead = new JTextField(6);

    /**
     *
     */
    private StockDisplayElement displayElement;

    /**
     *
     */
    private JFrame displayFrame;

    /**
     *
     */
    private JTextField dotHeight = new JTextField(6);

    /**
     *
     */
    private JTextField dotWidth = new JTextField(6);

    /**
     *
     */
    private JTextField hGap = new JTextField(6);

    /**
     *
     */
    private LedDisplay ledDisplay;

    /**
     *
     */
    private JTextField leftLead = new JTextField(6);

    /**
     *
     */
    private JTextField rightLead = new JTextField(6);

    /**
     *
     */
    private Color stockDownColor = Color.RED;

    /**
     *
     */
    private Color stockNeutralColor = Color.YELLOW;

    /**
     *
     */
    private Color stockUpColor = Color.GREEN;

    /**
     *
     */
    private Color symbolColor = Color.YELLOW;

    /**
     *
     */
    private JTextField topLead = new JTextField(6);

    /**
     *
     */
    private JTextField vGap = new JTextField(6);

    /**
     *
     */
    private YahooProvider yahooProvider;

    /**
     * Erstellt ein neues {@link LedDisplayDemo} Object.
     */
    public LedDisplayDemo()
    {
        createLedDisplayComponent();

        // create an TextDisplayElement and add it to the display
        String symbol = "GOOG";
        this.displayElement = new StockDisplayElement(symbol);
        this.ledDisplay.setDisplayElement(this.displayElement);
        createGUI();

        // create YahooProvider
        this.yahooProvider = new YahooProvider();
        this.yahooProvider.addSymbol(symbol);
        this.yahooProvider.addUpdateListener(this);
        this.yahooProvider.start();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event)
    {
        String command = event.getActionCommand();

        if (command.equals("setDotSize"))
        {
            int width = 0;
            int height = 0;

            try
            {
                width = Integer.parseInt(this.dotWidth.getText(), 10);
            }
            catch (Exception ex)
            {
                width = 1;
            }

            try
            {
                height = Integer.parseInt(this.dotHeight.getText(), 10);
            }
            catch (Exception ex)
            {
                height = 1;
            }

            this.ledDisplay.setDotSize(width, height);

            // centerFrame(displayFrame);
        }
        else if (command.equals("setDotGap"))
        {
            int hg = 0;
            int vg = 0;

            try
            {
                hg = Integer.parseInt(this.hGap.getText(), 10);
            }
            catch (Exception ex)
            {
                hg = 1;
            }

            try
            {
                vg = Integer.parseInt(this.vGap.getText(), 10);
            }
            catch (Exception ex)
            {
                vg = 1;
            }

            this.ledDisplay.setDotGaps(hg, vg);

            // centerFrame(displayFrame);
        }
        else if (command.equals("setPadding"))
        {
            int top = 0;
            int left = 0;
            int bottom = 0;
            int right = 0;

            try
            {
                top = Integer.parseInt(this.topLead.getText(), 10);
            }
            catch (Exception ex)
            {
                top = 0;
            }

            try
            {
                left = Integer.parseInt(this.leftLead.getText(), 10);
            }
            catch (Exception ex)
            {
                left = 0;
            }

            try
            {
                bottom = Integer.parseInt(this.bottomLead.getText(), 10);
            }
            catch (Exception ex)
            {
                bottom = 0;
            }

            try
            {
                right = Integer.parseInt(this.rightLead.getText(), 10);
            }
            catch (Exception ex)
            {
                right = 0;
            }

            this.ledDisplay.setPadding(top, left, bottom, right);
        }
    }

    /**
     * @param frame {@link Window}
     */
    private void centerFrame(final Window frame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    /**
     * @return {@link JPanel}
     */
    private JPanel createAnchorControlPanel()
    {
        TitledBorder anchorControlBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        anchorControlBorder.setTitle("Anchor Control");

        this.anchor.addItem("Center");
        this.anchor.addItem("North");
        this.anchor.addItem("West");
        this.anchor.addItem("East");
        this.anchor.addItem("South");
        this.anchor.addItem("Nortwest");
        this.anchor.addItem("Norteast");
        this.anchor.addItem("Southwest");
        this.anchor.addItem("Southeast");
        this.anchor.addItemListener(this);

        JLabel label = new JLabel("Display Anchor");

        JPanel stocksControlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
        stocksControlsPanel.setBorder(anchorControlBorder);
        stocksControlsPanel.add(label);
        stocksControlsPanel.add(this.anchor);

        return stocksControlsPanel;
    }

    /**
     * @return {@link JPanel}
     */
    private JPanel createDisplayPaneColors()
    {
        TitledBorder displayColorsBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        displayColorsBorder.setTitle("Display Colors");

        Color gridColor = new Color(0x111111);
        Color bgColor = new Color(0x333333);

        ColorSelectorPanel backgroundSelectorPanel = new ColorSelectorPanel("Select background color", bgColor, "bgColor", this);
        ColorSelectorPanel gridSelectorPanel = new ColorSelectorPanel("Select turned-off led color", gridColor, "gridColor", this);
        ColorSelectorPanel symbolSelectorPanel = new ColorSelectorPanel("Select symbol color", this.symbolColor, "symbolColor", this);

        JPanel textColorsPanel = new JPanel(new GridLayout(3, 1));
        textColorsPanel.setBorder(displayColorsBorder);
        textColorsPanel.add(backgroundSelectorPanel);
        textColorsPanel.add(gridSelectorPanel);
        textColorsPanel.add(symbolSelectorPanel);

        this.ledDisplay.setBackgroundColor(bgColor);
        this.ledDisplay.setDotOffColor(gridColor);

        return textColorsPanel;
    }

    /**
     * @return {@link JPanel}
     */
    private JPanel createDotsControlsPanel()
    {
        TitledBorder dotsControlsBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        dotsControlsBorder.setTitle("Dots Controls");

        this.dotWidth.setText("2");
        this.dotHeight.setText("2");

        JLabel dotWidthLabel = new JLabel("Dot width");
        JLabel dotHeightLabel = new JLabel("Dot height");
        JButton setDotSize = new JButton("Set Dot size");
        setDotSize.setActionCommand("setDotSize");
        setDotSize.addActionListener(this);

        JPanel dotsControlsPanel = new JPanel(new GridBagLayout());
        dotsControlsPanel.setBorder(dotsControlsBorder);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 5, 5);

        dotsControlsPanel.add(this.dotWidth, gbc);

        gbc.gridx++;
        dotsControlsPanel.add(dotWidthLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        dotsControlsPanel.add(this.dotHeight, gbc);

        gbc.gridx++;
        dotsControlsPanel.add(dotHeightLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dotsControlsPanel.add(setDotSize, gbc);

        return dotsControlsPanel;
    }

    /**
     * @return {@link JPanel}
     */
    private JPanel createGapsControlsPanel()
    {
        TitledBorder gapsControlsBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        gapsControlsBorder.setTitle("Gaps Controls");

        this.hGap.setText("1");
        this.vGap.setText("1");

        JLabel hGapLabel = new JLabel("Horizontal gap");
        JLabel vGapLabel = new JLabel("Vertical gap");
        JButton setDotGap = new JButton("Set Dot Gap");
        setDotGap.setActionCommand("setDotGap");
        setDotGap.addActionListener(this);

        JPanel gapsControlsPanel = new JPanel(new GridBagLayout());
        gapsControlsPanel.setBorder(gapsControlsBorder);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 5, 5);

        gapsControlsPanel.add(this.hGap, gbc);

        gbc.gridx++;
        gapsControlsPanel.add(hGapLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gapsControlsPanel.add(this.vGap, gbc);

        gbc.gridx++;
        gapsControlsPanel.add(vGapLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gapsControlsPanel.add(setDotGap, gbc);

        return gapsControlsPanel;
    }

    /**
     *
     */
    private void createGUI()
    {
        JPanel generalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.gridwidth = 2;
        generalPanel.add(createTrendColorsPanel(), gbc);

        gbc.gridy = 1;
        gbc.gridheight = 2;
        generalPanel.add(createAnchorControlPanel(), gbc);

        gbc.gridx += 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        generalPanel.add(createDisplayPaneColors(), gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        generalPanel.add(createPaddingControlsPanel(), gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        generalPanel.add(createDotsControlsPanel(), gbc);

        gbc.gridx++;
        generalPanel.add(createGapsControlsPanel(), gbc);

        // ADD TICKER COMPONENT
        this.displayFrame = new JFrame("Led Ticker Component v2.0");
        this.displayFrame.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints tickerFrameConstarints = new GridBagConstraints();

        tickerFrameConstarints.gridx = 0;
        tickerFrameConstarints.gridy = 0;
        tickerFrameConstarints.weightx = 1;
        tickerFrameConstarints.weighty = 1;
        tickerFrameConstarints.insets = new Insets(5, 5, 0, 5);
        tickerFrameConstarints.fill = GridBagConstraints.BOTH;
        this.displayFrame.getContentPane().add(this.ledDisplay.getComponent(), tickerFrameConstarints);

        // ADD THE MAIN PANEL OF THIS DEMO
        tickerFrameConstarints.fill = GridBagConstraints.NONE;
        tickerFrameConstarints.anchor = GridBagConstraints.NORTH;
        tickerFrameConstarints.gridy = 1;
        tickerFrameConstarints.gridx = 0;
        tickerFrameConstarints.weightx = 0;
        tickerFrameConstarints.weighty = 1;
        this.displayFrame.getContentPane().add(generalPanel, tickerFrameConstarints);

        // displayFrame.pack();
        this.displayFrame.setSize(600, 400);
        centerFrame(this.displayFrame);
        this.displayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.displayFrame.setVisible(true);
    }

    /**
     * Creates the LedTicker.
     */
    private void createLedDisplayComponent()
    {
        // STEP1 : create the component
        this.ledDisplay = LedDisplayFactory.createLedDisplay();
        this.ledDisplay.setTokenGap(2);
        this.ledDisplay.setDotSize(2, 2);
        this.ledDisplay.setDotGaps(1, 1);
    }

    /**
     * @return {@link JPanel}
     */
    private JPanel createPaddingControlsPanel()
    {
        TitledBorder paddingControlsBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        paddingControlsBorder.setTitle("Padding Controls");

        this.topLead.setText("0");
        this.leftLead.setText("0");
        this.bottomLead.setText("0");
        this.rightLead.setText("0");

        JLabel topLabel = new JLabel("Top lead");
        JLabel leftLabel = new JLabel("Left lead");
        JLabel bottomLabel = new JLabel("Bottom lead");
        JLabel rightLabel = new JLabel("Right lead");
        JButton setPadding = new JButton("Set Padding");
        setPadding.setActionCommand("setPadding");
        setPadding.addActionListener(this);

        JPanel paddingControlsPanel = new JPanel(new GridBagLayout());
        paddingControlsPanel.setBorder(paddingControlsBorder);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 5, 5);

        paddingControlsPanel.add(this.topLead, gbc);

        gbc.gridx++;
        paddingControlsPanel.add(topLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paddingControlsPanel.add(this.leftLead, gbc);

        gbc.gridx++;
        paddingControlsPanel.add(leftLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paddingControlsPanel.add(this.bottomLead, gbc);

        gbc.gridx++;
        paddingControlsPanel.add(bottomLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paddingControlsPanel.add(this.rightLead, gbc);

        gbc.gridx++;
        paddingControlsPanel.add(rightLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        paddingControlsPanel.add(setPadding, gbc);

        return paddingControlsPanel;
    }

    /**
     * @return {@link JPanel}
     */
    private JPanel createTrendColorsPanel()
    {
        TitledBorder trendColorsBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black));
        trendColorsBorder.setTitle("Trend Colors");

        ColorSelectorPanel stockUpSelectorPanel = new ColorSelectorPanel("Select Stock Up Color", this.stockUpColor, "upColor", this);
        ColorSelectorPanel stockNeutralSelectorPanel = new ColorSelectorPanel("Select Stock Neutral Color", this.stockNeutralColor, "neutralColor", this);
        ColorSelectorPanel stockDownSelectorPanel = new ColorSelectorPanel("Select Stock Down Color", this.stockDownColor, "downColor", this);

        JPanel trendColorsPanel = new JPanel(new GridLayout(3, 1));
        trendColorsPanel.setBorder(trendColorsBorder);
        trendColorsPanel.add(stockUpSelectorPanel);
        trendColorsPanel.add(stockNeutralSelectorPanel);
        trendColorsPanel.add(stockDownSelectorPanel);

        return trendColorsPanel;
    }

    /**
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(final ItemEvent e)
    {
        this.ledDisplay.setAnchor(this.anchor.getSelectedIndex());
    }

    /**
     * @see net.led.util.ColorSelectorListener#setColor(java.lang.String, java.awt.Color)
     */
    @Override
    public void setColor(final String id, final Color color)
    {
        if (id.equals("bgColor"))
        {
            this.ledDisplay.setBackgroundColor(color);
        }
        else if (id.equals("gridColor"))
        {
            this.ledDisplay.setDotOffColor(color);
        }
        else if (id.equals("symbolColor"))
        {
            this.displayElement.setSymbolColor(color);
            this.ledDisplay.update();
        }
        else if (id.equals("upColor"))
        {
            this.stockUpColor = color;
            this.displayElement.setStockUpColor(color);
            this.ledDisplay.update();
        }
        else if (id.equals("neutralColor"))
        {
            this.stockNeutralColor = color;
            this.displayElement.setStockNeutralColor(color);
            this.ledDisplay.update();
        }
        else if (id.equals("downColor"))
        {
            this.stockDownColor = color;
            this.displayElement.setStockDownColor(color);
            this.ledDisplay.update();
        }
    }

    /**
     * @see net.led.provider.UpdateListener#update(java.lang.Object)
     */
    @Override
    public void update(final Object newValue)
    {
        Stock stock = (Stock) newValue;
        this.displayElement.setLast(stock.getLast());
        this.displayElement.setChangePercent(stock.getChangePercent());
        this.ledDisplay.update();
    }
}
