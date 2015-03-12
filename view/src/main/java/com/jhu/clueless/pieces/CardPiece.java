package com.jhu.clueless.pieces;

import javax.swing.JLabel;

import com.jhu.clueless.util.FileUtilities;

/**
 * Defines a character for the GUI.
 *
 * @author Clint Pitzak
 */
@SuppressWarnings("serial")
public class CardPiece extends JLabel {

	private final Card card;
	/** path of image icons. */
	private static final String EXTENSION = ".png";

	/**
	 * Constructs the character.
	 *
	 * @param card
	 *            the character to use
	 */
	public CardPiece(Card card) {
		this.card = card;
		setIcon(FileUtilities.getIcon(card.getCardName() + EXTENSION));
	}

	/**
	 * @return the character
	 */
	public Card getCard() {
		return card;
	}

}
