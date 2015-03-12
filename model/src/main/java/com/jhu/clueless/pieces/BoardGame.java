package com.jhu.clueless.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ELO
 *
 */
public class BoardGame {

	private Square[][] board;

	private List<Player> players = new ArrayList<Player>();
	// private List<Player> inactivePlayers = new ArrayList<Player>();

	/**
	 * Murder Case file.
	 */
	private CaseFile caseFile;

	/**
	 * Indicator of whether or not the game had started.
	 */
	private boolean gameStarted = false;

	/**
	 * Get 2 Dimension arrays that contains rooms and hallways.
	 *
	 * @return 2 Dimension arrays that contains rooms and hallways.
	 */
	public Square[][] getBoard() {
		return board;
	}

	/**
	 * Get List of players for the board game.
	 *
	 * @return List of players for the board game.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Set List of players for the board game.
	 *
	 * @param players
	 *            Set List of players for the board game.
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 * Indicator of whether or not the game had started.
	 *
	 * @return gameStarted Indicator of whether or not the game had started.
	 */
	public boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * Set whether or not the game had started.
	 *
	 * @param gameStarted
	 *            Indicator of whether or not the game had started.
	 */
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * For the class these it's a 5 by 5 board including rooms and hallways.
	 *
	 * @param boardWidth
	 *            Width of the board
	 * @param boardHeight
	 *            Height of the board
	 */
	public BoardGame(int boardWidth, int boardHeight) {
		this.board = new Square[boardWidth][boardHeight];
		setupBoard(boardWidth, boardHeight);
	}

