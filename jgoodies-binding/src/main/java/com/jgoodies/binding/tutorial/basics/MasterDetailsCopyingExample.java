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

import com.jgoodies.binding.tutorial.Album;
import com.jgoodies.binding.tutorial.TutorialUtils;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

/**
 * Demonstrates how to connect a master list with a copying details view. It builds a JList of Albums with an attached Album details panel that presents the
 * current Album selection. The details data is copied back and forth from the domain to the details UI components.
 * <p>
 * A bound variant of this example is the MasterDetailsBoundExample that binds the details UI components to the ValueModels provided by a details
 * PresentationModel. An even simpler variant is the MasterDetailsSelectionInListExample that uses the SelectionInList as bean channel for the details
 * PresentationModel.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.12 $
 * @see com.jgoodies.binding.tutorial.basics.MasterDetailsBoundExample
 * @see com.jgoodies.binding.tutorial.basics.MasterDetailsSelectionInListExample
 */

public final class MasterDetailsCopyingExample
{
	/**
	 * Upates the view using the selected album.
	 */
	private final class AlbumSelectionHandler implements ListSelectionListener
	{
		/**
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(final ListSelectionEvent e)
		{
			if (e.getValueIsAdjusting())
			{
				return;
			}

			// Now set the current selection as edited album.
			MasterDetailsCopyingExample.this.editedAlbum = MasterDetailsCopyingExample.this.albumsList.getSelectedValue();
			// Then copy the album data to the component values.
			updateView(MasterDetailsCopyingExample.this.editedAlbum);
		}
	}

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
		frame.setTitle("Binding Tutorial :: Master/Details (Copying)");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JComponent panel = new MasterDetailsCopyingExample().buildPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		TutorialUtils.locateOnOpticalScreenCenter(frame);
		frame.setVisible(true);
	}

	/**
	 * The Albums displayed in the overview list.
	 */
	private final List<Album> albums;

	private JList<Album> albumsList;

	private JTextComponent artistField;

	private JTextComponent classicalField;

	private JButton closeButton;

	private JTextComponent composerField;

	// Launching **************************************************************

	/**
	 * Holds the list selection, which is the currently edited Album.
	 */
	private Album editedAlbum;

	// Instance Creation ******************************************************

	private JTextComponent titleField;

	/**
	 * Constructs a list editor using a example Album list.
	 */
	public MasterDetailsCopyingExample()
	{
		this(Album.ALBUMS);
	}

	// Component Creation and Initialization **********************************

	/**
	 * Constructs a list editor for editing the given list of Albums.
	 * 
	 * @param albums the list of Albums to edit
	 */
	public MasterDetailsCopyingExample(final List<Album> albums)
	{
		this.albums = albums;
	}

	private JComponent buildButtonBar()
	{
		return new ButtonBarBuilder().addButton(this.closeButton).build();
	}

	// Copying Data Back and Forth ********************************************

	/**
	 * Builds and returns a panel that consists of a master list and a details form.
	 * 
	 * @return the built panel
	 */
	public JComponent buildPanel()
	{
		initComponents();
		initEventHandling();

		FormLayout layout = new FormLayout("right:pref, 3dlu, 150dlu:grow", "p, 1dlu, p, 9dlu, p, 1dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 9dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);
		CellConstraints cc = new CellConstraints();

		builder.addSeparator("Albums", cc.xyw(1, 1, 3));
		builder.add(new JScrollPane(this.albumsList), cc.xy(3, 3));

		builder.addSeparator("Details", cc.xyw(1, 5, 3));
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

	// Building ***************************************************************

	/**
	 * Creates and intializes the UI components. All components in the details view are read-only.
	 */
	private void initComponents()
	{
		this.albumsList = new JList<>(this.albums.toArray(new Album[0]));
		this.albumsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.albumsList.setCellRenderer(TutorialUtils.createAlbumListCellRenderer());

		this.titleField = new JTextField();
		this.titleField.setEditable(false);
		this.artistField = new JTextField();
		this.artistField.setEditable(false);
		this.classicalField = new JTextField();
		this.classicalField.setEditable(false);
		this.composerField = new JTextField();
		this.composerField.setEditable(false);
		this.closeButton = new JButton(TutorialUtils.getCloseAction());
	}

	private void initEventHandling()
	{
		this.albumsList.addListSelectionListener(new AlbumSelectionHandler());
	}

	// Event Handling *********************************************************

	/**
	 * Reads the property values from the edited Album and sets them in this editor's components.
	 * 
	 * @param album the Album to read property values from
	 */
	private void updateView(final Album album)
	{
		this.titleField.setText(album.getTitle());
		this.artistField.setText(album.getArtist());
		this.classicalField.setText(album.isClassical() ? "Yes" : "No");
		this.composerField.setText(album.getComposer());
	}

}
