package com.jhu.clueless.pieces;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Defines a Player for the View.
 *
 * @author Clint Pitzak
 *
 */
public class VPlayer {

	private String userName = "guest";
	private CharacterPiece character;
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	/**
	 * @return the character piece
	 */
	public CharacterPiece getCharacter() {
		return character;
	}

	/**
	 * Sets the character.
	 *
	 * @param character the character to set
	 */
	public void setCharacter(CharacterPiece character) {
		this.character = character;
	}

	/**
	 * Gets the user name of this player.
	 *
	 * @return the user name of this player
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name of this player.
	 *
	 * @param newUserName
	 *            the user name of this player
	 */
	public void setUserName(String newUserName) {
		String oldUserName = this.userName;
		userName = newUserName;
		changeSupport.firePropertyChange("player.userName", oldUserName, newUserName);
	}

	/**
	 * Add property change listener.
	 *
	 * @param listener
	 *            the listener to use
	 */
	public void addPropertyChangeSupport(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a property change listener.
	 *
	 * @param listener
	 *            the listener to use
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

}
