package com.jhu.clueless.pieces;

import javax.swing.JLabel;

import com.jhu.clueless.util.FileUtilities;

/**
 * Defines a character for the GUI.
 *
 * @author Clint Pitzak
 */
@SuppressWarnings("serial")
public class CharacterPiece extends JLabel {

	private final CharacterEnum character;
	/** path of image icons. */
	private static final String EXTENSION = "-PIECE.png";

	/**
	 * Constructs the character.
	 *
	 * @param character
	 *            the character to use
	 */
	public CharacterPiece(CharacterEnum character) {
		this.character = character;
		setIcon(FileUtilities.getIcon(character.toString() + EXTENSION));
	}

	/**
	 * @return the character
	 */
	public CharacterEnum getCharacter() {
		return character;
	}

}
