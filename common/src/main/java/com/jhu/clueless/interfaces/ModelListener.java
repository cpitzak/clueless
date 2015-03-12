package com.jhu.clueless.interfaces;

import java.awt.Point;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import com.jhu.clueless.pieces.Card;
import com.jhu.clueless.pieces.CharacterEnum;

/**
 * @author ELO
 *
 */
public interface ModelListener extends EventListener {

	/**
	 * Add a new player to the game.
	 * @param characterName Character name of the player to be added to the game
	 * @param userName user name of the player to be added to the game
	 * @param startLocation Player's startion location
	 */
	void addPlayer(CharacterEnum characterName, String userName, Point startLocation);

	/**
	 * Begin the game.
	 * @param playerCardsMap A map of character name and its dealt cards
	 */
	void startGame(Map<CharacterEnum, List<Card>> playerCardsMap);

	/**
	 * Show possible moving location of a player.
	 * @param characterName Current player's character name
	 * @param points Possible move locations
	 */
	void showPossibleMoves(CharacterEnum characterName, List<Point> points);

	/**
	 * Move a player to a different location on the board.
	 * @param characterName Current player's character name
	 * @param point Destination point
	 */
	void movePlayer(CharacterEnum characterName, Point point);

	/**
	 * Advance the turn.
	 * @param endCharacterName The player whose turn to be ended
	 * @param startCharacterName The player whose turn is to be started
	 */
	void advanceTurn(CharacterEnum endCharacterName, CharacterEnum startCharacterName);

	/**
	 * Show accusation result a player made.
	 * @param characterName Character name of the player who made the accusation
	 * @param win Result of the accusation
	 */
	void showAccusationResult(CharacterEnum characterName, Boolean win);

	/**
	 * Request a player to select a card to show the current player.
	 * @param characterName Player who needs to select a card to show
	 * @param cards List of cards the player can choose from to show
	 */
	void requestSelectCardToShow(CharacterEnum characterName, List<String> cards);

	/**
	 * Proper player who has one of the suggestion card shows card to the current player.
	 * @param fromCharacterName Player that shows the card
	 * @param toCharacterName Player that sees the card
	 * @param showCard Card to be shown
	 */
	void showCard(CharacterEnum fromCharacterName, CharacterEnum toCharacterName, String showCard);
}
