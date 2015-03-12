package com.jhu.clueless.interfaces;

import java.awt.Point;
import java.util.List;

import com.jhu.clueless.pieces.PlayerData;

/**
 * Defines the Game Controller Interface.
 *
 * @author Clint Pitzak
 *
 */
public interface GameControllerInterface {

	/**
	 * Moves the piece.
	 *
	 * @param playerData the player data to use
	 * @param location the location to use
	 */
	void movePiece(PlayerData playerData, Point location);

	/**
	 * Join Game.
	 *
	 * @param username the username to use
	 * @param ipAddress the ipaddress to use
	 */
	void joinGame(String username, String ipAddress);

	/**
	 * Creates a game.
	 *
	 * @param playerDatas the playerDatas to use
	 */
	void createGame(List<PlayerData> playerDatas);

	/**
	 * Starts the game.
	 *
	 * @param playerDatas the player datas to use
	 */
	void startGame(List<PlayerData> playerDatas);

}
