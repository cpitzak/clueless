/**
 *
 */
package com.jhu.clueless.pieces;

import java.awt.Point;
import java.util.List;

/**
 * @author ELO
 *
 */
public class Room extends Square {

	private String roomName;

	/**
	 * Default constructor of Room.
	 */
	public Room() {
	}

	/**
	 * Constructor of Room.
	 * @param location Location of the room
	 * @param destinationLocations Possible moves from the room
	 * @param roomName Name of the room
	 */
	public Room(Point location, List<Point> destinationLocations, String roomName) {
		super(location, destinationLocations);
		this.setRoomName(roomName);
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((roomName == null) ? 0 : roomName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		return true;
	}

}
