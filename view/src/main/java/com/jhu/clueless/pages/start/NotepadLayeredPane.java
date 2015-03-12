package com.jhu.clueless.pages.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.RoomEnum;
import com.jhu.clueless.pieces.WeaponEnum;
import com.jhu.clueless.util.FileUtilities;

/**
 * Defines a Clueless Notepad.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class NotepadLayeredPane extends JLayeredPane implements ActionListener, MouseMotionListener {

	private static final String NOTEPAD_IMAGE = "NOTEPAD.png";

	/**
	 * Constructs a notepad.
	 */
	public NotepadLayeredPane() {
		Icon icon = FileUtilities.getIcon(NOTEPAD_IMAGE);
		Dimension dimension = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		setPreferredSize(dimension);
		JLabel notePadImageLabel = new JLabel();
		notePadImageLabel.setIcon(icon);
		notePadImageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		Point origin = new Point(0, 0);
		notePadImageLabel.setBounds(origin.x, origin.y, icon.getIconWidth(), icon.getIconHeight());
		add(notePadImageLabel, new Integer(1));

		createInputTextFields(new Point(55, 225), new Point(89, 32), CharacterEnum.valuesString());
		createInputTextFields(new Point(55, 225), new Point(89, 190), WeaponEnum.valuesString());
		createInputTextFields(new Point(55, 225), new Point(89, 355), RoomEnum.valuesString());

	}

	private void createInputTextFields(Point startBounds, Point startLocation, List<String> items) {
		Point bounds = new Point(startBounds.x, startBounds.y);
		Point location = new Point(startLocation.x, startLocation.y);

		Point locationOffset = new Point(30, 20);
		Point boundsOffset = new Point(40, 21);

		for (String item : items) {
			createInputTextField(bounds, location);
			createLabel(item, new Point(startBounds.x - 85, bounds.y), new Point(startLocation.x - 85, location.y));
			for (int column = 1; column < 3; column++) {
				bounds.x += boundsOffset.x;
				location.x += locationOffset.x;
				createInputTextField(bounds, location);
			}
			bounds = new Point(startBounds.x, bounds.y + boundsOffset.y);
			location = new Point(startLocation.x, location.y + locationOffset.y);
		}
	}

	private void createLabel(String text, Point bounds, Point location) {
		JLabel characterNameLabel = new JLabel(text);
		characterNameLabel.setForeground(Color.WHITE);
		characterNameLabel.setBounds(bounds.x, bounds.y, 100, 16);
		characterNameLabel.setLocation(location);
		add(characterNameLabel, new Integer(2));
	}

	private void createInputTextField(Point bounds, Point location) {
		JTextField jTextField = new JTextField(2);
		jTextField.setBounds(bounds.x, bounds.y, 25, 16);
		jTextField.setLocation(location);
		jTextField.setColumns(3);
		jTextField.setDocument(new JTextFieldLimit(3));
		add(jTextField, new Integer(2));
	}

	/**
	 * Runs the mouse dragged.
	 *
	 * @param arg0 the argument to act on
	 */
	public void mouseDragged(MouseEvent arg0) {
	}

	/**
	 * Runs the mouse moved.
	 *
	 * @param arg0 the argument to act on
	 */
	public void mouseMoved(MouseEvent arg0) {
	}

	/**
	 * Runs the action performed.
	 *
	 * @param arg0 the argument to act on
	 */
	public void actionPerformed(ActionEvent arg0) {
	}

	/**
	 * Limits the characters that can be typed in the JTextField.
	 *
	 * @author Clint Pitzak
	 *
	 */
	private class JTextFieldLimit extends PlainDocument {
		private int textLimit;

		public JTextFieldLimit(int textLimit) {
			super();
			this.textLimit = textLimit;
		}

		@Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;
			if ((getLength() + str.length()) <= textLimit) {
				super.insertString(offset, str, attr);
			}
		}
	}

}
