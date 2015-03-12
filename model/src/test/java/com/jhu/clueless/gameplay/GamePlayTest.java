package com.jhu.clueless.gameplay;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.jhu.clueless.pieces.CaseFile;
import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.CharacterStartLocationEnum;
import com.jhu.clueless.pieces.Player;
import com.jhu.clueless.pieces.Room;
import com.jhu.clueless.pieces.WeaponEnum;


/**
 *
 * @author ELO
 *
 */
public class GamePlayTest {

	/**
	 * Driver to test Game Play.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {

		testUpdateView();

		/*System.out.println("Test Add Players");
		testAddPlayers();
		System.out
		.println("======================================================");*/

		/*System.out.println("Test Deal Cards");
		testDealCards();
		System.out
		.println("======================================================");

		System.out.println("Test Make Accusation");
		testMakeAccusation();
		System.out
		.println("======================================================");

		System.out.println("Test Make Suggestion");
		testMakeSuggestion();
		System.out
		.println("======================================================");*/
	}

	private static void testUpdateView() {
		GamePlay gamePlay = new GamePlay(new ModelListenerImpl());

		gamePlay.addPlayer("Clint", CharacterEnum.WHITE, CharacterStartLocationEnum.WHITE.getPoint());
		gamePlay.addPlayer("James", CharacterEnum.SCARLET, CharacterStartLocationEnum.SCARLET.getPoint());
		gamePlay.addPlayer("Ethan", CharacterEnum.GREEN, CharacterStartLocationEnum.GREEN.getPoint());

		gamePlay.startGame();

		gamePlay.getPossibleMoves();

		gamePlay.movePlayer(new Point(0, 4));

		System.out.println("Current player suggested "
		+ ((Room) (gamePlay.getBoardGame().getCurrentPlayer().getPlayerLocation())).getRoomName()
		+ ", "  + CharacterEnum.WHITE.getText()
		+ ", " + WeaponEnum.REVOLVER.getText());

		Player showPlayer = gamePlay.makeSuggestion(CharacterEnum.WHITE.getText(), WeaponEnum.REVOLVER.getText());

		gamePlay.showCard(showPlayer.getCharacterName(), WeaponEnum.REVOLVER.getText());

		gamePlay.advanceTurn();

		gamePlay.getPossibleMoves();

		gamePlay.movePlayer(new Point(1, 4));

		gamePlay.advanceTurn();

		gamePlay.getPossibleMoves();

		gamePlay.movePlayer(new Point(4, 0));

		System.out.println("Current player suggested "
		+ ((Room) (gamePlay.getBoardGame().getCurrentPlayer().getPlayerLocation())).getRoomName()
		+ ", "  + CharacterEnum.MUSTARD.getText()
		+ ", " + WeaponEnum.KNIFE.getText());

		showPlayer = gamePlay.makeSuggestion(CharacterEnum.MUSTARD.getText(), WeaponEnum.KNIFE.getText());

		gamePlay.showCard(showPlayer.getCharacterName(), WeaponEnum.KNIFE.getText());

		gamePlay.advanceTurn();

		gamePlay.getPossibleMoves();

		CaseFile caseFile = gamePlay.getBoardGame().getCaseFile();
		/*System.out.println("Current player accused "
				+ caseFile.getCharacterCard().getCardName()
				+ ", "  + caseFile.getRoomCard().getCardName()
				+ ", " +  caseFile.getWeaponCard().getCardName());
		gamePlay.makeAccusation(caseFile.getCharacterCard().getCardName(), caseFile.getRoomCard().getCardName(), caseFile.getWeaponCard().getCardName());*/

		System.out.println("Current player accused "
				+ CharacterEnum.PEACOCK.getText()
				+ ", "  + caseFile.getRoomCard().getCardName()
				+ ", " +  caseFile.getWeaponCard().getCardName());
		gamePlay.makeAccusation(CharacterEnum.PEACOCK.getText(), caseFile.getRoomCard().getCardName(), caseFile.getWeaponCard().getCardName());
	}

	private static void testAddPlayers() {
		GamePlay gamePlay = new GamePlay(new ModelListenerImpl());

		gamePlay.addPlayer("Clint", CharacterEnum.WHITE, CharacterStartLocationEnum.WHITE.getPoint());
		gamePlay.addPlayer("James", CharacterEnum.SCARLET, CharacterStartLocationEnum.SCARLET.getPoint());
		gamePlay.addPlayer("Ethan", CharacterEnum.GREEN, CharacterStartLocationEnum.GREEN.getPoint());

		gamePlay.startGame();

		List<Player> players = new ArrayList<Player>();
		players = gamePlay.getBoardGame().getPlayers();
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getUserName());
			System.out.println(players.get(i).getCharacterName());
			System.out.println(players.get(i).isActive());
			System.out
					.println(players.get(i).getPlayerLocation().getLocation().x
							+ ", "
							+ players.get(i).getPlayerLocation().getLocation().y
							+ "\n");
		}
	}

	private static void testDealCards() {
		GamePlay gamePlay = new GamePlay(new ModelListenerImpl());

		gamePlay.addPlayer("Clint", CharacterEnum.WHITE, CharacterStartLocationEnum.WHITE.getPoint());
		gamePlay.addPlayer("James", CharacterEnum.SCARLET, CharacterStartLocationEnum.SCARLET.getPoint());
		gamePlay.addPlayer("Ethan", CharacterEnum.GREEN, CharacterStartLocationEnum.GREEN.getPoint());

		gamePlay.startGame();

		System.out.println("Case File");
		System.out.println(gamePlay.getBoardGame().getCaseFile().getCharacterCard().getCardName());
		System.out.println(gamePlay.getBoardGame().getCaseFile().getRoomCard().getCardName());
		System.out.println(gamePlay.getBoardGame().getCaseFile().getWeaponCard().getCardName());
		System.out.println("");

		List<Player> players = new ArrayList<Player>();
		players = gamePlay.getBoardGame().getPlayers();
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getCharacterName());
			System.out.println("----------");
			for (int j = 0; j < players.get(i).getCards().size(); j++) {
				System.out.println(players.get(i).getCards().get(j).getCardName());
			}
			System.out.println("");
		}
	}

	private static void testMakeAccusation() {
		GamePlay gamePlay = new GamePlay(new ModelListenerImpl());

		gamePlay.addPlayer("Clint", CharacterEnum.WHITE, CharacterStartLocationEnum.WHITE.getPoint());
		gamePlay.addPlayer("James", CharacterEnum.SCARLET, CharacterStartLocationEnum.SCARLET.getPoint());
		gamePlay.addPlayer("Ethan", CharacterEnum.GREEN, CharacterStartLocationEnum.GREEN.getPoint());

		gamePlay.startGame();
		System.out.println("Current Player: " + gamePlay.getBoardGame().getCurrentPlayer().getUserName());

		CaseFile caseFile = gamePlay.getBoardGame().getCaseFile();
		System.out.println("Case File");
		System.out.println(caseFile.getCharacterCard().getCardName());
		System.out.println(caseFile.getRoomCard().getCardName());
		System.out.println(caseFile.getWeaponCard().getCardName());
		System.out.println("");

		boolean accusationResult = gamePlay.makeAccusation(caseFile.getCharacterCard().getCardName(),
				caseFile.getRoomCard().getCardName(),
				WeaponEnum.CANDLESTICK.getText());

		System.out.println(accusationResult + "\n");

		List<Player> players = new ArrayList<Player>();
		players = gamePlay.getBoardGame().getPlayers();
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getUserName());
			System.out.println(players.get(i).getCharacterName());
			System.out.println(players.get(i).isActive());
			System.out
					.println(players.get(i).getPlayerLocation().getLocation().x
							+ ", "
							+ players.get(i).getPlayerLocation().getLocation().y
							+ "\n");
		}
	}

	private static void testMakeSuggestion() {
		GamePlay gamePlay = new GamePlay(new ModelListenerImpl());

		gamePlay.addPlayer("Clint", CharacterEnum.PLUM, CharacterStartLocationEnum.WHITE.getPoint());
		gamePlay.addPlayer("James", CharacterEnum.SCARLET, CharacterStartLocationEnum.SCARLET.getPoint());
		gamePlay.addPlayer("Ethan", CharacterEnum.GREEN, CharacterStartLocationEnum.GREEN.getPoint());

		gamePlay.startGame();
		gamePlay.movePlayer(new Point(0, 4));

		Player showPlayer = gamePlay.makeSuggestion(CharacterEnum.WHITE.getText(), WeaponEnum.REVOLVER.getText());
		//Player showPlayer = gamePlay.makeSuggestion(CharacterEnum.WHITE.getText(), WeaponEnum.REVOLVER.getText());

		System.out.println("Case File");
		System.out.println(gamePlay.getBoardGame().getCaseFile().getCharacterCard().getCardName());
		System.out.println(gamePlay.getBoardGame().getCaseFile().getRoomCard().getCardName());
		System.out.println(gamePlay.getBoardGame().getCaseFile().getWeaponCard().getCardName());
		System.out.println("");

		List<Player> players = new ArrayList<Player>();
		players = gamePlay.getBoardGame().getPlayers();
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getCharacterName());
			System.out.println("----------");
			System.out
			.println(players.get(i).getPlayerLocation().getLocation().x
					+ ", "
					+ players.get(i).getPlayerLocation().getLocation().y);
			for (int j = 0; j < players.get(i).getCards().size(); j++) {
				System.out.println(players.get(i).getCards().get(j).getCardName());
			}
			System.out.println("");
		}

		System.out.println("Shower Player = " + showPlayer.getCharacterName());
	}

}
