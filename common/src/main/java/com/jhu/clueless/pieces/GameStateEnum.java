/**
 *
 */
package com.jhu.clueless.pieces;


/**
 * @author ELO
 *
 */
public enum GameStateEnum {

	addPlayer(1),
	waitingForStartGame(2),
	gameStarted(3),
	moving(4),
	chooseAction(5),
	suggesting(6),
	accussing(7),
	waiting(8),
	showCard(9),
	waitingSeeCard(10);



	private int gameState;

	private GameStateEnum(int gameState) {
		this.gameState = gameState;
	}

	/**
	 *
	 * @return the Character.
	 */
	public int getGameState() {
		return gameState;
	}
}
