//package com.jhu.clueless.pages.start;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Point;
//
//import javax.swing.BorderFactory;
//import javax.swing.Icon;
//import javax.swing.JLabel;
//import javax.swing.JLayeredPane;
//
//import com.jhu.clueless.util.FileUtilities;
//
///**
// * Defines a Clueless Notepad.
// *
// * @author Clint Pitzak
// *
// */
//@SuppressWarnings("serial")
//public class CardHolderLayeredPane extends JLayeredPane {
//
//	private static final String CARD_HOLDER_IMAGE = "CARDHOLDER.png";
//
//	/**
//	 * Constructs a card holder.
//	 * @param location the location of the card holder
//	 */
//	public CardHolderLayeredPane(Point location) {
//		Icon icon = FileUtilities.getIcon(CARD_HOLDER_IMAGE);
//		Dimension dimension = new Dimension(icon.getIconWidth(), icon.getIconHeight());
//		setPreferredSize(dimension);
//		JLabel label = new JLabel();
//		label.setIcon(icon);
//		label.setBorder(BorderFactory.createLineBorder(Color.black));
//		label.setBounds(location.x, location.y, icon.getIconWidth(), icon.getIconHeight());
//		add(label, new Integer(1));
//	}
//
//}
