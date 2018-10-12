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
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.tutorial.TutorialUtils;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

/**
 * Demonstrates three different styles when to commit changes: on key typed, on focus lost, on OK/Apply pressed. Therefore we bind 3 JTextFields to 3 String
 * typed ValueModels that honor the commit style. And we bind 3 JLabels directly to these ValueModels that display the current value.
 * <p>
 * The ValueModels used in this example are requested from a PresentationModel that adapts text properties of a TextBean. This is just to demonstrate a
 * consistent binding style. The same techniques work with any ValueModel.
 * <p/>
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.8 $
 * @see com.jgoodies.binding.PresentationModel
 */
public final class CommitStylesExample
{
	private final class ApplyAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		private ApplyAction()
		{
			super("Apply");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			CommitStylesExample.this.presentationModel.triggerCommit();
		}
	}

	/**
	 * A simple bean that just provides three bound read-write text properties.
	 */
	public static final class TextBean extends Model
	{
		// Names for the Bound Bean Properties ---------------------
		public static final String PROPERTYNAME_TEXT1 = "text1";
		public static final String PROPERTYNAME_TEXT2 = "text2";
		public static final String PROPERTYNAME_TEXT3 = "text3";
		/**
		 *
		 */
		private static final long serialVersionUID = -3184924946409185085L;
		private String text1;
		private String text2;
		private String text3;

		// Instance Creation ---------------------------------------
		private TextBean()
		{
			this.text1 = "Text1";
			this.text2 = "Text2";
			this.text3 = "Text3";
		}

		// Accessors -----------------------------------------------
		public String getText1()
		{
			return this.text1;
		}

		public String getText2()
		{
			return this.text2;
		}

		public String getText3()
		{
			return this.text3;
		}

		public void setText1(final String newText1)
		{
			String oldText1 = getText1();
			this.text1 = newText1;
			firePropertyChange(PROPERTYNAME_TEXT1, oldText1, newText1);
		}

		public void setText2(final String newText2)
		{
			String oldText2 = getText2();
			this.text2 = newText2;
			firePropertyChange(PROPERTYNAME_TEXT2, oldText2, newText2);
		}

		public void setText3(final String newText3)
		{
			String oldText3 = getText3();
			this.text3 = newText3;
			firePropertyChange(PROPERTYNAME_TEXT3, oldText3, newText3);
		}
	}

	public static void main(final String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
			// Likely PlasticXP is not in the class path; ignore.
		}
		JFrame frame = new JFrame();
		frame.setTitle("Binding Tutorial :: Commit Styles");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		CommitStylesExample example = new CommitStylesExample();
		JComponent panel = example.buildPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		TutorialUtils.locateOnOpticalScreenCenter(frame);
		frame.setVisible(true);
	}

	private JButton applyButton;
	private JTextComponent onApplyField;
	private JLabel onApplyLabel;
	private JTextComponent onFocusLostField;
	private JLabel onFocusLostLabel;
	// Launching **************************************************************
	private JTextComponent onKeyTypedField;
	// Instance Creation ******************************************************
	private JLabel onKeyTypedLabel;
	// Component Creation and Initialization **********************************
	/**
	 * Holds a TextBean and vends ValueModels that adapt TextBean properties. As an alternative to this PresentationModel we could use 3 ValueModels, for
	 * example 3 ValueHolders, or any other ValueModel implementation.
	 */
	private final PresentationModel<TextBean> presentationModel;

	// Building ***************************************************************
	/**
	 * Constructs the example with a PresentationModel on the a TextBean.
	 */
	public CommitStylesExample()
	{
		this.presentationModel = new PresentationModel<>(new TextBean());
	}

	private JComponent buildButtonBar()
	{
		return new ButtonBarBuilder().addButton(this.applyButton).build();
	}

	// Actions ****************************************************************
	/**
	 * Builds and returns the panel.
	 * <p/>
	 * 
	 * @return the built panel
	 */
	public JComponent buildPanel()
	{
		initComponents();

		FormLayout layout = new FormLayout("right:max(50dlu;pref), 3dlu, 50dlu, 9dlu, 50dlu", "p, 6dlu, p, 3dlu, p, 3dlu, p, 17dlu, p");

		PanelBuilder builder = new PanelBuilder(layout);
		builder.border(Borders.DIALOG);
		CellConstraints cc = new CellConstraints();

		builder.addTitle("Commit Style", cc.xy(1, 1));
		builder.addTitle("Value", cc.xy(5, 1));
		builder.addLabel("Key Typed", cc.xy(1, 3));
		builder.add(this.onKeyTypedField, cc.xy(3, 3));
		builder.add(this.onKeyTypedLabel, cc.xy(5, 3));
		builder.addLabel("Focus Lost", cc.xy(1, 5));
		builder.add(this.onFocusLostField, cc.xy(3, 5));
		builder.add(this.onFocusLostLabel, cc.xy(5, 5));
		builder.addLabel("Apply Pressed", cc.xy(1, 7));
		builder.add(this.onApplyField, cc.xy(3, 7));
		builder.add(this.onApplyLabel, cc.xy(5, 7));
		builder.add(buildButtonBar(), cc.xyw(1, 9, 5));

		return builder.getPanel();
	}

	// Helper Class ***********************************************************
	/**
	 * Creates,binds and configures the UI components.
	 * <p>
	 */
	private void initComponents()
	{
		this.onKeyTypedField = BasicComponentFactory.createTextField(this.presentationModel.getModel(TextBean.PROPERTYNAME_TEXT1), false);
		this.onKeyTypedLabel = BasicComponentFactory.createLabel(this.presentationModel.getModel(TextBean.PROPERTYNAME_TEXT1));

		this.onFocusLostField = BasicComponentFactory.createTextField(this.presentationModel.getModel(TextBean.PROPERTYNAME_TEXT2));
		this.onFocusLostLabel = BasicComponentFactory.createLabel(this.presentationModel.getModel(TextBean.PROPERTYNAME_TEXT2));

		this.onApplyField = BasicComponentFactory.createTextField(this.presentationModel.getBufferedModel(TextBean.PROPERTYNAME_TEXT3));
		this.onApplyLabel = BasicComponentFactory.createLabel(this.presentationModel.getModel(TextBean.PROPERTYNAME_TEXT3));

		this.applyButton = new JButton(new ApplyAction());
	}
}