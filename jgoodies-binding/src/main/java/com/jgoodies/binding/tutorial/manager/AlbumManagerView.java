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

package com.jgoodies.binding.tutorial.manager;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.tutorial.TutorialUtils;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Builds a user interface for managing Albums using a table to display the Albums and buttons to add, edit, and delete the selected album. The models and
 * Actions are provided by an underlying AlbumManagerModel.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.3 $
 * @see AlbumManagerModel
 * @see com.jgoodies.binding.PresentationModel
 */

public final class AlbumManagerView
{

	/**
	 * Provides a list of Albums with selection and Action for operating on the managed Albums.
	 */
	private final AlbumManagerModel albumManagerModel;

	private JTable albumTable;

	private JButton deleteButton;

	private JButton editButton;

	private JButton newButton;

	// Instance Creation ******************************************************

	/**
	 * Constructs a list editor for editing the given list of albums.
	 * 
	 * @param albumManagerModel the list of albums to edit
	 */
	public AlbumManagerView(final AlbumManagerModel albumManagerModel)
	{
		this.albumManagerModel = albumManagerModel;
	}

	// Component Creation and Initialization **********************************

	private JComponent buildButtonBar()
	{
		return new ButtonBarBuilder().addButton(this.newButton, this.editButton, this.deleteButton).build();
	}

	/**
	 * Builds and returns the panel for the Album Manager View.
	 * 
	 * @return the built panel
	 */
	public JComponent buildPanel()
	{
		initComponents();
		initEventHandling();

		FormLayout layout = new FormLayout("fill:250dlu:grow", "p, 1dlu, fill:200dlu, 6dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);
		CellConstraints cc = new CellConstraints();

		builder.addTitle("Albums", cc.xy(1, 1));
		builder.add(new JScrollPane(this.albumTable), cc.xy(1, 3));
		builder.add(buildButtonBar(), cc.xy(1, 5));

		return builder.getPanel();
	}

	// Building ***************************************************************

	/**
	 * Creates and intializes the UI components.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents()
	{
		this.albumTable = new JTable();
		this.albumTable.setModel(TutorialUtils.createAlbumTableModel(this.albumManagerModel.getAlbumSelection()));
		this.albumTable.setSelectionModel(new SingleListSelectionAdapter(this.albumManagerModel.getAlbumSelection().getSelectionIndexHolder()));

		this.newButton = new JButton(this.albumManagerModel.getNewAction());
		this.editButton = new JButton(this.albumManagerModel.getEditAction());
		this.deleteButton = new JButton(this.albumManagerModel.getDeleteAction());
	}

	private void initEventHandling()
	{
		this.albumTable.addMouseListener(this.albumManagerModel.getDoubleClickHandler());
	}

}
