package com.jhu.clueless.pages.start;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javabuilders.swing.SwingJavaBuilder;

import com.jhu.clueless.util.Validator;

/**
 * Defines the Game Join Panel used by Main Panel.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class JoinGamePanel extends JPanel {

	private JTextField ipAddressTextField;
	/** Join Game Command. */
	public static final String JOIN_GAME = "JoinGame";
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	/**
	 * Constructs the join game panel.
	 */
	public JoinGamePanel() {
		SwingJavaBuilder.getConfig().addResourceBundle("JoinGamePanel");
		SwingJavaBuilder.build(this);
	}

	/**
	 * Defines the back button.
	 */
	public void backButton() {
		changeSupport.firePropertyChange(MainPanelButtonPanel.class.toString(), false, true);
	}

	/**
	 * Defines the connect button.
	 */
	public void connectButton() {
		boolean ipAddressValid = Validator.isIpAddressValid(ipAddressTextField.getText());
		if (!ipAddressValid) {
			JOptionPane.showMessageDialog(this, "Please enter a valid IP Address", "Invalid IP Address",
			        JOptionPane.ERROR_MESSAGE);
		} else {
			changeSupport.firePropertyChange(JOIN_GAME, false, ipAddressTextField.getText());
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
