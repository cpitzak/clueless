package com.jhu.clueless.pages;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jhu.clueless.interfaces.GameControllerInterface;
import com.jhu.clueless.pages.start.GameBoardPanel;
import com.jhu.clueless.pages.start.MainPanel;
import com.jhu.clueless.util.FileUtilities;
import com.jhu.clueless.util.PlayerDataManager;

/**
 * Defines the UI for Clueless.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class CluelessUI extends JFrame implements PropertyChangeListener {

	private JPanel cards;
	private GameBoardPanel gameBoardPanel;

	/**
	 * Creates the Clueless UI.
	 *
	 * @param controller
	 *            the game controller
	 */
	public CluelessUI(GameControllerInterface controller) {
		setLookAndFeel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		this.setIconImage(((ImageIcon) FileUtilities.getIcon("CLUELESS.png")).getImage());

		MainPanel mainPanel = new MainPanel(controller);
		mainPanel.addPropertyChangeListener(this);

		gameBoardPanel = new GameBoardPanel(controller);
		gameBoardPanel.addPropertyChangeListener(this);

		cards = new JPanel(new CardLayout());
		cards.add(mainPanel, MainPanel.class.toString());
		cards.add(gameBoardPanel, GameBoardPanel.class.toString());
		add(cards, BorderLayout.CENTER);

	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the selected card.
	 *
	 * @param evt
	 *            the event that was changed
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PlayerDataManager.class.toString())) {
			gameBoardPanel.setPlayerDataManager((PlayerDataManager) evt.getNewValue());
		}
		CardLayout card = (CardLayout) cards.getLayout();
		card.show(cards, evt.getPropertyName());
	}

	public GameBoardPanel getGameBoardPanel() {
		return gameBoardPanel;
	}

}