	// Add rooms and hallways to the board based on board dimension
	private void setupBoard(int boardWidth, int boardHeight) {

		// Add rooms and hallways
		int roomCount = 0;
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {

				Point squareLocation = new Point(x, y);

				// if square is a room (index mod 2 = 0)
				if (x % 2 == 0 && y % 2 == 0) {

					// Calculate available destination locations for this room
					List<Point> destinationLocations = new ArrayList<Point>();
					if (y - 1 >= 0) {
						destinationLocations.add(new Point(x, y - 1));
					}
					if (y + 1 < boardHeight) {
						destinationLocations.add(new Point(x, y + 1));
					}
					if (x - 1 >= 0) {
						destinationLocations.add(new Point(x - 1, y));
					}
					if (x + 1 < boardWidth) {
						destinationLocations.add(new Point(x + 1, y));
					}

					// Corner rooms with secret passage has extra available
					// destination
					if ((x == 0 && y == 0) || (x == 0 && y == boardHeight - 1)
							|| (x == boardWidth - 1 && y == 0)
							|| (x == boardWidth - 1 && y == boardHeight - 1)) {
						int i = 0;
						int j = 0;

						if (x == 0)
							i = boardWidth - 1;

						if (x == boardWidth - 1)
							i = 0;

						if (y == 0)
							j = boardHeight - 1;

						if (y == boardHeight - 1)
							j = 0;

						destinationLocations.add(new Point(i, j));
					}

					// Create a room for that square block on the board
					Square square = new Room(squareLocation,
							destinationLocations,
							RoomEnum.values()[roomCount].getText());
					board[x][y] = square;
					roomCount++;
				}
				// if square is a hallway ( x mod 2 not equals to y mod 2 makes
				// it a hallway)
				else if ((x % 2) != (y % 2)) {

					// Calculate available destination locations for this
					// hallway
					List<Point> destinationLocations = new ArrayList<Point>();
					if ((y - 1 >= 0) && (x % 2 == 0) && ((y - 1) % 2 == 0)) {
						destinationLocations.add(new Point(x, y - 1));
					}
					if ((y + 1 < boardHeight) && (x % 2 == 0)
							&& ((y + 1) % 2 == 0)) {
						destinationLocations.add(new Point(x, y + 1));
					}
					if ((x - 1 >= 0) && ((x - 1) % 2 == 0) && (y % 2 == 0)) {
						destinationLocations.add(new Point(x - 1, y));
					}
					if ((x + 1 < boardWidth) && ((x + 1) % 2 == 0)
							&& (y % 2 == 0)) {
						destinationLocations.add(new Point(x + 1, y));
					}

					// Create a hallway for that square block on the board
					Square square = new Hallway(squareLocation,
							destinationLocations);
					board[x][y] = square;
				}
				// The square is not a room nor a hallway, it is an empty
				// unreachable square
				else {
					// Create an empty square for that square block on the board
					Square square = new EmptySquare(squareLocation);
					board[x][y] = square;
				}
			}
		}
	}

	/**
	 * Add an player to the board game.
	 *
	 * @param player
	 *            to be added to game
	 * @return Indicates if a player is added successfully
	 */
	public boolean addPlayer(Player player) {

		if (!gameStarted) {
			this.players.add(player);
			int x = player.getPlayerLocation().getLocation().x;
			int y = player.getPlayerLocation().getLocation().y;
			this.board[x][y].addPlayer(player);
			return true;
		} else {
			return false;
		}
	}

	private boolean dealCards() {

		if (players.size() >= 3 && players.size() <= 6) {
			// Build a list for each type of card
			List<CharacterCard> characterCards = new ArrayList<CharacterCard>();
			for (int i = 0; i < CharacterEnum.values().length; i++) {
				characterCards.add(new CharacterCard(CharacterEnum.values()[i]
						.getText()));
			}

			List<RoomCard> roomCards = new ArrayList<RoomCard>();
			for (int i = 0; i < RoomEnum.values().length; i++) {
				roomCards.add(new RoomCard(RoomEnum.values()[i].getText()));
			}

			List<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
			for (int i = 0; i < WeaponEnum.values().length; i++) {
				weaponCards
						.add(new WeaponCard(WeaponEnum.values()[i].getText()));
			}

			// Shuffle the cards and use the first card from each list as the
			// murder cards
			Collections.shuffle(characterCards);
			Collections.shuffle(roomCards);
			Collections.shuffle(weaponCards);
			setCaseFile(new CaseFile(characterCards.get(0), roomCards.get(0),
					weaponCards.get(0)));
			characterCards.remove(0);
			roomCards.remove(0);
			weaponCards.remove(0);

			// Mix all the remaining cards together and shuffle
			List<Card> cards = new ArrayList<Card>();
			cards.addAll(characterCards);
			cards.addAll(roomCards);
			cards.addAll(weaponCards);
			Collections.shuffle(cards);

			// Deal cards to each player
			for (int i = 0; i < cards.size(); i++) {
				players.get(i % players.size()).addCard(cards.get(i));
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Search for active player using character name.
	 *
	 * @param characterName
	 *            Character name of the player
	 * @return player
	 */
	public Player searchActivePlayer(String characterName) {
		Player player = new Player();

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isActive()
					&& players.get(i).getCharacterName().getText().equals(characterName)) {
				player = players.get(i);
				break;
			}
		}

		return player;
	}

	/**
	 * Search for inactive player using character name.
	 *
	 * @param characterName
	 *            Character name of the player
	 * @return player
	 */
	public Player searchInactivePlayer(String characterName) {
		Player player = new Player();

		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isActive()
					&& players.get(i).getCharacterName().equals(characterName)) {
				player = players.get(i);
				break;
			}
		}

		return player;
	}

	/**
	 * Starting the game.
	 *
	 * @return Indicates the game is successfully started
	 */
	public boolean startGame() {

		if (players.size() >= 3 && players.size() <= 6) {
			gameStarted = true;

			// Deal cards to the active players
			dealCards();

			// Create a list of all players
			List<Player> allPlayers = new ArrayList<Player>();
			for (int i = 0; i < CharacterEnum.values().length; i++) {
				Player player = new Player(
						"NA",
						CharacterEnum.values()[i],
						board[CharacterStartLocationEnum.values()[i].getPoint().x][CharacterStartLocationEnum
								.values()[i].getPoint().y], false);

				Player activePlayer = searchActivePlayer(CharacterEnum.values()[i].getText());
				if (player.equals(activePlayer)) {
					allPlayers.add(activePlayer);
				}
				else {
					allPlayers.add(player);
				}
			}

			players = allPlayers;

			// Set initial turn
			for (int i = 0; i < players.size(); i++) {
				players.get(i).setTurn(false);
			}
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).isActive()) {
					players.get(0).setTurn(true);
					break;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param player
	 *            Player to be moved
	 * @param toLocation
	 *            Destination location
	 */
	public void movePlayer(Player player, Square toLocation) {

		// Add player to the TO Location
		int toX = toLocation.getLocation().x;
		int toY = toLocation.getLocation().y;
		board[toX][toY].addPlayer(player);

		// Remove player from the FROM Location
		int fromX = player.getPlayerLocation().getLocation().x;
		int fromY = player.getPlayerLocation().getLocation().y;
		board[fromX][fromY].removePlayer(player);

		// Move player on the board
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(player)) {
				players.get(i).setPlayerLocation(toLocation);
			}
		}
	}

	/**
	 *
	 * @param player
	 *            player making the accusation
	 * @param accusation
	 *            accusation case file
	 * @return whether the accusation is correct or not
	 */
	public boolean makeAccusation(Player player, CaseFile accusation) {
		// If accusation is correct
		if (caseFile.equals(accusation)) {
			return true;
		}
		// If accusation is incorrect
		else {
			// Remove player from the the board
			//int x = player.getPlayerLocation().getLocation().x;
			//int y = player.getPlayerLocation().getLocation().y;
			//board[x][y].removePlayer(player);

			// Move player from active list to inactive list
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).equals(player)) {
					players.get(i).setTurn(false);
					players.get(i).setActive(false);
				}
			}
			return false;
		}
	}

	/**
	 * @return the caseFile
	 */
	public CaseFile getCaseFile() {
		return caseFile;
	}

	/**
	 * @param caseFile
	 *            the caseFile to set
	 */
	public void setCaseFile(CaseFile caseFile) {
		this.caseFile = caseFile;
	}

	/**
	 * Get the current player.
	 *
	 * @return Current player
	 */
	public Player getCurrentPlayer() {
		Player currentPlayer = new Player();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isTurn() && players.get(i).isActive()) {
				currentPlayer = players.get(i);
			}
		}
		return currentPlayer;
	}

	/**
	 * Ends the current player's turn and advances to the next player.
	 */
	public void advanceTurn() {
		// Find the index of the current player
		int currentTurn = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isTurn() && players.get(i).isActive()) {
				currentTurn = i;
				break;
			}
		}

		// Ends the current player's turn
		players.get(currentTurn).setTurn(false);

		// Locate the next active player and advance the turn
		boolean foundNextActivePlayer = false;
		while (!foundNextActivePlayer) {
			if (currentTurn == players.size() - 1) {
				currentTurn = 0;
			} else {
				currentTurn++;
			}

			if (players.get(currentTurn).isActive()) {
				players.get(currentTurn).setTurn(true);
				foundNextActivePlayer = true;
			}
		}
	}

	/**
	 * Current player makes a suggestion.
	 *
	 * @param currentPlayer
	 *            Current player making the suggestion
	 * @param suggestion
	 *            Suggestion case file
	 * @return The player that is supposed to show card to the current player
	 *         and current player if no one has the suggested cards.
	 */
	public Player makeSuggestion(Player currentPlayer, CaseFile suggestion) {

		if (!(currentPlayer.getPlayerLocation() instanceof Room)) {
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Find the player/character that needs to be moved
		Player suggestedPlayer = new Player();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getCharacterName().getText()
					.equals(suggestion.getCharacterCard().getCardName())) {
				suggestedPlayer = players.get(i);
			}
		}

		// Move the suggested character to the suggested room
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if (board[i][j] instanceof Room) {
					if (((Room) board[i][j]).getRoomName().equals(
							suggestion.getRoomCard().getCardName())) {
						this.movePlayer(suggestedPlayer, board[i][j]);
					}
				}
			}
		}

		// Special case if suggestion case file matches murder case file
		if (suggestion.equals(caseFile)) {
			return currentPlayer;
		}


		// Locate the player that is supposed to show a card to the current
		// player
		// Find the index of the current player
		int currentTurn = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isTurn()) {
				currentTurn = i;
				break;
			}
		}

		// Locate the next active player and advance the turn
		boolean foundNextPlayer = false;
		while (!foundNextPlayer) {
			if (currentTurn == players.size() - 1) {
				currentTurn = 0;
			} else {
				currentTurn++;
			}

			// Try to find match between next player's cards and the cards in
			// the suggestion case file
			for (int i = 0; i < players.get(currentTurn).getCards().size(); i++) {
				if (players.get(currentTurn).getCards().get(i)
						.equals(suggestion.getCharacterCard())) {
					foundNextPlayer = true;
					break;
				}

				if (players.get(currentTurn).getCards().get(i)
						.equals(suggestion.getRoomCard())) {
					foundNextPlayer = true;
					break;
				}

				if (players.get(currentTurn).getCards().get(i)
						.equals(suggestion.getWeaponCard())) {
					foundNextPlayer = true;
					break;
				}
			}
		}

		return players.get(currentTurn);

	}
}
