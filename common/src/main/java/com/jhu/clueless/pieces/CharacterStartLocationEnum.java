package com.jhu.clueless.pieces;

import java.awt.Point;

/**
*
* @author ELO
*
*/
public enum CharacterStartLocationEnum {

	/**
	 * Miss Scarlet.
	 */
	SCARLET(new Point(0, 3)),
	/**
	 * Colonel Mustard.
	 */
	MUSTARD(new Point(1, 4)),
	/**
	 * Mrs. White.
	 */
	WHITE(new Point(4, 3)),
	/**
	 * Mr. Green.
	 */
	GREEN(new Point(4, 1)),
	/**
	 * Mrs. Peacock.
	 */
	PEACOCK(new Point(3, 0)),
	/**
	 * Professor Plum.
	 */
	PLUM(new Point(1, 0));


	private Point characterStartLocation;

	private CharacterStartLocationEnum(Point characterStartLocation) {
		this.characterStartLocation = characterStartLocation;
	}

	/**
	 *
	 * @return the Character.
	 */
	public Point getPoint() {
		return characterStartLocation;
	}
}
