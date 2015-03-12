package com.jhu.clueless.gameplay;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jhu.clueless.interfaces.ModelListener;
import com.jhu.clueless.pieces.Card;
import com.jhu.clueless.pieces.CharacterEnum;

/**
 *
 * @author ELO
 *
 */
public class ModelListenerImpl implements ModelListener {

	/**
	 * Add a new player to the game.
	 *
	 * @param characterName
	 *            Character name of the player to be added to the game
	 * @param userName
	 *            user name of the player to be added to the game
	 * @param startLocation
	 *            Player's startion location
	 */
	public void addPlayer(CharacterEnum characterName, String userName,
			Point startLocation) {
		// TODO: Remove the following testing code
		System.out.println("New player added!");

		System.out.println("Username: " + userName);
		System.out.println("Character: " + characterName.getText());
		System.out.println("Starting location: " + startLocation.toString()
				+ "\n");

		// TODO: Implement the body
	}

	/**
	 * Begin the game.
	 *
	 * @param playerCardsMap
	 *            A map of character name and its dealt cards
	 */
	public void startGame(Map<CharacterEnum, List<Card>> playerCardsMap) {
		// TODO: Remove the following testing code

		System.out.println("Game started!");

		Iterator<Entry<CharacterEnum, List<Card>>> it = playerCardsMap
				.entrySet().iterator();

		while (it.hasNext()) {
			Entry<CharacterEnum, List<Card>> pairs = it.next();
			System.out.println(pairs.getKey().getText() + " has " + pairs.getValue().size() + " cards...");
			for (int i = 0; i < pairs.getValue().size(); i++) {
				System.out.println(pairs.getValue().get(i));
			}
			System.out.println();
			it.remove();

		}

		// TODO: Implement the body

	}

	/**
	 * Show possible moving location of a player.
	 *
	 * @param characterName
	 *            Current player's character name
	 * @param points
	 *            Possible move locations
	 */
	public void showPossibleMoves(CharacterEnum characterName,
			List<Point> points) {
		// TODO: Remove the following testing code

		System.out.println("Moves Shown!");
		System.out.println(characterName + " can move to:");
		for (int i = 0; i < points.size(); i++) {
			System.out.println(points.get(i).toString());
		}
		System.out.println("");

		// TODO: Implement the body

	}

	/**
	 * Move a player to a different location on the board.
	 * @param characterName Current player's character name
	 * @param point Destination point
	 */
	public void movePlayer(CharacterEnum characterName, Point point) {
		// TODO: Remove the following testing code
		System.out.println("Player moved!");
		System.out.println(characterName.getText() + " moved to "
				+ point.toString() + "\n");

		// TODO: Implement the body

	}

	/**
	 * Advance the turn.
	 * @param endCharacterName The player whose turn to be ended
	 * @param startCharacterName The player whose turn is to be started
	 */
	public void advanceTurn(CharacterEnum endCharacterName, CharacterEnum startCharacterName) {
		// TODO: Remove the following testing code

		System.out.println("Turn advanced!");
		System.out.println("Ends " + endCharacterName + "'s turn");
		System.out.println("Begins " + startCharacterName + "'s turn\n");

		// TODO: Implement the body

	}

	/**
	 * Show accusation result a player made.
	 * @param characterName Character name of the player who made the accusation
	 * @param win Result of the accusation
	 */
	public void showAccusationResult(CharacterEnum characterName, Boolean win) {
		// TODO: Remove the following testing code

		System.out.println("Accusation result shown!");

		if (win) {
			System.out.println(characterName + " won!");
		} else {
			System.out.println(characterName + " lost!");
		}

		// TODO: Implement the body
	}

	/**
	 * Request a player to select a card to show the current player.
	 * @param characterName Player who needs to select a card to show
	 * @param cards List of cards the player can choose from to show
	 */
	public void requestSelectCardToShow(CharacterEnum characterName, List<String> cards) {
		// TODO: Remove the following testing code

		System.out.println("Requesting player to show card!");
		System.out.println("Requesting " + characterName
				+ " to show one of the following cards...");
		for (int i = 0; i < cards.size(); i++) {
			System.out.println((i + 1) + " - " + cards.get(i));
		}
		System.out.println();

		// TODO: Implement the body
	}

	/**
	 * Proper player who has one of the suggestion card shows card to the current player.
	 * @param fromCharacterName Player that shows the card
	 * @param toCharacterName Player that sees the card
	 * @param showCard Card to be shown
	 */
	public void showCard(CharacterEnum fromCharacterName, CharacterEnum toCharacterName, String showCard) {
		// TODO Auto-generated method stub

		System.out.println("Card shown!");
		System.out.println(fromCharacterName.getText() + " shows "
				+ toCharacterName.getText() + " " + showCard + "\n");
	}

}
