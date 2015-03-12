package com.jhu.clueless.pages.start;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javabuilders.swing.SwingJavaBuilder;

import com.jhu.clueless.util.PlayerDataManager;

/**
 * Defines the main game button panel.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class MainPanelButtonPanel extends JPanel {

	/** create game command. */
	public static final String CREATE_GAME = "createGame";
	private JTextField userNameTextField;
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	/**
	 * Creates the start panel.
	 */
	public MainPanelButtonPanel() {
		SwingJavaBuilder.getConfig().addResourceBundle("MainPanelButtonPanel");
		SwingJavaBuilder.build(this);
	}

	/**
	 * Settings for game.
	 */
	public void settingsButton() {

	}

	/**
	 * Creates a game.
	 */
	public void createGameButton() {
		changeSupport.firePropertyChange(PlayerDataManager.class.toString(), false,
		        new PlayerDataManager(userNameTextField.getText()));
		changeSupport.firePropertyChange(CREATE_GAME, false, userNameTextField.getText());
		changeSupport.firePropertyChange(CreateGamePanel.class.toString(), false, true);
	}

	/**
	 * Connects to a game.
	 */
	public void joinGameButton() {
		changeSupport.firePropertyChange(PlayerDataManager.class.toString(), false,
		        new PlayerDataManager(userNameTextField.getText()));
		changeSupport.firePropertyChange(JoinGamePanel.class.toString(), false, true);
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
