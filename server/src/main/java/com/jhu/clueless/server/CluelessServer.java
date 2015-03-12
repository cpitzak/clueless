package com.jhu.clueless.server;

import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;
import com.jhu.clueless.client.ConsoleInterface;
import com.jhu.clueless.client.ConsolePlayerInterface;
import com.jhu.clueless.client.Network;
import com.jhu.clueless.gameplay.GamePlay;
import com.jhu.clueless.interfaces.ModelListener;
import com.jhu.clueless.pieces.Card;
import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.CharacterStartLocationEnum;
import com.jhu.clueless.pieces.GameStateEnum;
import com.jhu.clueless.pieces.Player;
import com.jhu.clueless.pieces.Room;
import com.jhu.clueless.pieces.RoomEnum;
import com.jhu.clueless.pieces.WeaponEnum;

/**
 * Defines the Clueless Server.
 */
public class CluelessServer {
	Server server;
	List<ConsolePlayer> consolePlayers = new ArrayList<ConsolePlayer>();
	GamePlay gamePlay;
	List<String> showCardsOptions;

	public CluelessServer() throws IOException {
		server = new Server() {
			@Override
			protected Connection newConnection() {
				ConsolePlayer consolePlayer = null;
				if (consolePlayers.isEmpty()) {
					consolePlayer = new ConsolePlayer(true);
				} else {
					consolePlayer = new ConsolePlayer(false);
				}
				consolePlayers.add(consolePlayer);
				gamePlay = new GamePlay(consolePlayer);
				return consolePlayer;
			}
		};

		Network.register(server);
		server.addListener(new Listener() {
			@Override
			public void disconnected(Connection connection) {
				ConsolePlayer consolePlayer = (ConsolePlayer) connection;
				consolePlayers.remove(consolePlayer);
				if (consolePlayer.username != null) {
					String consoleText = consolePlayer.username + " disconnected.";
					for (ConsolePlayer tempPlayer : consolePlayers) {
						tempPlayer.consoleFrame.addText(consoleText);
					}
					updateUserNames();
				}
			}
		});
		server.bind(Network.port);
		server.start();

		JFrame frame = new JFrame("Clueless Server");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent evt) {
				server.stop();
			}
		});
		frame.getContentPane().add(new JLabel("Close to stop the Cluless Server."));
		frame.setSize(320, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	void updateUserNames() {
		List<String> consoleUserNames = new ArrayList<String>(consolePlayers.size());
		List<String> characterList = new ArrayList<String>(consolePlayers.size());
		for (ConsolePlayer consolePlayer : consolePlayers) {
			if (consolePlayer.username != null) {
				consoleUserNames.add(consolePlayer.username);
				characterList.add(consolePlayer.character);
			}
		}
		String[] usernames = consoleUserNames.toArray(new String[consoleUserNames.size()]);
		String[] characters = characterList.toArray(new String[characterList.size()]);
		for (ConsolePlayer consolePlayer : consolePlayers) {
			consolePlayer.consoleFrame.setUserNames(usernames, characters);
		}
	}

	/**
	 * Defines the console player.
	 */
	class ConsolePlayer extends Connection implements ConsolePlayerInterface, ModelListener {
		ConsoleInterface consoleFrame;
		String username;
		String character;
		GameStateEnum gameState;

		public GameStateEnum getGameState() {
			return gameState;
		}

		public String getCharacter() {
			return character;
		}

		final boolean isModerator;

		public ConsolePlayer(boolean isModerator) {
			this.isModerator = isModerator;
			ObjectSpace objectSpace = new ObjectSpace(this);
			objectSpace.register(Network.CONSOLE_PLAYER, this);
			consoleFrame = ObjectSpace.getRemoteObject(this, Network.CONSOLE_TEXT_AREA, ConsoleInterface.class);
			RemoteObject remoteConsoleFrameObject = (RemoteObject) consoleFrame;
			remoteConsoleFrameObject.setResponseTimeout(Network.reponseTime);
		}

		public boolean isModerator() {
			return isModerator;
		}

		public boolean isTurn() {
			String userName = gamePlay.getBoardGame().getCurrentPlayer().getUserName();
			if (userName == null) {
				return false;
			}
			return userName.equals(username);
		}

		public ConsoleInterface getConsoleFrame() {
			return consoleFrame;
		}

		public String getUserName() {
			return username;
		}

		public void registerUserName(String username) {
			if (this.username != null) {
				return;
			}
			if (username == null) {
				return;
			}
			username = username.trim();
			if (username.length() == 0) {
				return;
			}
			this.username = username;
			String consoleText = username + " connected.";
			for (ConsolePlayer consolePlayer : consolePlayers) {
				if (consolePlayer != this) {
					consolePlayer.consoleFrame.addText(consoleText);
				}
			}

			this.getConsoleFrame().addText("Welcome to Clue-less!");
			this.getConsoleFrame().addText("");
			this.getConsoleFrame().addText("Please select a character");
			CharacterEnum[] characters = CharacterEnum.values();
			for (int i = 0; i < CharacterEnum.values().length; i++) {
				this.getConsoleFrame().addText((i + 1) + " - " + characters[i].getText());
			}
			updateUserNames();
			gameState = GameStateEnum.addPlayer;
		}

		public void sendText(String consoleText) {
			if (this.username == null) {
				return;
			}
			if (consoleText == null) {
				return;
			}
			consoleText = consoleText.trim();
			if (consoleText.length() == 0) {
				return;
			}

			// game started
			if (!isTurn() && gamePlay.getBoardGame().isGameStarted()) {
				consoleFrame.addText("Its not your turn!");
				return;
			}

			consoleText = this.username + ": " + consoleText;
			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.consoleFrame.addText(consoleText);
			}
		}

		public void addPlayer(String userName, String consoleText) {
			if (this.username == null) {
				return;
			}
			if (consoleText == null) {
				return;
			}
			consoleText = consoleText.trim();
			if (consoleText.length() == 0) {
				return;
			}

			int characterChoice = Integer.parseInt(consoleText);
			if (characterChoice < 1 || characterChoice > CharacterEnum.values().length) {
				consoleFrame.addText("Invalid character choice!");
				return;
			} else {
				this.character = CharacterEnum.values()[characterChoice - 1].getText();
				gamePlay.addPlayer(userName, CharacterEnum.values()[characterChoice - 1],
				        CharacterStartLocationEnum.values()[characterChoice - 1].getPoint());
			}

			String consoleTextOutput = this.username + ": " + this.character;
			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.consoleFrame.addText(consoleTextOutput);
			}
			updateUserNames();

			if (this.isModerator) {
				consoleFrame.addText("Begin Game?");
				consoleFrame.addText("1 - Yes");
			}

			this.gameState = GameStateEnum.waitingForStartGame;
			this.setTimeout(Network.reponseTime);
		}

		public void startGame(String consoleText) {
			if (this.username == null) {
				return;
			}
			if (consoleText == null) {
				return;
			}
			consoleText = consoleText.trim();
			if (consoleText.length() == 0) {
				return;
			}

			if (this.isModerator) {
				int choice = Integer.parseInt(consoleText);
				if (choice != 1) {
					consoleFrame.addText("Invalid choice!");
					return;
				} else {

					gamePlay.startGame();
					String currentCharacter = gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText();

					for (ConsolePlayer consolePlayer : consolePlayers) {
						if (consolePlayer.character == currentCharacter) {
							consolePlayer.consoleFrame.addText("Game started");
							consolePlayer.gameState = GameStateEnum.moving;
							showMoves();
						} else {
							consolePlayer.consoleFrame.addText("Game started");
							consolePlayer.gameState = GameStateEnum.waiting;
						}
					}
				}
			}

		}

		public void showMoves() {
			String currentCharacter = gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText();

			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.consoleFrame.addText(currentCharacter + "'s turn");
				if (consolePlayer.character == currentCharacter) {
					consolePlayer.consoleFrame.addText("This is your turn");
					consolePlayer.gameState = GameStateEnum.moving;

					List<Point> possibleMoves = new ArrayList<Point>();
					possibleMoves = gamePlay.getPossibleMoves();

					if (possibleMoves.size() == 0) {
						consolePlayer.consoleFrame.addText("You have no where to move to. Turn ended");
						gamePlay.advanceTurn();
					} else {
						consolePlayer.consoleFrame.addText("Choose one of the following destinations");
						for (int i = 0; i < possibleMoves.size(); i++) {
							consolePlayer.consoleFrame.addText((i + 1) + " - " + possibleMoves.get(i).toString());
						}
					}

				} else {
					consolePlayer.consoleFrame.addText("Please wait for your turn");
					consolePlayer.gameState = GameStateEnum.waiting;
				}

			}

		}

		public void movePlayer(String consoleText) {
			String currentCharacter = gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText();
			if (this.username == null) {
				return;
			}
			if (consoleText == null) {
				return;
			}
			consoleText = consoleText.trim();
			if (consoleText.length() == 0) {
				return;
			}

			List<Point> possibleMoves = new ArrayList<Point>();
			possibleMoves = gamePlay.getPossibleMoves();

			int choice = Integer.parseInt(consoleText);
			if (choice < 1 || choice > possibleMoves.size()) {
				consoleFrame.addText("Invalid choice!");
				return;
			} else {
				gamePlay.movePlayer(possibleMoves.get(choice - 1));

				for (ConsolePlayer consolePlayer : consolePlayers) {
					consolePlayer.consoleFrame.addText(currentCharacter + " moved to " + possibleMoves.get(choice - 1));

					if (consolePlayer.character == currentCharacter) {
						consolePlayer.gameState = GameStateEnum.chooseAction;
						consolePlayer.consoleFrame.addText("Please choose one of the following actions");
						consolePlayer.consoleFrame.addText("1 - Make a suggestion");
						consolePlayer.consoleFrame.addText("2 - Make an accusation");
						consolePlayer.consoleFrame.addText("3 - End turn");
					} else {
						consolePlayer.gameState = GameStateEnum.waiting;
					}
				}
			}
		}

		public void chooseAction(String consoleText) {
			if (this.username == null) {
				return;
			}
			if (consoleText == null) {
				return;
			}
			consoleText = consoleText.trim();
			if (consoleText.length() == 0) {
				return;
			}

			int choice = Integer.parseInt(consoleText);
			if (choice < 1 || choice > 3) {
				consoleFrame.addText("Invalid choice!");
				return;
			} else {
				switch (choice) {

				// Suggestion
				case 1:
					if (gamePlay.getBoardGame().getCurrentPlayer().getPlayerLocation().getClass().getSimpleName()
					        .equals("Room")) {
						showSuggestionOptions();
					} else {
						consoleFrame.addText("You can't make a suggestion since you're not in a room!");
					}

					break;

				// Accusation
				case 2:
					showAccusationOptions();
					break;

				// End turn
				case 3:
					endTurn();
					break;

				default:
					break;

				}

			}
		}

		public void cheatMode() {

			consoleFrame.addText(gamePlay.getBoardGame().getCaseFile().getCharacterCard().getCardName() + ","
			        + gamePlay.getBoardGame().getCaseFile().getRoomCard().getCardName() + ","
			        + gamePlay.getBoardGame().getCaseFile().getWeaponCard().getCardName());
		}

		public void makeAccusation(String message) {
			String[] choices = message.split(",");

			if (choices.length != 3) {
				consoleFrame.addText("Invalid input format");
				return;
			}

			if (Integer.parseInt(choices[0]) < 1 || Integer.parseInt(choices[0]) > CharacterEnum.values().length) {
				consoleFrame.addText("Invalid character choice");
				return;
			}

			if (Integer.parseInt(choices[1]) < 1 || Integer.parseInt(choices[1]) > RoomEnum.values().length) {
				consoleFrame.addText("Invalid room choice");
				return;
			}

			if (Integer.parseInt(choices[2]) < 1 || Integer.parseInt(choices[2]) > WeaponEnum.values().length) {
				consoleFrame.addText("Invalid weapon choice");
				return;
			}

			String murderCharacter = CharacterEnum.values()[Integer.parseInt(choices[0]) - 1].getText();

			String murderRoom = RoomEnum.values()[Integer.parseInt(choices[1]) - 1].getText();

			String murderWeapon = WeaponEnum.values()[Integer.parseInt(choices[2]) - 1].getText();

			String currentCharacter = gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText();
			boolean win = gamePlay.makeAccusation(murderCharacter, murderRoom, murderWeapon);

			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.consoleFrame.addText(currentCharacter + " accused " + murderCharacter + ", " + murderRoom
				        + ", " + murderWeapon);
				if (win) {
					consolePlayer.consoleFrame.addText(currentCharacter + " won the game!");
				} else {
					consolePlayer.consoleFrame.addText(currentCharacter + " lost the game!");
				}
				consolePlayer.consoleFrame.addText("Game ended...");
				consolePlayer.consoleFrame.addText("Thank you for playing Clue-less!");
			}
		}

		public void makeSuggestion(String consoleText) {
			String[] choices = consoleText.split(",");

			if (choices.length != 2) {
				consoleFrame.addText("Invalid input format");
				return;
			}

			if (Integer.parseInt(choices[0]) < 1 || Integer.parseInt(choices[0]) > CharacterEnum.values().length) {
				consoleFrame.addText("Invalid character choice");
				return;
			}

			if (Integer.parseInt(choices[1]) < 1 || Integer.parseInt(choices[1]) > WeaponEnum.values().length) {
				consoleFrame.addText("Invalid weapon choice");
				return;
			}

			String murderCharacter = CharacterEnum.values()[Integer.parseInt(choices[0]) - 1].getText();
			String murderWeapon = WeaponEnum.values()[Integer.parseInt(choices[1]) - 1].getText();

			String murderRoom = ((Room) gamePlay.getBoardGame().getCurrentPlayer().getPlayerLocation()).getRoomName();

			String currentCharacter = gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText();

			Player showCardCharacter = gamePlay.makeSuggestion(murderCharacter, murderWeapon);

			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.consoleFrame.addText(currentCharacter + " suggested " + murderCharacter + ", "
				        + murderRoom + ", " + murderWeapon);

				if (consolePlayer.getCharacter().equals(currentCharacter)) {
					consolePlayer.consoleFrame.addText("Waiting for " + showCardCharacter.getCharacterName().getText()
					        + " to show you a card");
					consolePlayer.gameState = GameStateEnum.waitingSeeCard;
				}

				else if (consolePlayer.getCharacter().equals(showCardCharacter.getCharacterName().getText())) {
					consolePlayer.gameState = GameStateEnum.showCard;
					consolePlayer.consoleFrame.addText("Please show " + currentCharacter
					        + " one of the following cards:");

					List<String> cards = new ArrayList<String>();
					for (int i = 0; i < showCardCharacter.getCards().size(); i++) {
						if (murderCharacter.equals(showCardCharacter.getCards().get(i).getCardName())) {
							cards.add(showCardCharacter.getCards().get(i).getCardName());
						}

						if (murderRoom.equals(showCardCharacter.getCards().get(i).getCardName())) {
							cards.add(showCardCharacter.getCards().get(i).getCardName());
						}

						if (murderWeapon.equals(showCardCharacter.getCards().get(i).getCardName())) {
							cards.add(showCardCharacter.getCards().get(i).getCardName());
						}
					}

					for (int i = 0; i < cards.size(); i++) {
						consolePlayer.consoleFrame.addText((i + 1) + " - " + cards.get(i));
					}

					showCardsOptions = cards;
				}

				else {
					consolePlayer.consoleFrame.addText("Waiting for " + showCardCharacter.getCharacterName().getText()
					        + " to show " + currentCharacter + " card");
					consolePlayer.gameState = GameStateEnum.waiting;
				}
			}
		}

		public void showAccusationOptions() {
			for (ConsolePlayer consolePlayer : consolePlayers) {
				if (consolePlayer.character == gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText()) {
					showPlayerCards(consolePlayer.character);
					consolePlayer.gameState = GameStateEnum.accussing;
					consolePlayer.consoleFrame.addText("Please select one card from each of the 3");
					consolePlayer.consoleFrame.addText("cateogries using the format");
					consolePlayer.consoleFrame.addText("<Character Choice>,<Room Choice>,<Weapon Choice>");
					consolePlayer.consoleFrame.addText("Example: 3,2,6");

					consolePlayer.consoleFrame.addText("Character Choice:");
					CharacterEnum[] characters = CharacterEnum.values();
					for (int i = 0; i < CharacterEnum.values().length; i++) {
						this.getConsoleFrame().addText((i + 1) + " - " + characters[i].getText());
					}

					consolePlayer.consoleFrame.addText("Room Choice:");
					RoomEnum[] rooms = RoomEnum.values();
					for (int i = 0; i < RoomEnum.values().length; i++) {
						this.getConsoleFrame().addText((i + 1) + " - " + rooms[i].getText());
					}

					consolePlayer.consoleFrame.addText("Weapon Choice:");
					WeaponEnum[] weapons = WeaponEnum.values();
					for (int i = 0; i < WeaponEnum.values().length; i++) {
						this.getConsoleFrame().addText((i + 1) + " - " + weapons[i].getText());
					}
				} else {
					consolePlayer.gameState = GameStateEnum.waiting;
				}
			}

		}

		public void showCard(String consoleText) {
			if (this.username == null) {
				return;
			}
			if (consoleText == null) {
				return;
			}
			consoleText = consoleText.trim();
			if (consoleText.length() == 0) {
				return;
			}

			int choice = Integer.parseInt(consoleText);
			if (choice < 1 || choice > showCardsOptions.size()) {
				consoleFrame.addText("Invalid choice!");
				return;
			}

			for (ConsolePlayer consolePlayer : consolePlayers) {
				if (consolePlayer.character == gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText()) {
					consolePlayer.gameState = GameStateEnum.waitingSeeCard;
					consolePlayer.consoleFrame.addText("Card " + showCardsOptions.get(choice - 1) + " shown to you");
				} else {
					consolePlayer.gameState = GameStateEnum.waiting;
				}
			}

			endTurn();
		}

		public void showSuggestionOptions() {
			for (ConsolePlayer consolePlayer : consolePlayers) {
				if (consolePlayer.character == gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText()) {
					showPlayerCards(consolePlayer.character);
					consolePlayer.gameState = GameStateEnum.suggesting;
					consolePlayer.consoleFrame.addText("Please select one card from each of the 2");
					consolePlayer.consoleFrame.addText("cateogries using the format");
					consolePlayer.consoleFrame.addText("<Character Choice>,<Weapon Choice>");
					consolePlayer.consoleFrame.addText("Example: 2,6");

					consolePlayer.consoleFrame.addText("Character Choice:");
					CharacterEnum[] characters = CharacterEnum.values();
					for (int i = 0; i < CharacterEnum.values().length; i++) {
						this.getConsoleFrame().addText((i + 1) + " - " + characters[i].getText());
					}

					consolePlayer.consoleFrame.addText("Weapon Choice:");
					WeaponEnum[] weapons = WeaponEnum.values();
					for (int i = 0; i < WeaponEnum.values().length; i++) {
						this.getConsoleFrame().addText((i + 1) + " - " + weapons[i].getText());
					}
				} else {
					consolePlayer.gameState = GameStateEnum.waiting;
				}
			}
		}

		public void endTurn() {
			for (ConsolePlayer consolePlayer : consolePlayers) {
				if (consolePlayer.character == gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText()) {
					consolePlayer.consoleFrame.addText("Your turn ended");
					consolePlayer.gameState = GameStateEnum.waiting;
				}
			}

			gamePlay.advanceTurn();

			for (ConsolePlayer consolePlayer : consolePlayers) {
				if (consolePlayer.character == gamePlay.getBoardGame().getCurrentPlayer().getCharacterName().getText()) {
					consolePlayer.gameState = GameStateEnum.moving;
					showMoves();
				} else {
					consolePlayer.gameState = GameStateEnum.waiting;
				}
			}
		}

		public void showPlayerCards(String character) {
			List<Card> cards = new ArrayList<Card>();
			for (Player consolePlayer : gamePlay.getBoardGame().getPlayers()) {
				if (consolePlayer.getCharacterName().getText().equals(character)) {
					cards = consolePlayer.getCards();
					break;
				}
			}

			for (ConsolePlayer consolePlayer : consolePlayers) {
				if (consolePlayer.character == character) {
					consolePlayer.consoleFrame.addText("You carry the following cards:");
					for (Card card : cards) {
						consolePlayer.consoleFrame.addText(card.getCardName());
					}
				}
			}
		}

		public void addPlayer(CharacterEnum characterName, String userName, Point startLocation) {
			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.getConsoleFrame().addPlayer(characterName, userName, startLocation);
			}
		}

		public void startGame(Map<CharacterEnum, List<Card>> playerCardsMap) {
			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.getConsoleFrame().startGame(playerCardsMap);
			}
		}

		public void showPossibleMoves(CharacterEnum characterName, List<Point> points) {
			// TODO Auto-generated method stub

		}

		public void movePlayer(CharacterEnum characterName, Point point) {
			for (ConsolePlayer consolePlayer : consolePlayers) {
				consolePlayer.getConsoleFrame().movePlayer(characterName, point);
			}
		}

		public void advanceTurn(CharacterEnum endCharacterName, CharacterEnum startCharacterName) {
			// TODO Auto-generated method stub

		}

		public void showAccusationResult(CharacterEnum characterName, Boolean win) {
			// TODO Auto-generated method stub

		}

		public void requestSelectCardToShow(CharacterEnum characterName, List<String> cards) {
			// TODO Auto-generated method stub

		}

		public void showCard(CharacterEnum fromCharacterName, CharacterEnum toCharacterName, String showCard) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Starts the clueless server.
	 *
	 * @param args
	 *            unused args
	 * @throws IOException
	 *             throws ioexception
	 */
	public static void main(String[] args) throws IOException {
		new CluelessServer();
	}
}
