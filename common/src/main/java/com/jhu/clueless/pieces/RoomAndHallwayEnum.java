package com.jhu.clueless.pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ELO
 *
 */
public enum RoomAndHallwayEnum {

	/**
	 * Study room.
	 */
	STUDY("Study"),
	/**
	 * Hall.
	 */
	HALL("Hall"),
	/**
	 * Hallway.
	 */
	HALLWAY("Hallway"),
	/**
	 * Lounge.
	 */
	LOUNGE("Lounge"),
	/**
	 * Library.
	 */
	LIBRARY("Library"),
	/**
	 * Billiard room.
	 */
	BILLIARD("Billiard Room"),
	/**
	 * Dining room.
	 */
	DINNING("Dinning Room"),
	/**
	 * Conservatory.
	 */
	CONSERVATORY("Conservatory"),
	/**
	 * Ballroom.
	 */
	BALLROOM("Ballroom"),
	/**
	 * Kitchen.
	 */
	KITCHEN("Kitchen");

	private static final List<String> ROOM_NAMES;
	static {
		ROOM_NAMES = new ArrayList<String>();
		for (RoomAndHallwayEnum roomEnum : RoomAndHallwayEnum.values()) {
			ROOM_NAMES.add(roomEnum.getText());
		}
	}


	private String room;

	private RoomAndHallwayEnum(String room) {
		this.room = room;
	}

	/**
	 *
	 * @return the Room.
	 */
	public String getText() {
		return room;
	}

	/**
	 * @return a list of room names
	 */
	public static List<String> valuesString() {
		return ROOM_NAMES;
	}

}
