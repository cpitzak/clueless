package com.jhu.clueless.pages.start;

import java.awt.CardLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

/**
 * Defines a cardLayout for the Main Panel.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class CardLayoutPanel extends JPanel {

	private MainPanelButtonPanel mainPanelButtonPanel;
	private JoinGamePanel joinGamePanel;
	private CreateGamePanel createGamePanel;

	/**
	 * Constructs the card layout panel.
	 */
	public CardLayoutPanel() {
		SwingJavaBuilder.build(this);
		this.setLayout(new CardLayout());
		add(mainPanelButtonPanel, MainPanelButtonPanel.class.toString());
		add(joinGamePanel, JoinGamePanel.class.toString());
		add(createGamePanel, CreateGamePanel.class.toString());
		add(createGamePanel, CreateGamePanel.class.toString());
	}

	/**
	 * Sets the property change listener.
	 *
	 * @param listener the listener to use
	 */
	public void setPropertyChangeListener(PropertyChangeListener listener) {
		mainPanelButtonPanel.addPropertyChangeListener(listener);
		joinGamePanel.addPropertyChangeListener(listener);
		createGamePanel.addPropertyChangeListener(listener);
	}

	/**
	 * @param className the class name to use.
	 *
	 * @return the card
	 */
	public Object getCard(String className) {
		if (CreateGamePanel.class.toString().equals(className)) {
			return this.createGamePanel;
		}
		return null;
	}

}
