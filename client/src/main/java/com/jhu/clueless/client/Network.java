package com.jhu.clueless.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.beans.PropertyChangeSupport;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import org.javabuilders.swing.SwingJavaBuilder;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.jhu.clueless.gameplay.GamePlay;
import com.jhu.clueless.gameplay.ModelListenerImpl;
import com.jhu.clueless.interfaces.GameControllerInterface;
import com.jhu.clueless.interfaces.ModelListener;
import com.jhu.clueless.pages.CluelessUI;
import com.jhu.clueless.pages.start.CardLayoutPanel;
import com.jhu.clueless.pages.start.CreateGamePanel;
import com.jhu.clueless.pages.start.GameBoardPanel;
import com.jhu.clueless.pages.start.JoinGamePanel;
import com.jhu.clueless.pages.start.MainPanel;
import com.jhu.clueless.pages.start.MainPanelButtonPanel;
import com.jhu.clueless.pages.start.NotepadLayeredPane;
import com.jhu.clueless.pieces.BoardGame;
import com.jhu.clueless.pieces.Card;
import com.jhu.clueless.pieces.CaseFile;
import com.jhu.clueless.pieces.CharacterCard;
import com.jhu.clueless.pieces.CharacterEnum;
import com.jhu.clueless.pieces.CharacterPiece;
import com.jhu.clueless.pieces.EmptySquare;
import com.jhu.clueless.pieces.GameStateEnum;
import com.jhu.clueless.pieces.Hallway;
import com.jhu.clueless.pieces.Player;
import com.jhu.clueless.pieces.PlayerData;
import com.jhu.clueless.pieces.Room;
import com.jhu.clueless.pieces.RoomAndHallwayEnum;
import com.jhu.clueless.pieces.RoomCard;
import com.jhu.clueless.pieces.RoomEnum;
import com.jhu.clueless.pieces.Square;
import com.jhu.clueless.pieces.VPlayer;
import com.jhu.clueless.pieces.WeaponCard;
import com.jhu.clueless.pieces.WeaponEnum;
import com.jhu.clueless.util.FileUtilities;
import com.jhu.clueless.util.PlayerDataManager;
import com.jhu.clueless.util.Validator;

/**
 * Defines the network.
 */
public class Network {
	static public final int port = 54777;
	static public final int reponseTime = 999999999;

	// IDs for the object space
	static public final short CONSOLE_PLAYER = 1;
	static public final short CONSOLE_TEXT_AREA = 2;
	static public final short GAME_PLAY = 3;
	static public final short GAME_PLAY_DRIVER = 4;
	static public final short MODEL_LISTENERS = 5;

	/**
	 * Registers the classes to be sent over the network.
	 *
	 * @param endPoint the endpoint
	 */
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		ObjectSpace.registerClasses(kryo);
		kryo.register(ConsolePlayerInterface.class);
		kryo.register(ConsoleInterface.class);
		kryo.register(GamePlay.class);
		kryo.register(BoardGame.class);
		kryo.register(Point.class);
		kryo.register(Card.class);
		kryo.register(CaseFile.class);
		kryo.register(CharacterCard.class);
		kryo.register(CharacterEnum.class);
		kryo.register(EmptySquare.class);
		kryo.register(Hallway.class);
		kryo.register(Player.class);
		kryo.register(Room.class);
		kryo.register(RoomCard.class);
		kryo.register(RoomEnum.class);
		kryo.register(Square.class);
		kryo.register(WeaponCard.class);
		kryo.register(WeaponEnum.class);
		kryo.register(ArrayList.class);
		kryo.register(ModelListenerImpl.class);
		kryo.register(List.class);
		kryo.register(GameStateEnum.class);
		kryo.register(ModelListener.class);
		kryo.register(ModelListenerImpl.class);
		kryo.register(GameBoardPanel.class);
		kryo.register(Color.class);
		kryo.register(float[].class);
		kryo.register(Point.class);
		kryo.register(HashMap.class);
		kryo.register(JPanel.class);
		kryo.register(Card.class);
		kryo.register(WeaponCard.class);
		kryo.register(RoomCard.class);
		kryo.register(CharacterCard.class);
		//--------------------------------------
		kryo.register(CluelessUI.class);
		kryo.register(CardLayoutPanel.class);
		kryo.register(CreateGamePanel.class);
		kryo.register(JoinGamePanel.class);
		kryo.register(MainPanel.class);
		kryo.register(MainPanelButtonPanel.class);
		kryo.register(NotepadLayeredPane.class);
		kryo.register(CharacterPiece.class);
		kryo.register(VPlayer.class);
		kryo.register(FileUtilities.class);
		kryo.register(PlayerDataManager.class);
		kryo.register(PlayerData.class);
		kryo.register(Validator.class);
		kryo.register(FontUIResource.class);
		kryo.register(ColorUIResource.class);
		kryo.register(FlowLayout.class);
		kryo.register(EventListenerList.class);
		kryo.register(Locale.class);
		kryo.register(Dimension.class);
		kryo.register(PropertyChangeSupport.class);
		kryo.register(RoomAndHallwayEnum[][].class);
		kryo.register(Color[][].class);
		kryo.register(RoomAndHallwayEnum.class);
		kryo.register(GameControllerInterface.class);
		kryo.register(JLayeredPane.class);
		kryo.register(SwingJavaBuilder.class);
		kryo.register(Map.class);
		kryo.register(CluelessClient.class);
		kryo.register(Client.class);
		kryo.register(Inet4Address.class);

		//--------------------------------------
		kryo.register(String[].class);
	}
}

