package com.jhu.clueless.client;

import com.jhu.clueless.pieces.GameStateEnum;

/**
 * Defines the console player interface.
 */
public interface ConsolePlayerInterface {

	/**
	 * Registers the username.
	 *
	 * @param username the username to use
	 */
	public void registerUserName (String username);

	/**
	 * Sends the text.
	 *
	 * @param consoleText the console text to send
	 */
	public void sendText (String consoleText);

	/**
	 * Adds a player.
	 *
	 * @param userName the username to use
	 * @param consoleText the console text to use
	 */
	public void addPlayer(String userName, String consoleText);

	/**
	 * Starts the game.
	 *
	 * @param consoleText the console text to use
	 */
	public void startGame(String consoleText);

	/**
	 * Moves the player.
	 *
	 * @param consoleText the console text to use
	 */
	public void movePlayer(String consoleText);

	/**
	 * Choose action.
	 *
	 * @param consoleText the console text to use
	 */
	public void chooseAction(String consoleText);

	/**
	 * @return the username
	 */
	public String getUserName();

	/**
	 * @return the console frame
	 */
	public ConsoleInterface getConsoleFrame();

	/**
	 * @return true if a moderator false if not
	 */
	public boolean isModerator();

	/**
	 * @return the character
	 */
	public String getCharacter();

	/**
	 * @return the game state
	 */
	public GameStateEnum getGameState();

	/**
	 * Make an accusation.
	 *
	 * @param consoleText the console text to use
	 */
	public void makeAccusation(String consoleText);

	/**
	 * Make a suggestion.
	 *
	 * @param consoleText the console text to use
	 */
	public void makeSuggestion(String consoleText);

	/**
	 * Show a card.
	 *
	 * @param consoleText the console text to use
	 */
	public void showCard(String consoleText);

	/**
	 * Turn on cheat mode.
	 */
	public void cheatMode();

}
