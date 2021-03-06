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
import com.jgoodies.binding.tutorial.Album;
import com.jgoodies.binding.tutorial.TutorialUtils;
import com.jgoodies.binding.value.ConverterFactory;
import com.jgoodies.binding.value.ValueHolder;
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
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

/**
 * Demonstrates a "hand-made" way how to connect a master list with a bound details view. It builds a JList of Albums with an attached details panel that
 * presents the current Album selection. The details panel's components are bound to the domain using ValueModels returned by a PresentationModel.
 * <p>
 * This example handles selection changes with a custom ListSelectionListener, the AlbumSelectionHandler, that sets the JList's selected values as new bean of
 * the details PresentationModel. A simpler means to achieve the same effect is demonstrated by the MasterDetailsSelectionInListExample that uses the
 * SelectionInList as bean channel for the details PresentationModel.
 * <p>
 * Another variant of this example is the MasterDetailsCopyingExample that copies the details data on list selection changes, instead of binding the details UI
 * components to the details PresentationModel's ValueModels.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.12 $
 * @see com.jgoodies.binding.PresentationModel
 * @see com.jgoodies.binding.tutorial.basics.MasterDetailsCopyingExample
 * @see com.jgoodies.binding.tutorial.basics.MasterDetailsSelectionInListExample
 */

public final class MasterDetailsBoundExample
{

	/**
	 * Sets the selected album as bean in the details model.
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

			Album selectedAlbum = MasterDetailsBoundExample.this.albumsList.getSelectedValue();
			MasterDetailsBoundExample.this.detailsModel.setBean(selectedAlbum);
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
		frame.setTitle("Binding Tutorial :: Master/Details (Bound)");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JComponent panel = new MasterDetailsBoundExample().buildPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		TutorialUtils.locateOnOpticalScreenCenter(frame);
		frame.setVisible(true);
	}

	/**
	 * The Albums displayed in the master list.
	 */
	private final List<Album> albums;

	private JList<Album> albumsList;

	private JTextComponent artistField;

	private JTextComponent classicalField;

	private JButton closeButton;

	private JTextComponent composerField;

	// Launching **************************************************************

	/**
	 * Holds the edited Album and vends ValueModels that adapt Album properties.
	 */
	private final PresentationModel<Album> detailsModel;

	// Instance Creation ******************************************************

	private JTextComponent titleField;

	/**
	 * Constructs a list editor using a example Album list.
	 */
	public MasterDetailsBoundExample()
	{
		this(Album.ALBUMS);
	}

	// Component Creation and Initialization **********************************

	/**
	 * Constructs a list editor for editing the given list of Albums.
	 * 
	 * @param albums the list of Albums to edit
	 */
	public MasterDetailsBoundExample(final List<Album> albums)
	{
		this.albums = albums;
		this.detailsModel = new PresentationModel<>(new ValueHolder(null, true));
	}

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

	/**
	 * Creates, binds, and configures the UI components. All components in the details view are read-only.
	 * <p>
	 * The coding style used here is based on standard Swing components. Therefore we can create and bind the components in one step. And that's the purpose of
	 * the BasicComponentFactory class.
	 * <p>
	 * If you need to bind custom components, for example MyTextField, MyCheckBox, MyComboBox, you can use the more basic Bindings class. The code would then
	 * read:
	 * 
	 * <pre>
	 * titleField = new MyTextField();
	 * Bindings.bind(titleField, detailsModel.getModel(Album.PROPERTYNAME_TITLE));
	 * </pre>
	 * <p>
	 * I strongly recommend to use either the BasicComponentFactory or the Bindings class. These classes hide details of the binding. So you better <em>not</em>
	 * write the following code:
	 * 
	 * <pre>
	 * titleField = new JTextField();
	 * titleField.setDocument(new DocumentAdapter(detailsModel.getModel(Album.PROPERTYNAME_TITLE)));
	 * </pre>
	 */
	private void initComponents()
	{
		this.albumsList = new JList<>(this.albums.toArray(new Album[0]));
		this.albumsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.albumsList.setCellRenderer(TutorialUtils.createAlbumListCellRenderer());

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

	// Event Handling ********************************************************

	private void initEventHandling()
	{
		this.albumsList.addListSelectionListener(new AlbumSelectionHandler());
	}

}
