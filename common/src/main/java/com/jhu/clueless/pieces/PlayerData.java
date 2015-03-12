package com.jhu.clueless.pieces;

/**
 * Defines a players data.
 *
 * @author Clint Pitzak
 *
 */
public class PlayerData {

	private final String username;
	private final CharacterEnum characterEnum;

	public PlayerData(String username, CharacterEnum characterEnum) {
		this.username = username;
		this.characterEnum = characterEnum;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the characterEnum
	 */
	public CharacterEnum getCharacterEnum() {
		return characterEnum;
	}

}
