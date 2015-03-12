package com.jhu.clueless.pieces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ELO
 *
 */
public class Player {

	private boolean isTurn;
	//private Point playerLocation = new Point();
	private Square playerLocation;
	private String userName;
	private CharacterEnum characterName;
	private boolean isActive;

	/**
	 * @param characterName the characterName to set
	 */
	public void setCharacterName(CharacterEnum characterName) {
		this.characterName = characterName;
	}

	private List<Card> cards = new ArrayList<Card>();

	/**
	 * Default constructor for Player.
	 */
	public Player() {
		this.setTurn(false);
	}

	/**
	 * Constructor for Player.
	 * @param userName User name of the player
	 * @param characterName Character name of the player
	 * @param playerLocation the initial location of the player
	 * @param isActive Set if the player is active or inactive
	 */
	public Player(String userName, CharacterEnum characterName, Square playerLocation, boolean isActive) {
		this.userName = userName;
		this.characterName = characterName;
		this.playerLocation = playerLocation;
		this.isActive = isActive;
	}


	/**
	 * @return the playerLocation
	 */
	public Square getPlayerLocation() {
		return playerLocation;
	}

	/**
	 * @param playerLocation location of the player
	 */
	public void setPlayerLocation(Square playerLocation) {
		this.playerLocation = playerLocation;

	}

	/**
	 * @return the character name
	 */
	public CharacterEnum getCharacterName() {
		return characterName;
	}

	/**
	 * @param characterName Character name
	 *            the character to set
	 */
	public void setCharacter(CharacterEnum characterName) {
		this.characterName = characterName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName User name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the cards
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Add card to player's card list.
	 * @param card the card to be added to the player's card list
	 */
	public void addCard(Card card) {
		this.cards.add(card);
	}



	/**
	 * @return the isTurn
	 */
	public boolean isTurn() {
		return isTurn;
	}



	/**
	 * @param isTurn the isTurn to set
	 */
	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((characterName == null) ? 0 : characterName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (characterName != other.characterName)
			return false;
		return true;
	}
}
