package com.jhu.clueless.pages.start;

import java.awt.CardLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import com.jhu.clueless.interfaces.GameControllerInterface;
import com.jhu.clueless.pieces.PlayerData;
import com.jhu.clueless.util.FileUtilities;
import com.jhu.clueless.util.PlayerDataManager;

/**
 * Defines the Start Panel.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel implements PropertyChangeListener {

	private final GameControllerInterface controller;
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	private static final String CLUELESS_LOGO = "CLUELESS.png";

	private JLabel cluelessLogoLabel;
	private JLabel welcomeLabel;
	private CardLayoutPanel cards;

	private PlayerDataManager playerDataManager;

	/**
	 * Creates the start panel.
	 *
	 * @param controller
	 *            the game controller to use
	 */
	public MainPanel(GameControllerInterface controller) {
		this.controller = controller;
		SwingJavaBuilder.getConfig().addResourceBundle("MainPanel");
		SwingJavaBuilder.build(this);
		Icon icon = FileUtilities.getIcon(CLUELESS_LOGO);
		cluelessLogoLabel.setIcon(icon);
		welcomeLabel.setFont(new Font("Serif", Font.BOLD, 12));
		cards.setPropertyChangeListener(this);
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, MainPanelButtonPanel.class.toString()); // on first
		                                                       // launch show
		                                                       // the main
		                                                       // button panel
	}

	/**
	 * Displays the selected card.
	 *
	 * @param evt
	 *            the event that was changed
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PlayerDataManager.class.toString())) {
			playerDataManager = (PlayerDataManager) evt.getNewValue();
			changeSupport.firePropertyChange(PlayerDataManager.class.toString(), false, evt.getNewValue());
		}
		if (evt.getPropertyName().equals(MainPanelButtonPanel.CREATE_GAME)) {
			CreateGamePanel cg = (CreateGamePanel) cards.getCard(CreateGamePanel.class.toString());
			cg.setCurrentPlayer(new PlayerData(this.playerDataManager.getUsername(), this.playerDataManager
			        .getCharacterEnum()));
			controller.createGame(null);// (String) evt.getNewValue());
		}
		if (evt.getPropertyName().equals(CreateGamePanel.START_GAME)) {
			controller.startGame((List<PlayerData>) evt.getNewValue());
			return;
		}
		if (evt.getPropertyName().equals(JoinGamePanel.JOIN_GAME)) {
			controller.joinGame(playerDataManager.getUsername(), (String) evt.getNewValue());
			changeSupport.firePropertyChange(GameBoardPanel.class.toString(), false, true);
			return;
		}
		if (evt.getPropertyName().equals(GameBoardPanel.class.toString())) {
			changeSupport.firePropertyChange(GameBoardPanel.class.toString(), false, true);
		} else {
			CardLayout card = (CardLayout) cards.getLayout();
			card.show(cards, evt.getPropertyName());
		}
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
