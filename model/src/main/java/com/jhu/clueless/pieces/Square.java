package com.jhu.clueless.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ELO
 *
 */
public abstract class Square {

	private Point location;
	private boolean isOccupied;
	private List<Point> destinationLocations = new ArrayList<Point>();
	private List<Player> players = new ArrayList<Player>();

	/**
	 * Default constructor of Square.
	 */
	public Square() {
	}

	/**
	 * Constructor of Square.
	 * @param location x,y of point of the square
	 * @param destinationLocations possible destination locations
	 */
	public Square(Point location, List<Point> destinationLocations) {
		this.location = location;
		this.destinationLocations = destinationLocations;
		this.isOccupied = false;
	}

	/**
	 *
	 * @return location of the square
	 */
	public Point getLocation() {
		return this.location;
	}

	/**
	 *
	 * @return A list of possible destination location
	 */
	public List<Point> getPossibleMoves() {
		return this.destinationLocations;
	}

	/**
	 * @return the isOccupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}

	/**
	 * @param isOccupied
	 *            the isOccupied to set
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	/**
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @param player the players to added to the square
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
		System.out.println("Adding player " + player.getCharacterName() + " to location " + location.toString());
	}

	/**
	 * @param player player to be removed from the square
	 */
	public void removePlayer(Player player) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getCharacterName().equals(player.getCharacterName())) {
				this.players.remove(i);
				System.out.println("Removing player " + player.getCharacterName() + " from location " + location.toString());
				break;
			}
		}

		//this.players.remove(player);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((destinationLocations == null) ? 0 : destinationLocations
						.hashCode());
		result = prime * result + (isOccupied ? 1231 : 1237);
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (destinationLocations == null) {
			if (other.destinationLocations != null)
				return false;
		} else if (!destinationLocations.equals(other.destinationLocations))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
}
