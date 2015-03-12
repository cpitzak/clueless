//package com.jhu.clueless.util;
//
//import java.awt.Point;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//
//import com.jhu.clueless.controllers.GameController;
//import com.jhu.clueless.pieces.Card;
//import com.jhu.clueless.pieces.CharacterCard;
//import com.jhu.clueless.pieces.CharacterEnum;
//import com.jhu.clueless.pieces.CharacterStartLocationEnum;
//import com.jhu.clueless.pieces.RoomCard;
//import com.jhu.clueless.pieces.RoomEnum;
//import com.jhu.clueless.pieces.WeaponCard;
//import com.jhu.clueless.pieces.WeaponEnum;
//
//public class GameBoardPanelTest {
//
//	@Test
//	public void boardTest() {
//		GameController game = new GameController();
//
//		game.getGameBoardPanel().setPlayerDataManager(new PlayerDataManager("m"));
//
//		List<CharacterEnum> players = new ArrayList<CharacterEnum>();
//		CharacterEnum mustard = CharacterEnum.MUSTARD;
//		CharacterEnum scarlet = CharacterEnum.SCARLET;
//		CharacterEnum white = CharacterEnum.WHITE;
//
//		players.add(mustard);
//		players.add(scarlet);
//		players.add(white);
//
//		game.getGameBoardPanel().addPlayer(mustard, "m", CharacterStartLocationEnum.MUSTARD.getPoint());
//		game.getGameBoardPanel().addPlayer(scarlet, "s", CharacterStartLocationEnum.SCARLET.getPoint());
//		game.getGameBoardPanel().addPlayer(white, "w", CharacterStartLocationEnum.WHITE.getPoint());
//
//		game.getGameBoardPanel().movePlayer(mustard, new Point(0,2));
//		game.getGameBoardPanel().movePlayer(scarlet, new Point(2,2));
//		game.getGameBoardPanel().movePlayer(white, new Point(4,2));
//
//		Map<CharacterEnum, List<Card>> playerCardsMap = new HashMap<CharacterEnum, List<Card>>();
//		List<Card> mustardCards = Arrays.asList(new WeaponCard(WeaponEnum.CANDLESTICK.getText()),
//				new RoomCard(RoomEnum.BALLROOM.getText()),
//				new CharacterCard(CharacterEnum.GREEN.getText()));
//
//		List<Card> scarletCards = Arrays.asList(new WeaponCard(WeaponEnum.CANDLESTICK.getText()),
//				new RoomCard(RoomEnum.BALLROOM.getText()),
//				new CharacterCard(CharacterEnum.GREEN.getText()));
//
//		List<Card> whiteCards = Arrays.asList(new WeaponCard(WeaponEnum.CANDLESTICK.getText()),
//				new RoomCard(RoomEnum.BALLROOM.getText()),
//				new CharacterCard(CharacterEnum.GREEN.getText()));
//
//		playerCardsMap.put(mustard, mustardCards);
//		playerCardsMap.put(scarlet, scarletCards);
//		playerCardsMap.put(white, whiteCards);
//
//		game.getGameBoardPanel().startGame(playerCardsMap);
//
//		System.out.println();
//
////		for (int row = 0; row <= 4; row++) {
////			for (int col = 0; col <= 4; col++) {
////				Point point = new Point(row, col);
////				game.getGameBoardPanel().movePlayer(mustard, point);
////			}
////		}
//
//	}
//
//}
