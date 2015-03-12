package com.jhu.clueless.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;
import com.jhu.clueless.interfaces.ModelListener;
import com.jhu.clueless.pages.start.GameBoardPanel;
import com.jhu.clueless.pieces.Card;
import com.jhu.clueless.pieces.CharacterEnum;

/**
 * Defines the Clueless Client.
 */
public class CluelessClient {
	ConsoleFrame consoleTextArea;
	Client client;
	ConsolePlayerInterface consolePlayer;
	static GameBoardPanel gameBoardPanel;

	/**
	 * Creates a clueless client.
	 */
	public CluelessClient(String username, String hostAddress) {
		client = new Client();
		client.start();

		// Registers the client class so we can send it over the network
		Network.register(client);

		// Gets the player from the other end of the connection for rmi
		consolePlayer = ObjectSpace.getRemoteObject(client, Network.CONSOLE_PLAYER, ConsolePlayerInterface.class);
		RemoteObject remoteConsolePlayerObject = (RemoteObject) consolePlayer;
		remoteConsolePlayerObject.setResponseTimeout(Network.reponseTime);

		client.addListener(new Listener() {
			@Override
			public void disconnected(Connection connection) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						consoleTextArea.dispose();
					}
				});
			}
		});

		// ask user for host
//		String userInput = (String) JOptionPane.showInputDialog(null, "Host:", "Connect to Clueless Server",
//		        JOptionPane.QUESTION_MESSAGE, null, null, "localhost");
//		if (userInput == null || userInput.trim().length() == 0) {
//			System.exit(1);
//		}
//		final String host = userInput.trim();
		final String host = hostAddress.trim();

		// ask for user name
