package com.jhu.clueless.controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.List;

import com.jhu.clueless.client.CluelessClient;
import com.jhu.clueless.interfaces.GameControllerInterface;
import com.jhu.clueless.pages.CluelessUI;
import com.jhu.clueless.pieces.PlayerData;
import com.jhu.clueless.server.CluelessServer;

/**
 * Defines the game controller.
 */
public class GameController implements GameControllerInterface {

	private final CluelessUI view;
	private CluelessClient client;

	/**
	 * Constructs the game controller.
	 *
	 * @param client
	 *            the clueless client to use
	 */
	public GameController() {//CluelessClient client) {
//		this.client = client;
		this.view = new CluelessUI(this);
		view.setPreferredSize(new Dimension(800, 600));
		view.pack();
		view.setVisible(true);
//		this.client.addGameBoardPanel(view.getGameBoardPanel());
	}

//	// for testing only
//	public GameBoardPanel getGameBoardPanel() {
//		return view.getGameBoardPanel();
//	}

	/**
	 * Moves the game board piece. Called by view.
	 *
	 * @param playerData
	 *            the player data to use
	 * @param location
	 *            the location to move the character enum to
	 */
	public void movePiece(PlayerData playerData, Point location) {

	}

	/**
	 * Joining a game.
	 *
	 * @param username
	 *            the username to use
	 * @param ipAddress
	 *            the ipAddress to use
	 */
	public void joinGame(String username, String ipAddress) {
		this.client = new CluelessClient(username, ipAddress);
		this.client.addGameBoardPanel(view.getGameBoardPanel());
	}

	/**
	 * Create game.
	 *
	 * @param playerDatas
	 *            the playerDatas to use
	 * t
	 */
	public void createGame(List<PlayerData> playerDatas) {
		try {
			new CluelessServer();
		} catch (IOException e) {
			System.out.println("ERROR: running server");
		}
		this.joinGame(this.view.getGameBoardPanel().getPlayerDataManager().getUsername(), "localhost");
//		for (PlayerData playerData : playerDatas) {
//			client.getConsolePlayer().addPlayer(playerData.getUsername(),
//			        Integer.toString(playerData.getCharacterEnum().ordinal()));
//		}
	}

	/**
	 * Starts the game.
	 *
	 * @param playerDatas
	 *            the player datas to use
	 */
	public void startGame(List<PlayerData> playerDatas) {
//		for (PlayerData playerData : playerDatas) {
//			CharacterEnum characterEnum = playerData.getCharacterEnum();
//			model.addPlayer(playerData.getUsername(), characterEnum,
//			        CharacterStartLocationEnum.valueOf(characterEnum.toString()).getPoint());
//		}
//		model.startGame();
	}

}
