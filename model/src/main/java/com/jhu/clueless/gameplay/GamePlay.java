package com.jhu.clueless.gameplay;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.jhu.clueless.interfaces.ModelListener;
import com.jhu.clueless.pieces.BoardGame;
import com.jhu.clueless.pieces.Card;
import com.jhu.clueless.pieces.CaseFile;
import com.jhu.clueless.pieces.CharacterCard;
import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.Hallway;
import com.jhu.clueless.pieces.Player;
import com.jhu.clueless.pieces.Room;
import com.jhu.clueless.pieces.RoomCard;
import com.jhu.clueless.pieces.Square;
import com.jhu.clueless.pieces.WeaponCard;

/**
 *
 * @author ELO
 *
 */
public class GamePlay extends Connection {

	private BoardGame boardGame = new BoardGame(5, 5);

	private ModelListener modelListener = null;

	/**
	 * Constructor of class GamePlay.
	 *
	 * @param modelListener
	 *            Model listener that sends updates to the Views
	 */
	public GamePlay(ModelListener modelListener) {
		this.modelListener = modelListener;
	}

	/**
	 * @return the boardGame
	 */
	public BoardGame getBoardGame() {
		return boardGame;
	}

	/**
	 * Add player to the game.
	 *
	 * @param userName
	 *            User name of the player
	 * @param characterName
	 *            Character name of the player
	 * @param playerLocation
	 *            Initial location of the player
	 */
	public void addPlayer(String userName, CharacterEnum characterName,
			Point playerLocation) {
		Player newPlayer = new Player(userName, characterName,
				boardGame.getBoard()[playerLocation.x][playerLocation.y], true);
		boardGame.addPlayer(newPlayer);

		// Update the view
		modelListener.addPlayer(characterName, userName, playerLocation);
	}

	/**
	 * Start the game. This should be called after all players are added.
	 */
	public void startGame() {
		boardGame.startGame();

		List<Player> players = new ArrayList<Player>();
		players = boardGame.getPlayers();

		Map<CharacterEnum, List<Card>> playerCardsMap = new HashMap<CharacterEnum, List<Card>>();

		for (int i = 0; i < players.size(); i++) {
			List<Card> cards = new ArrayList<Card>();
			if (players.get(i).isActive()) {
				for (int j = 0; j < players.get(i).getCards().size(); j++) {
					cards.add(players.get(i).getCards().get(j));
				}
				playerCardsMap.put(players.get(i).getCharacterName(), cards);

			}
		}

		// Update the view
		modelListener.startGame(playerCardsMap);
	}

	/**
	 * Presents a list of possible moves for the current player.
	 *
	 * @return List of possible moves of the current player
	 */
	public List<Point> getPossibleMoves() {
		List<Point> possibleMoves = boardGame.getCurrentPlayer()
				.getPlayerLocation().getPossibleMoves();

		for (int i = 0; i < possibleMoves.size(); i++) {
			Square square = boardGame.getBoard()[possibleMoves.get(i).x][possibleMoves
					.get(i).y];

			// Remove hallways that are occupied
			if ((square instanceof Hallway) && square.getPlayers().size() > 0) {
				possibleMoves.remove(i);
			}
		}

		// Update the view
		modelListener.showPossibleMoves(boardGame.getCurrentPlayer()
				.getCharacterName(), possibleMoves);

		return possibleMoves;
	}

	/**
	 * Move the current player to a different location.
	 *
	 * @param toLocation
	 *            Destination location
	 */
	public void movePlayer(Point toLocation) {
		boardGame.movePlayer(boardGame.getCurrentPlayer(),
				boardGame.getBoard()[toLocation.x][toLocation.y]);

		// Update the view
		modelListener.movePlayer(boardGame.getCurrentPlayer()
				.getCharacterName(), toLocation);
	}