//		userInput = (String) JOptionPane.showInputDialog(null, "Name:", "Connect to Clueless Server",
//		        JOptionPane.QUESTION_MESSAGE, null, null, "YourUsername");
//		if (userInput == null || userInput.trim().length() == 0) {
//			System.exit(1);
//		}
//		final String userName = userInput.trim();
		final String userName = username.trim();

		consoleTextArea = new ConsoleFrame(host);
		new ObjectSpace(client).register(Network.CONSOLE_TEXT_AREA, consoleTextArea);
		consoleTextArea.setSendListener(new Runnable() {
			public void run() {
				// Create a cheat mode to show case file
				if (consoleTextArea.getConsoleText().equals("CHEAT")) {
					consolePlayer.cheatMode();
				} else {
					switch (consolePlayer.getGameState().getGameState()) {
					// addPlayer(1)
					case 1:
						consolePlayer.addPlayer(userName, consoleTextArea.getConsoleText());
						break;

					// waitingForStartGame(2)
					case 2:
						consolePlayer.startGame(consoleTextArea.getConsoleText());
						break;

					// gameStarted(3)
					case 3:
						break;

					// moving(4)
					case 4:
						consolePlayer.movePlayer(consoleTextArea.getConsoleText());
						break;

					// chooseAction(5),
					case 5:
						consolePlayer.chooseAction(consoleTextArea.getConsoleText());
						break;

					// suggesting(6)
					case 6:
						consolePlayer.makeSuggestion(consoleTextArea.getConsoleText());
						break;

					// accussing(7)
					case 7:
						consolePlayer.makeAccusation(consoleTextArea.getConsoleText());
						break;

					// waiting(8)
					case 8:
						break;

					// showCard(9)
					case 9:
						consolePlayer.showCard(consoleTextArea.getConsoleText());
						break;

					// waitingSeeCard(10)
					case 10:
						break;

					default:
						break;
					}
				}
			}
		});
		consoleTextArea.setCloseListener(new Runnable() {
			public void run() {
				client.stop();
			}
		});
		consoleTextArea.setVisible(true);

		// connecting on a new thread. This allows a progress bar to be shown if
		// connection is slow enough.
		new Thread("Connect") {
			@Override
			public void run() {
				try {
					client.connect(Network.reponseTime, host, Network.port);
					consolePlayer.registerUserName(userName);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}.start();
	}

	public void addGameBoardPanel(GameBoardPanel gameBoardPanel) {
		CluelessClient.gameBoardPanel = gameBoardPanel;
	}

	/**
	 * Defines the console frame.
	 */
	@SuppressWarnings("serial")
    static private class ConsoleFrame extends JFrame implements ConsoleInterface, ModelListener {
		CardLayout cardLayout;
		JProgressBar consoleProgressBar;
		JList consoleTexts;
		JTextField consoleText;
		JButton sendButton;
		JList consoleUserNames;

		/**
		 * Create the console frame.
		 */
		public ConsoleFrame(String host) {
			super("Clueless Client");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setSize(640, 290);
			setLocationRelativeTo(null);

			Container consoleContentPane = getContentPane();
			cardLayout = new CardLayout();
			consoleContentPane.setLayout(cardLayout);
			{
				JPanel panel = new JPanel(new BorderLayout());
				consoleContentPane.add(panel, "progress");
				panel.add(new JLabel("Connecting to " + host + "..."));
				{
					panel.add(consoleProgressBar = new JProgressBar(), BorderLayout.SOUTH);
					consoleProgressBar.setIndeterminate(true);
				}
			}
			{
				JPanel mainPanel = new JPanel(new BorderLayout());
				consoleContentPane.add(mainPanel, "clueless");
				{
					JPanel firstPanel = new JPanel(new GridLayout(1, 2));
					mainPanel.add(firstPanel);
					{
						firstPanel.add(new JScrollPane(consoleTexts = new JList()));
						consoleTexts.setModel(new DefaultListModel());
					}
					{
						firstPanel.add(new JScrollPane(consoleUserNames = new JList()));
						consoleUserNames.setModel(new DefaultListModel());
					}
					DefaultListSelectionModel disableSelections = new DefaultListSelectionModel() {
						@Override
						public void setSelectionInterval(int index0, int index1) {
						}
					};
					consoleTexts.setSelectionModel(disableSelections);
					consoleUserNames.setSelectionModel(disableSelections);
				}
				{
					JPanel bottomPanel = new JPanel(new GridBagLayout());
					mainPanel.add(bottomPanel, BorderLayout.SOUTH);
					bottomPanel.add(consoleText = new JTextField(), new GridBagConstraints(0, 0, 1, 1, 1, 0,
					        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					bottomPanel.add(sendButton = new JButton("Send"), new GridBagConstraints(1, 0, 1, 1, 0, 0,
					        GridBagConstraints.CENTER, 0, new Insets(0, 0, 0, 0), 0, 0));
				}
			}

			consoleText.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sendButton.doClick();
				}
			});
		}

		public void setSendListener(final Runnable listener) {
			sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (getConsoleText().length() == 0)
						return;
					listener.run();
					consoleText.setText("");
					consoleText.requestFocus();
				}
			});
		}

		public void setCloseListener(final Runnable listener) {
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent evt) {
					listener.run();
				}

				@Override
				public void windowActivated(WindowEvent evt) {
					consoleText.requestFocus();
				}
			});
		}

		public String getConsoleText() {
			return consoleText.getText().trim();
		}

		@SuppressWarnings("unused")
        public void clearSendText() {
			consoleText.setText("");
		}

		public void setUserNames(final String[] usernames, final String[] characters) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					cardLayout.show(getContentPane(), "clueless");
					DefaultListModel consoleModel = (DefaultListModel) consoleUserNames.getModel();
					consoleModel.removeAllElements();
					for (int i = 0; i < characters.length; i++) {
						consoleModel.addElement(usernames[i] + ": " + characters[i]);
					}
				}
			});
		}

		public void addText(final String consoleText) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					DefaultListModel consoleModel = (DefaultListModel) consoleTexts.getModel();
					consoleModel.addElement(consoleText);
					consoleTexts.ensureIndexIsVisible(consoleModel.size() - 1);
				}
			});
		}

		public void addPlayer(CharacterEnum characterName, String userName, Point startLocation) {
			CluelessClient.gameBoardPanel.addPlayer(characterName, userName, startLocation);
        }

		public void startGame(Map<CharacterEnum, List<Card>> playerCardsMap) {
			CluelessClient.gameBoardPanel.startGame(playerCardsMap);
        }

		public void showPossibleMoves(CharacterEnum characterName, List<Point> points) {
			CluelessClient.gameBoardPanel.showPossibleMoves(characterName, points);
        }

		public void movePlayer(CharacterEnum characterName, Point point) {
			CluelessClient.gameBoardPanel.movePlayer(characterName, point);
        }

		public void advanceTurn(CharacterEnum endCharacterName, CharacterEnum startCharacterName) {
			CluelessClient.gameBoardPanel.advanceTurn(endCharacterName, startCharacterName);
        }

		public void showAccusationResult(CharacterEnum characterName, Boolean win) {
			CluelessClient.gameBoardPanel.showAccusationResult(characterName, win);
        }

		public void requestSelectCardToShow(CharacterEnum characterName, List<String> cards) {
			CluelessClient.gameBoardPanel.requestSelectCardToShow(characterName, cards);
        }

		public void showCard(CharacterEnum fromCharacterName, CharacterEnum toCharacterName, String showCard) {
			CluelessClient.gameBoardPanel.showCard(fromCharacterName, toCharacterName, showCard);
        }
	}

	/**
	 * Runs the clueless client.
	 *
	 * @param args unused args
	 */
	public static void main(String[] args) {
//		new CluelessClient();
	}
}
