/**
 *
 */
package com.jhu.clueless.gameplay;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.CharacterStartLocationEnum;
import com.jhu.clueless.pieces.Player;
import com.jhu.clueless.pieces.Room;
import com.jhu.clueless.pieces.RoomEnum;
import com.jhu.clueless.pieces.WeaponEnum;

/**
 * @author ELO
 *
 */
public class GamePlayTextUIDriver {

	private static GamePlay gamePlay = new GamePlay(new ModelListenerImpl());

	/**
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));

		System.out.println("Welcome to Clue-less!\n");

		int choice = 0;
		boolean inputValid = false;
		do {
			System.out.println("Select one of the following options:");
			System.out.println("1 - Start a new game");
			System.out.println("2 - Quit");

			try {
				choice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (choice < 1 || choice > 2) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		switch (choice) {
		case 1:
			createGame();
			break;
		case 2:
			quitGame();
			break;
		default:
			quitGame();
			break;
		}
	}

	private static void createGame() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));

		int choice = 0;
		boolean inputValid = false;
		do {
			System.out.println("Select one of the following options:");
			System.out.println("1 - Add a player");
			System.out.println("2 - Start game");
			System.out.println("3 - Quit");

			try {
				choice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (choice < 1 || choice > 3) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		switch (choice) {
		case 1:
			addPlayer();
			break;
		case 2:
			startGame();
			break;
		case 3:
			quitGame();
			break;
		default:
			quitGame();
			break;
		}
	}

	private static void addPlayer() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));

		System.out.println("Please enter player's user name");
		String userName = "";
		try {
			userName = stdin.readLine();
		} catch (IOException e) {
			System.out.println("Invalid input");
		}

		System.out.println("Please select a character");
		for (int i = 0; i < CharacterEnum.values().length; i++) {
			System.out.println((i + 1) + " - "
					+ CharacterEnum.values()[i].getText());
		}
		int choice = 0;
		boolean inputValid = false;
		do {
			try {
				choice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (choice < 1 || choice > CharacterEnum.values().length) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		gamePlay.addPlayer(userName, CharacterEnum.values()[choice - 1],
				CharacterStartLocationEnum.values()[choice - 1].getPoint());

		createGame();
	}

	private static void startGame() {
		gamePlay.startGame();

		playTurn();
	}

	private static void playTurn() {

		boolean endGame = false;
		do {
			List<Point> possibleMoves = new ArrayList<Point>();
			possibleMoves = gamePlay.getPossibleMoves();

			if (possibleMoves.size() == 0) {
				gamePlay.advanceTurn();
			} else {
				System.out.println("Choose one of the following destinations");
				for (int i = 0; i < possibleMoves.size(); i++) {
					System.out.println((i + 1) + " - "
							+ possibleMoves.get(i).toString());
				}

				BufferedReader stdin = new BufferedReader(
						new InputStreamReader(System.in));

				int moveChoice = 0;
				boolean inputValid = false;
				do {
					try {
						moveChoice = Integer.parseInt(stdin.readLine());
					} catch (NumberFormatException e) {
						System.out.println("Invalid input");
					} catch (IOException e) {
						System.out.println("Invalid input");
					}

					if (moveChoice < 1 || moveChoice > possibleMoves.size()) {
						System.out.println("Invalid input");
					} else {
						inputValid = true;
					}

				} while (!inputValid);

				gamePlay.movePlayer(possibleMoves.get(moveChoice - 1));

				System.out
						.println("Please choose from one of the follow options:");
				System.out.println("1 - Make a suggestion");
				System.out.println("2 - Make an accusation");
				System.out.println("3 - End turn");

				int actionChoice = 0;
				inputValid = false;
				do {
					try {
						actionChoice = Integer.parseInt(stdin.readLine());
					} catch (NumberFormatException e) {
						System.out.println("Invalid input");
					} catch (IOException e) {
						System.out.println("Invalid input");
					}

					if (actionChoice < 1 || actionChoice > 3) {
						System.out.println("Invalid input");
					} else {
						inputValid = true;
					}

				} while (!inputValid);

				switch (actionChoice) {
				case 1:
					if (gamePlay.getBoardGame().getCurrentPlayer()
							.getPlayerLocation() instanceof Room) {
						makeSuggestion();
					} else {
						System.out.println("Player is not in a room.");
						gamePlay.advanceTurn();
					}

					break;
				case 2:
					makeAccusation();
					endGame = true;
					break;
				case 3:
					gamePlay.advanceTurn();
					break;
				default:
					quitGame();
					break;
				}
			}
		} while (!endGame);

	}

	private static void makeAccusation() {

		System.out.println("Please suggest one of the follow characters");
		for (int i = 0; i < CharacterEnum.values().length; i++) {
			System.out.println((i + 1) + " - "
					+ CharacterEnum.values()[i].getText());
		}

		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));

		int characterChoice = 0;
		boolean inputValid = false;
		do {
			try {
				characterChoice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (characterChoice < 1
					|| characterChoice > CharacterEnum.values().length) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		System.out.println("Please suggest one of the follow rooms");

		for (int i = 0; i < RoomEnum.values().length; i++) {
			System.out
					.println((i + 1) + " - " + RoomEnum.values()[i].getText());
		}

		int roomChoice = 0;
		inputValid = false;
		do {
			try {
				roomChoice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (roomChoice < 1 || roomChoice > RoomEnum.values().length) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		System.out.println("Please suggest one of the follow weapons");

		for (int i = 0; i < WeaponEnum.values().length; i++) {
			System.out.println((i + 1) + " - "
					+ WeaponEnum.values()[i].getText());
		}

		int weaponChoice = 0;
		inputValid = false;
		do {
			try {
				weaponChoice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (weaponChoice < 1 || weaponChoice > WeaponEnum.values().length) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		String murderCharacter = CharacterEnum.values()[characterChoice - 1]
				.getText();
		String murderRoom = RoomEnum.values()[roomChoice - 1].getText();
		String murderWeapon = WeaponEnum.values()[weaponChoice - 1].getText();
		gamePlay.makeAccusation(murderCharacter, murderRoom, murderWeapon);

	}

	private static void makeSuggestion() {

		System.out.println("Please suggest one of the follow characters");
		for (int i = 0; i < CharacterEnum.values().length; i++) {
			System.out.println((i + 1) + " - "
					+ CharacterEnum.values()[i].getText());
		}

		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));

		int characterChoice = 0;
		boolean inputValid = false;
		do {
			try {
				characterChoice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (characterChoice < 1
					|| characterChoice > CharacterEnum.values().length) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		System.out.println("Please suggest one of the follow weapons");

		for (int i = 0; i < WeaponEnum.values().length; i++) {
			System.out.println((i + 1) + " - "
					+ WeaponEnum.values()[i].getText());
		}

		int weaponChoice = 0;
		inputValid = false;
		do {
			try {
				weaponChoice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (weaponChoice < 1 || weaponChoice > WeaponEnum.values().length) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		Player showCardPlayer = gamePlay.makeSuggestion(
				CharacterEnum.values()[characterChoice - 1].getText(),
				WeaponEnum.values()[weaponChoice - 1].getText());

		int showCardChoice = 0;
		inputValid = false;
		do {
			try {
				showCardChoice = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			} catch (IOException e) {
				System.out.println("Invalid input");
			}

			if (showCardChoice < 1 || showCardChoice > 3) {
				System.out.println("Invalid input");
			} else {
				inputValid = true;
			}

		} while (!inputValid);

		List<String> cards = new ArrayList<String>();
		for (int i = 0; i < showCardPlayer.getCards().size(); i++) {
			if (CharacterEnum.values()[characterChoice - 1].getText().equals(
					showCardPlayer.getCards().get(i).getCardName())) {
				cards.add(showCardPlayer.getCards().get(i).getCardName());
			}
			Room currentRoom = (Room) gamePlay.getBoardGame()
					.getCurrentPlayer().getPlayerLocation();

			if (currentRoom.getRoomName().equals(
					showCardPlayer.getCards().get(i).getCardName())) {
				cards.add(showCardPlayer.getCards().get(i).getCardName());
			}

			if (WeaponEnum.values()[weaponChoice - 1].getText().equals(
					showCardPlayer.getCards().get(i).getCardName())) {
				cards.add(showCardPlayer.getCards().get(i).getCardName());
			}
		}

		gamePlay.showCard(showCardPlayer.getCharacterName(),
				cards.get(showCardChoice - 1));

		gamePlay.advanceTurn();

	}

	private static void quitGame() {
		System.out.println("Thank you for playing Clue-less! Goodbye!\n");
	}

}
