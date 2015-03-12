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
public class Hallway extends Square {

	/**
	 * Default constructor of Hallway.
	 */
	public Hallway() {
	}

	/**
	 * Constructor of Hallway.
	 * @param location Location of the hallway
	 * @param destinationLocations Possible move from the hallway
	 */
	public Hallway(Point location, List<Point> destinationLocations) {
		super(location, destinationLocations);
	}

}
