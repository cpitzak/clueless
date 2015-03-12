package com.jhu.clueless.pieces;

import java.awt.Point;

/**
 * @author ELO
 *
 */
public class EmptySquare extends Square {
	/**
	 * Default constructor of Hallway.
	 */
	public EmptySquare() {
	}

	/**
	 * Constructor of Hallway.
	 * @param location Location of the hallway
	 */
	public EmptySquare(Point location) {
		super(location, null);
	}
}