	/**
	 * Current player makes an accusation.
	 *
	 * @param murderCharacter
	 *            Accused character
	 * @param murderRoom
	 *            Accused room
	 * @param murderWeapon
	 *            Accused weapon
	 * @return result of the accusation
	 */
	public boolean makeAccusation(String murderCharacter, String murderRoom,
			String murderWeapon) {
		CaseFile accusation = new CaseFile(new CharacterCard(murderCharacter),
				new RoomCard(murderRoom), new WeaponCard(murderWeapon));

		Player currentPlayer = boardGame.getCurrentPlayer();
		boolean accusationResult = boardGame.makeAccusation(
				boardGame.getCurrentPlayer(), accusation);

		modelListener.showAccusationResult(currentPlayer.getCharacterName(),
				accusationResult);
		return accusationResult;
	}

	/**
	 * Receives player's suggestion and moves the suggested murder character to
	 * the suggested murder room.
	 *
	 * @param murderCharacter
	 *            Suggested character
	 * @param murderWeapon
	 *            Suggested Weapon
	 * @return The player that is supposed to show card to the current player
	 *         and current player if no one has the suggested cards.
	 */
	public Player makeSuggestion(String murderCharacter, String murderWeapon) {

		String murderRoom = ((Room) boardGame.getCurrentPlayer()
				.getPlayerLocation()).getRoomName();
		CaseFile suggestion = new CaseFile(new CharacterCard(murderCharacter),
				new RoomCard(murderRoom), new WeaponCard(murderWeapon));

		// Update view - Move the suggested character to the suggested room
		// Find the player/character that needs to be moved
		Player suggestedPlayer = new Player();
		for (int i = 0; i < boardGame.getPlayers().size(); i++) {
			if (boardGame.getPlayers().get(i).getCharacterName().getText()
					.equals(suggestion.getCharacterCard().getCardName())) {
				suggestedPlayer = boardGame.getPlayers().get(i);
			}
		}

		Point moveLocation = new Point();
		for (int i = 0; i < boardGame.getBoard().length; i++) {
			for (int j = 0; j < boardGame.getBoard()[i].length; j++) {
				if (boardGame.getBoard()[i][j] instanceof Room) {
					if (((Room) boardGame.getBoard()[i][j]).getRoomName()
							.equals(murderRoom)) {
						moveLocation = boardGame.getBoard()[i][j].getLocation();
					}
				}
			}
		}

		modelListener.movePlayer(suggestedPlayer.getCharacterName(),
				moveLocation);



		Player showCardPlayer = boardGame.makeSuggestion(
				boardGame.getCurrentPlayer(), suggestion);



		List<String> cards = new ArrayList<String>();
		for (int i = 0; i < showCardPlayer.getCards().size(); i++) {
			if (suggestion.getCharacterCard().equals(
					showCardPlayer.getCards().get(i))) {
				cards.add(showCardPlayer.getCards().get(i).getCardName());
			}

			if (suggestion.getRoomCard().equals(
					showCardPlayer.getCards().get(i))) {
				cards.add(showCardPlayer.getCards().get(i).getCardName());
			}

			if (suggestion.getWeaponCard().equals(
					showCardPlayer.getCards().get(i))) {
				cards.add(showCardPlayer.getCards().get(i).getCardName());
			}
		}

		// Update view - request the proper player to show card
		modelListener.requestSelectCardToShow(
				showCardPlayer.getCharacterName(), cards);

		return showCardPlayer;
	}

	/**
	 * Player shows card to the current player.
	 *
	 * @param fromCharacter
	 *            Player that shows the card
	 * @param showCard
	 *            The card to be shown
	 */
	public void showCard(CharacterEnum fromCharacter, String showCard) {
		// Update View
		modelListener.showCard(fromCharacter, boardGame
				.getCurrentPlayer().getCharacterName(), showCard);
	}

	/**
	 * Ends the current player's turn and advances to the next player.
	 */
	public void advanceTurn() {
		Player endPlayer = boardGame.getCurrentPlayer();
		boardGame.advanceTurn();
		Player startPlayer = boardGame.getCurrentPlayer();

		// Update View
		modelListener.advanceTurn(endPlayer.getCharacterName(), startPlayer.getCharacterName());
	}
}
