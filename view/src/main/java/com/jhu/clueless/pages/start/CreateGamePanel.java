package com.jhu.clueless.pages.start;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.PlayerData;

/**
 * Defines a Create Game Panel.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class CreateGamePanel extends JPanel {

	/** start game command. */
	public static final String START_GAME = "startGame";
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	private JLabel playerOne;
	private JLabel playerTwo;
	private JLabel playerThree;
	private JLabel playerFour;
	private JLabel playerFive;
	private JLabel playerSix;
	private List<JLabel> labels;
	private List<PlayerData> players = new ArrayList<PlayerData>();
	private static final int MAX_PLAYERS = 6;
	private JComboBox chooseCharacterComboBox;
	private PlayerData currentPlayer;

	/**
	 * Constructs the Create Game Panel.
	 */
	public CreateGamePanel() {
		SwingJavaBuilder.getConfig().addResourceBundle("CreateGamePanel");
		SwingJavaBuilder.build(this);
		increaseFontSize(playerOne);
		increaseFontSize(playerTwo);
		increaseFontSize(playerThree);
		increaseFontSize(playerFour);
		increaseFontSize(playerFive);
		increaseFontSize(playerSix);

		labels = Arrays.asList(playerOne, playerTwo, playerThree, playerFour, playerFive, playerSix);

		for (CharacterEnum characterEnum : CharacterEnum.values()) {
			chooseCharacterComboBox.addItem(characterEnum);
		}
	}

	private void increaseFontSize(JLabel label) {
		Font currentFront = playerOne.getFont();
		label.setFont(new Font(currentFront.getFontName(), currentFront.getStyle(), 18));
		label.setForeground(Color.GREEN);
	}

	/**
	 * @param currentPlayer
	 *            the myPlayer to set
	 */
	public void setCurrentPlayer(PlayerData currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.addPlayer(currentPlayer);
	}

	/**
	 * addPlayer.
	 *
	 * @param playerData
	 *            the players to add.
	 */
	public void addPlayer(PlayerData playerData) {
		if (players.size() < MAX_PLAYERS) {
			JLabel playerSlot = labels.get(players.size());
			playerSlot.setText(playerData.getUsername());
			playerSlot.setForeground(Color.MAGENTA);
			players.add(playerData);
		}
	}

	/**
	 * Start Game Button Definiton.
	 */
	public void startGame() {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getUsername().equals(this.currentPlayer.getUsername())) {
				players.set(
				        i,
				        new PlayerData(players.get(i).getUsername(), (CharacterEnum) chooseCharacterComboBox
				                .getSelectedItem()));
			}
		}
		changeSupport.firePropertyChange(START_GAME, false, players);
		changeSupport.firePropertyChange(GameBoardPanel.class.toString(), false, true);
	}

	/**
	 * Back button definition.
	 */
	public void backButton() {
		changeSupport.firePropertyChange(MainPanelButtonPanel.class.toString(), false, true);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

}
