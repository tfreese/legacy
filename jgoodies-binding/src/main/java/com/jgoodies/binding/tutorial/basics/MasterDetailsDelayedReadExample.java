/*
 * Copyright (c) 2002-2007 JGoodies Karsten Lentzsch. All Rights Reserved. Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met: o Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer. o Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution. o Neither the name of JGoodies Karsten Lentzsch nor the names of its
 * contributors may be used to endorse or promote products derived from this software without specific prior written permission. THIS SOFTWARE IS PROVIDED BY
 * THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jgoodies.binding.tutorial.basics;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.tutorial.Album;
import com.jgoodies.binding.tutorial.TutorialUtils;
import com.jgoodies.binding.value.ConverterFactory;
import com.jgoodies.binding.value.DelayedReadValueModel;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

/**
 * Demonstrates an elegant means how to defer updates of a details view after selecting an element in a master list. This can be useful if changing the details
 * requires time consuming additional operations, for example a database lookup, a heavy computation, or a remote access.
 * <p>
 * This class builds a JList of Albums with an attached details panel that presents the current Album selection. The details panel's components are bound to the
 * domain using ValueModels returned by a PresentationModel. The master JList is bound to a SelectionInList that holds the list of Albums and the currently
 * selected Album. The SelectionInList is used as bean channel for the details PresentationModel. And so, whenever the SelectionInList's selection changes, the
 * details PresentationModel will automatically update the bean used to display the details.
 * <p>
 * This example implements the deferred details update by using a delayed version of the SelectionInList as the presentation model's bean channel. Changes in
 * the SelectionInList's selecion are held back for a specified delay before the selection becomes the presentation model's new bean.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.6 $
 * @see #initComponents()
 * @see DelayedReadValueModel
 * @see com.jgoodies.binding.PresentationModel
 * @see com.jgoodies.binding.tutorial.basics.MasterDetailsSelectionInListExample
 */

public final class MasterDetailsDelayedReadExample
{
	/**
	 * The default in milliseconds to wait before a selection change shall be forwarded to the delayed details presentation model.
	 */
	private static final int DEFAULT_DELAY = 1000;

	public static void main(final String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		}
		catch (Exception e)
		{
			// Likely PlasticXP is not in the class path; ignore.
		}

		JFrame frame = new JFrame();
		frame.setTitle("Binding Tutorial :: Master/Details (Delayed)");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JComponent panel = new MasterDetailsDelayedReadExample().buildPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		TutorialUtils.locateOnOpticalScreenCenter(frame);
		frame.setVisible(true);
	}

	/**
	 * Holds the list of Albums plus a single selection.
	 */
	private final SelectionInList<Album> albumSelection;

	private JList<Album> albumsList;

	private JTextComponent artistField;

	private JTextComponent classicalField;

	private JButton closeButton;

	private JTextComponent composerField;

	/**
	 * Holds the selected Album and vends ValueModels that adapt Album properties.
	 */
	private final PresentationModel<Album> detailsModel;

	// Launching **************************************************************

	private JTextComponent titleField;

	// Instance Creation ******************************************************

	/**
	 * Constructs a delayed list editor using an example Album list and the default delay.
	 */
	public MasterDetailsDelayedReadExample()
	{
		this(Album.ALBUMS);
	}

	/**
	 * Constructs a delayed list editor for editing the given list of Albums using the default delay.
	 * 
	 * @param albums the list of Albums to edit
	 */
	public MasterDetailsDelayedReadExample(final List<Album> albums)
	{
		this(albums, DEFAULT_DELAY);
	}

	/**
	 * Constructs a list editor for editing the given list of Albums using the specified delay.
	 * 
	 * @param albums the list of Albums to edit
	 * @param delay the milliseconds to wait before the album selection is forwarded to the SelectionInList
	 */
	public MasterDetailsDelayedReadExample(final List<Album> albums, final int delay)
	{
		this.albumSelection = new SelectionInList<>(albums);

		// Uses the delayed SelectionInList as PresentationModel's bean channel.
		// The SelectionInList is wrapped so that selection updates will be
		// deferred for the specified delay. In other words, the list's
		// selection becomes the Presentation Model's new bean - after a delay.
		this.detailsModel = new PresentationModel<>(new DelayedReadValueModel(this.albumSelection, delay, true));
	}

	// Component Creation and Initialization **********************************

	private JComponent buildButtonBar()
	{
		return new ButtonBarBuilder().addButton(this.closeButton).build();
	}

	// Building ***************************************************************

	/**
	 * Builds and returns a panel that consists of a master list and a details form.
	 * 
	 * @return the built panel
	 */
	public JComponent buildPanel()
	{
		initComponents();

		FormLayout layout = new FormLayout("right:pref, 3dlu, 150dlu:grow", "p, 1dlu, p, 9dlu, p, 1dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 9dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);
		CellConstraints cc = new CellConstraints();

		builder.addSeparator("Albums", cc.xyw(1, 1, 3));
		builder.add(new JScrollPane(this.albumsList), cc.xy(3, 3));

		builder.addSeparator("Delayed Details", cc.xyw(1, 5, 3));
		builder.addLabel("Artist", cc.xy(1, 7));
		builder.add(this.artistField, cc.xy(3, 7));
		builder.addLabel("Title", cc.xy(1, 9));
		builder.add(this.titleField, cc.xy(3, 9));
		builder.addLabel("Classical", cc.xy(1, 11));
		builder.add(this.classicalField, cc.xy(3, 11));
		builder.addLabel("Composer", cc.xy(1, 13));
		builder.add(this.composerField, cc.xy(3, 13));
		builder.add(buildButtonBar(), cc.xyw(1, 15, 3));

		return builder.getPanel();
	}

	/**
	 * Creates and intializes the UI components. The JList used to present the Album list is bound directly to the SelectionInList which implements the required
	 * ListModel interface. Even the JList's selection model is bound directly bound the the SelectionInList's selection index holder. It's just that the
	 * presentation model's bean channel is not the selection, but a delayed selection.
	 * <p>
	 * If the user selects an element in the Album JList, the selection model changes immediately. It's just that the bean of the detailsModel will be set a bit
	 * later, which in turn holds back details change a bit. See the constructor to study how the master SelectionInList is wrapped by a DelayedReadValueModel
	 * to hold back selection changes for a limited amount of time.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents()
	{
		this.albumsList = BasicComponentFactory.createList(this.albumSelection, TutorialUtils.createAlbumListCellRenderer());

		this.titleField = BasicComponentFactory.createTextField(this.detailsModel.getModel(Album.PROPERTYNAME_TITLE));
		this.titleField.setEditable(false);
		this.artistField = BasicComponentFactory.createTextField(this.detailsModel.getModel(Album.PROPERTYNAME_ARTIST));
		this.artistField.setEditable(false);
		this.classicalField =
				BasicComponentFactory.createTextField(ConverterFactory.createBooleanToStringConverter(this.detailsModel.getModel(Album.PROPERTYNAME_CLASSICAL),
						"Yes", "No"));
		this.classicalField.setEditable(false);
		this.composerField = BasicComponentFactory.createTextField(this.detailsModel.getModel(Album.PROPERTYNAME_COMPOSER));
		this.composerField.setEditable(false);
		this.closeButton = new JButton(TutorialUtils.getCloseAction());
	}

}
