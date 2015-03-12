package com.jhu.clueless.util;

import java.util.ArrayList;
import java.util.List;

import com.jhu.clueless.pieces.CardPiece;
import com.jhu.clueless.pieces.CharacterEnum;

/**
 * @author Clint Pitzak
 */
public class PlayerDataManager {

	private String username;
	private CharacterEnum characterEnum;
	private List<CardPiece> cards = new ArrayList<CardPiece>();

	/**
	 * Creates player data manager.
	 *
	 * @param username the username to use
	 */
	public PlayerDataManager(String username) {
		this.username = username;
	}

	public void setUserName(String username) {
		this.username = username;
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

	/**
	 * @param characterEnum the character enum to use
	 */
	public void setCharacterEnum(CharacterEnum characterEnum) {
		this.characterEnum = characterEnum;
	}

	/**
	 * @return the cards
	 */
	public List<CardPiece> getCards() {
		return cards;
	}

	/**
	 * @param cards
	 *            the cards to set
	 */
	public void setCards(List<CardPiece> cards) {
		this.cards = cards;
	}

}
