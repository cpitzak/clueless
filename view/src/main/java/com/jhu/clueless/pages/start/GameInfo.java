package com.jhu.clueless.pages.start;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameInfo extends JPanel {

	private JLabel ourPlayer = new JLabel();

	public GameInfo() {
		this.setVisible(false);
		this.setBackground(Color.BLACK);
		this.ourPlayer.setForeground(Color.ORANGE);
		this.setOurPlayer("");
		add(ourPlayer);
	}

	public void setOurPlayer(String name) {
		ourPlayer.setText("Your Character is: " + name);
		this.validate();
		this.repaint();
		ourPlayer.validate();
		ourPlayer.repaint();
	}

}
