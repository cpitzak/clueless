package com.jhu.clueless.pages.start;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import org.javabuilders.swing.SwingJavaBuilder;

import com.jhu.clueless.interfaces.GameControllerInterface;
import com.jhu.clueless.interfaces.ModelListener;
import com.jhu.clueless.pieces.Card;
import com.jhu.clueless.pieces.CardPiece;
import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.CharacterPiece;
import com.jhu.clueless.pieces.PlayerData;
import com.jhu.clueless.pieces.RoomAndHallwayEnum;
import com.jhu.clueless.util.PlayerDataManager;

/**
 * Defines a game board panel.
 *
 * @author Clint Pitzak
 *
 */
@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel {

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private final GameControllerInterface controller;
	private JLayeredPane gameBoardLayeredPane;
	@SuppressWarnings("unused")
	private NotepadLayeredPane notepadLayeredPane;
	private Color brown = new Color(142, 107, 35);
	private Color purple = new Color(160, 32, 240);
	private Color lightBlue = new Color(0, 191, 255);
	private Color darkBrown = new Color(139, 69, 19);

	private RoomAndHallwayEnum[][] roomEnums = {
	        { RoomAndHallwayEnum.STUDY, RoomAndHallwayEnum.HALLWAY, RoomAndHallwayEnum.HALL,
	                RoomAndHallwayEnum.HALLWAY, RoomAndHallwayEnum.LOUNGE },
	        { RoomAndHallwayEnum.HALLWAY, null, RoomAndHallwayEnum.HALLWAY, null, RoomAndHallwayEnum.HALLWAY },
	        { RoomAndHallwayEnum.LIBRARY, RoomAndHallwayEnum.HALLWAY, RoomAndHallwayEnum.BILLIARD,
	                RoomAndHallwayEnum.HALLWAY, RoomAndHallwayEnum.DINNING },
	        { RoomAndHallwayEnum.HALLWAY, null, RoomAndHallwayEnum.HALLWAY, null, RoomAndHallwayEnum.HALLWAY },
	        { RoomAndHallwayEnum.CONSERVATORY, RoomAndHallwayEnum.HALLWAY, RoomAndHallwayEnum.BALLROOM,
	                RoomAndHallwayEnum.HALLWAY, RoomAndHallwayEnum.KITCHEN } };
	private Color[][] layerColors = { { brown, Color.LIGHT_GRAY, Color.RED, Color.LIGHT_GRAY, Color.PINK },
	        { Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY },
	        { purple, Color.LIGHT_GRAY, Color.GREEN, Color.LIGHT_GRAY, lightBlue },
	        { Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY },
	        { Color.BLUE, Color.LIGHT_GRAY, darkBrown, Color.LIGHT_GRAY, Color.MAGENTA } };

	private CharacterPiece thisCharacter;
	private List<CharacterPiece> characterPieces = new ArrayList<CharacterPiece>();

	/** Offset to put icon at tip of cursor. */
	private static final int X_CURSOR_OFFSET = 22;
	private static final int Y_CURSOR_OFFSET = 30;

	private PlayerDataManager playerDataManager;
	private JPanel cardHolder;
	private GameInfo gameInfo;
	private static int hallwayCounter;
	private Map<FixedPoint, FixedPoint> boardPointToActualPoint = new HashMap<FixedPoint, FixedPoint>();

	/**
	 * Creates a game board panel.
	 *
	 * @param controller
	 *            the game controller
	 */
	public GameBoardPanel(GameControllerInterface controller) {
		SwingJavaBuilder.getConfig().addResourceBundle("GameBoardPanel");
		SwingJavaBuilder.build(this);

		this.controller = controller;

		PieceMoveListener pieceMoveListener = new PieceMoveListener();
		gameBoardLayeredPane.addMouseListener(pieceMoveListener);
		gameBoardLayeredPane.addMouseMotionListener(pieceMoveListener);
		gameBoardLayeredPane.setPreferredSize(new Dimension(580, 580));

		setupBoard();
	}

	private void setupBoard() {
		hallwayCounter = 0; // tracking hallway names
		createRoomHallwayRoom(0, 20, 45);
		createHallwayHallway(1, 99);
		createRoomHallwayRoom(2, 150, 175);
		createHallwayHallway(3, 229);
		createRoomHallwayRoom(4, 280, 305);
		hallwayCounter = 0; // reset

		cardHolder.setPreferredSize(new Dimension(300, 300));
	}

	/**
	 * @return the player data manager
	 */
	public PlayerDataManager getPlayerDataManager() {
		return playerDataManager;
	}

	/**
	 * Defines a fixed point.
	 */
	private class FixedPoint {
		private final int x;
		private final int y;

		public FixedPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public FixedPoint(Point point) {
			this.x = point.x;
			this.y = point.y;
		}

		/**
		 * @return the x
		 */
		public int getX() {
			return x;
		}

		/**
		 * @return the y
		 */
		public int getY() {
			return y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FixedPoint other = (FixedPoint) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

	}

	private void createHallwayHallway(int row, int yHallway) {
		Point point = new Point(50, yHallway);
		IntDimension hallwayDimension = new IntDimension(40, 52);

		int xRoomToHallwayOffset = 219;

		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][0].getText(), layerColors[row][0], point, hallwayDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 0), new FixedPoint(point));
		point.x += xRoomToHallwayOffset;
		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][2].getText(), layerColors[row][2], point, hallwayDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 2), new FixedPoint(point));
		point.x += xRoomToHallwayOffset;
		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][4].getText(), layerColors[row][4], point, hallwayDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 4), new FixedPoint(point));
	}

	private void createRoomHallwayRoom(int row, int yHallwayToRoom, int yRoomToHallway) {
		Point point = new Point(10, yHallwayToRoom);
		IntDimension roomDimension = new IntDimension(120, 80);
		IntDimension hallwayDimension = new IntDimension(100, 40);

		int xRoomToHallwayOffset = 119;
		int xHallToRoomOffset = 99;

		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][0].getText(), layerColors[row][0], point, roomDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 0), new FixedPoint(point));
		point.x += xRoomToHallwayOffset;
		point.y = yRoomToHallway;
		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][1].getText(), layerColors[row][1], point, hallwayDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 1), new FixedPoint(point));
		point.x += xHallToRoomOffset;
		point.y = yHallwayToRoom;
		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][2].getText(), layerColors[row][2], point, roomDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 2), new FixedPoint(point));
		point.x += xRoomToHallwayOffset;
		point.y = yRoomToHallway;
		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][3].getText(), layerColors[row][3], point, hallwayDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 3), new FixedPoint(point));
		point.x += xHallToRoomOffset;
		point.y = yHallwayToRoom;
		gameBoardLayeredPane.add(
		        createColoredLabel(roomEnums[row][4].getText(), layerColors[row][4], point, roomDimension),
		        new Integer(1));
		boardPointToActualPoint.put(new FixedPoint(row, 4), new FixedPoint(point));
	}

	private JLabel createColoredLabel(String title, Color color, Point origin, IntDimension dimension) {
		JLabel label = new JLabel(title);
		if (title.equals(RoomAndHallwayEnum.HALLWAY.getText())) {
			label.setName(RoomAndHallwayEnum.HALLWAY.getText() + hallwayCounter);
			hallwayCounter++;
		}
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(color);
		label.setForeground(Color.black);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setBounds(origin.x, origin.y, dimension.width, dimension.height);
		return label;
	}

	private void createCards() {
		cardHolder.removeAll();

		int cardXposition = 10;
		int cardYposition = 400;
		int xIncrement = 100;
		for (CardPiece cardPiece : playerDataManager.getCards()) {
			IntDimension cardDimension = new IntDimension(cardPiece.getWidth(), cardPiece.getHeight());
			final JLabel cardLabel = createColoredLabel("", Color.ORANGE, new Point(cardXposition, cardYposition),
			        cardDimension);
			cardLabel.setIcon(cardPiece.getIcon());
			cardLabel.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

			});
			cardHolder.add(cardLabel, new Integer(1));
			cardXposition += xIncrement;
		}
		this.validate();
		this.repaint();
		cardHolder.validate();
		cardHolder.repaint();
	}

	/**
	 * @param playerDataManager
	 *            the playerDataManager to set
	 */
	public void setPlayerDataManager(PlayerDataManager playerDataManager) {
		if (this.playerDataManager != null) {
			this.playerDataManager.setUserName(playerDataManager.getUsername());
		} else {
			this.playerDataManager = playerDataManager;
		}
		// this.createCards();
	}

	/**
	 * Defines the back button.
	 */
	public void backButton() {
		changeSupport.firePropertyChange(MainPanel.class.toString(), false, true);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Define an int dimension.
	 */
	private class IntDimension {
		public int width;
		public int height;

		public IntDimension(int width, int height) {
			this.width = width;
			this.height = height;
		}

	}

	/**
	 * Defines the piece move listener.
	 *
	 * @author Clint Pitzak
	 *
	 */
	private class PieceMoveListener extends MouseInputAdapter {

		private static final int MAX_HEIGHT = 375;

		@Override
		public void mousePressed(MouseEvent arg0) {
			Component component = gameBoardLayeredPane.findComponentAt(arg0.getX(), arg0.getY());
			if (!(component instanceof CharacterPiece)) {
				return;
			}
			CharacterPiece selectedPiece = (CharacterPiece) component;
			selectedPiece.setLocation(arg0.getX() - X_CURSOR_OFFSET, arg0.getY() - Y_CURSOR_OFFSET);
			gameBoardLayeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			if (thisCharacter == null) {
				return;
			}
			int xMax = gameBoardLayeredPane.getWidth() - thisCharacter.getWidth();
			int x = arg0.getX();
			x = Math.min(x, xMax);
			x = Math.max(x, 0);

			int yMax = MAX_HEIGHT - thisCharacter.getHeight();
			int y = arg0.getY();
			y = Math.min(y, yMax);
			y = Math.max(y, 0);

			thisCharacter.setLocation(x - X_CURSOR_OFFSET, y - Y_CURSOR_OFFSET);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (thisCharacter == null) {
				return;
			}

			if (!(gameBoardLayeredPane.findComponentAt(arg0.getPoint()) instanceof CharacterPiece)) {
				return;
			}

			// find out what where the user dropped the piece. To accomplish
			// this we use the findComponent method and
			// the location of the piece. However since that would return the
			// piece, we move the piece out of the way
			// which reveals what it was dropped on.
			thisCharacter.setLocation(-50, -50);
			Component component = gameBoardLayeredPane.findComponentAt(arg0.getPoint());
			if (component instanceof JLabel) {
				boolean foundRoom = false;
				JLabel possibleRoomOrHallway = (JLabel) component;
				int tempHallwayCount = 0;
				for (int row = 0; row < roomEnums.length && !foundRoom; row++) {
					for (int col = 0; col < roomEnums[0].length && !foundRoom; col++) {
						RoomAndHallwayEnum roomEnum = roomEnums[row][col];
						if (roomEnum != null) {
							if (roomEnum.getText().equals(RoomAndHallwayEnum.HALLWAY.getText())) {
								String name = possibleRoomOrHallway.getName();
								if (name != null
								        && name.equals(RoomAndHallwayEnum.HALLWAY.getText() + (tempHallwayCount))) {
									controller.movePiece(new PlayerData("filler", thisCharacter.getCharacter()),
									        new Point(row, col));
									foundRoom = true;
									break;
								}
								tempHallwayCount++;
							} else if (roomEnum.getText().equals(possibleRoomOrHallway.getText())) {
								controller.movePiece(new PlayerData("filler", thisCharacter.getCharacter()), new Point(
								        row, col));
								foundRoom = true;
								break;
							}
						}
					}
				}

			}

			int xMax = gameBoardLayeredPane.getWidth() - thisCharacter.getWidth();
			int x = arg0.getX();
			x = Math.min(x, xMax);
			x = Math.max(x, 0);

			int yMax = MAX_HEIGHT - thisCharacter.getHeight();
			int y = arg0.getY();
			y = Math.min(y, yMax);
			y = Math.max(y, 0);

			thisCharacter.setLocation(x - X_CURSOR_OFFSET, y - Y_CURSOR_OFFSET);
			gameBoardLayeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void addPlayer(CharacterEnum characterName, String userName, Point startLocation) {
		CharacterPiece currentPiece = new CharacterPiece(characterName);
		this.characterPieces.add(currentPiece);

		currentPiece.setBounds(15, 225, currentPiece.getIcon().getIconWidth(), currentPiece.getIcon().getIconHeight());
		gameBoardLayeredPane.add(currentPiece, new Integer(2));
		gameBoardLayeredPane.moveToFront(currentPiece);

		setCharacterPieceLocation(startLocation, currentPiece);
		if (playerDataManager.getUsername().equals(userName)) {
			this.thisCharacter = currentPiece;
			playerDataManager.setCharacterEnum(characterName);
		}
	}

	private void setCharacterPieceLocation(Point point, CharacterPiece characterPiece) {
		FixedPoint actualPoint = boardPointToActualPoint.get(new FixedPoint(point));
		if (actualPoint == null) {
			System.out.println("ACTUAL POINT WAS NULL");
			return;
		}
		characterPiece.setLocation(actualPoint.getX(), actualPoint.getY());
	}

	public void startGame(Map<CharacterEnum, List<Card>> playerCardsMap) {
		boolean found = false;
		for (Map.Entry<CharacterEnum, List<Card>> entry : playerCardsMap.entrySet()) {
			if (playerDataManager.getCharacterEnum().equals(entry.getKey())) {
				List<Card> value = entry.getValue();
				List<CardPiece> cardPieces = new ArrayList<CardPiece>();
				for (Card card : value) {
					cardPieces.add(new CardPiece(card));
				}
				playerDataManager.getCards().addAll(cardPieces);
				found = true;
				break;
			}
			if (found) {
				break;
			}
		}
		this.createCards();
		gameInfo.setOurPlayer(playerDataManager.getCharacterEnum().getText());
		gameInfo.setVisible(true);
	}

	public void showPossibleMoves(CharacterEnum characterName, List<Point> points) {
	}

	public void movePlayer(CharacterEnum characterName, Point point) {
		for (CharacterPiece characterPiece : characterPieces) {
			if (characterPiece.getCharacter().equals(characterName)) {
				setCharacterPieceLocation(point, characterPiece);
				break;
			}
		}
	}

	public void advanceTurn(CharacterEnum endCharacterName, CharacterEnum startCharacterName) {
	}

	public void showAccusationResult(CharacterEnum characterName, Boolean win) {
	}

	public void requestSelectCardToShow(CharacterEnum characterName, List<String> cards) {
	}

	public void showCard(CharacterEnum fromCharacterName, CharacterEnum toCharacterName, String showCard) {
	}

	public void addModelListener(ModelListener modelListener) {
	}

}
